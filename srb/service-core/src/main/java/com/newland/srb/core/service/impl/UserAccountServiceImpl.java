package com.newland.srb.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.newland.srb.rabbitmq.constant.MQConst;
import com.newland.srb.rabbitmq.service.MQService;
import com.newland.srb.base.dto.SmsDTO;
import com.newland.srb.common.exception.Assert;
import com.newland.srb.common.result.ResponseEnum;
import com.newland.srb.core.enums.TransTypeEnum;
import com.newland.srb.core.hfb.FormHelper;
import com.newland.srb.core.hfb.HfbConst;
import com.newland.srb.core.hfb.RequestHelper;
import com.newland.srb.core.mapper.UserInfoMapper;
import com.newland.srb.core.pojo.bo.TransFlowBO;
import com.newland.srb.core.pojo.entity.UserAccount;
import com.newland.srb.core.mapper.UserAccountMapper;
import com.newland.srb.core.pojo.entity.UserInfo;
import com.newland.srb.core.service.ITransFlowService;
import com.newland.srb.core.service.IUserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.srb.core.service.IUserBindService;
import com.newland.srb.core.service.IUserInfoService;
import com.newland.srb.core.utils.LendNoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Service
@Slf4j
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ITransFlowService transFlowService;

    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IUserBindService userBindService;
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private MQService mqService;

    @Override
    public String commitCharge(BigDecimal chargeAmt, Long userId) {
        //获取充值人绑定协议号
        UserInfo userInfo = userInfoMapper.selectById(userId);
        String bindCode = userInfo.getBindCode();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentBillNo", LendNoUtils.getChargeNo());
        paramMap.put("bindCode", bindCode);
        paramMap.put("chargeAmt", chargeAmt);
        paramMap.put("feeAmt", new BigDecimal("0"));
        paramMap.put("notifyUrl", HfbConst.RECHARGE_NOTIFY_URL);
        paramMap.put("returnUrl", HfbConst.RECHARGE_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        paramMap.put("sign", RequestHelper.getSign(paramMap));


        String formStr = FormHelper.buildForm(HfbConst.RECHARGE_URL, paramMap);
        return formStr;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String notify(Map<String, Object> paramMap) {
        String agentBillNo = (String) paramMap.get("agentBillNo");

        //幂等性判断？标准 =  //判断交易流水是否存在
        boolean isSave = transFlowService.isSaveTransFlow(agentBillNo);
        if (isSave) {
            log.warn("幂等性返回");
            return "success";
        }

        //账户处理
        String bindCode = (String) paramMap.get("bindCode");
        String chargeAmt = (String) paramMap.get("chargeAmt");
        baseMapper.updateAccount(bindCode, new BigDecimal(chargeAmt), new BigDecimal(0));

        //记录账户流水
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBillNo,
                bindCode,
                new BigDecimal(chargeAmt),
                TransTypeEnum.RECHARGE,
                "充值啦");

        transFlowService.saveTransFlow(transFlowBO);

        //发消息
        log.info("发消息");
        String mobile = userInfoService.getMobileByBindCode(bindCode);
        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setMobile(mobile);
        smsDTO.setMessage("充值成功");
        mqService.sendMessage(MQConst.EXCHANGE_TOPIC_SMS, MQConst.ROUTING_SMS_ITEM, smsDTO);
        return "success";
    }

    @Override
    public BigDecimal getAccount(Long userId) {
        return baseMapper.selectOne(Wrappers.<UserAccount>query().eq("user_id", userId)).getAmount();
    }

    @Override
    public String commitWithdraw(BigDecimal fetchAmt, Long userId) {
        //用户账户余额
        BigDecimal amount = userAccountService.getAccount(userId);
        Assert.isTrue(amount.doubleValue() >= fetchAmt.doubleValue(),
                ResponseEnum.NOT_SUFFICIENT_FUNDS_ERROR);

        String bindCode = userBindService.getBindCodeByUserId(userId);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentBillNo", LendNoUtils.getWithdrawNo());
        paramMap.put("bindCode", bindCode);
        paramMap.put("fetchAmt", fetchAmt);
        paramMap.put("feeAmt", new BigDecimal(0));
        paramMap.put("notifyUrl", HfbConst.WITHDRAW_NOTIFY_URL);
        paramMap.put("returnUrl", HfbConst.WITHDRAW_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        String sign = RequestHelper.getSign(paramMap);
        paramMap.put("sign", sign);

        //构建自动提交表单
        String formStr = FormHelper.buildForm(HfbConst.WITHDRAW_URL, paramMap);
        return formStr;
    }

    @Override
    public void notifyWithdraw(Map<String, Object> paramMap) {
        //幂等判断
        log.info("提现成功");
        String agentBillNo = (String) paramMap.get("agentBillNo");
        boolean result = transFlowService.isSaveTransFlow(agentBillNo);
        if (result) {
            log.warn("幂等性返回");
            return;
        }

        //账户同步
        String bindCode = (String) paramMap.get("bindCode");
        String fetchAmt = (String) paramMap.get("fetchAmt");
        baseMapper.updateAccount(bindCode, new BigDecimal("-" + fetchAmt), new BigDecimal(0));

        //交易流水
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBillNo,
                bindCode,
                new BigDecimal(fetchAmt),
                TransTypeEnum.WITHDRAW,
                "提现啦");
        transFlowService.saveTransFlow(transFlowBO);
    }
}
