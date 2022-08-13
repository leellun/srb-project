package com.newland.srb.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.newland.srb.common.exception.Assert;
import com.newland.srb.common.result.ResponseEnum;
import com.newland.srb.core.enums.UserBindEnum;
import com.newland.srb.core.hfb.FormHelper;
import com.newland.srb.core.hfb.HfbConst;
import com.newland.srb.core.hfb.RequestHelper;
import com.newland.srb.core.mapper.UserInfoMapper;
import com.newland.srb.core.pojo.entity.UserBind;
import com.newland.srb.core.mapper.UserBindMapper;
import com.newland.srb.core.pojo.entity.UserInfo;
import com.newland.srb.core.pojo.vo.UserBindVO;
import com.newland.srb.core.service.IUserBindService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.http.util.Asserts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Service
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements IUserBindService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public String commitBindUser(UserBindVO userBindVO, Long userId) {
        UserBind userBind = baseMapper.selectOne(Wrappers.<UserBind>query().eq("id_card", userBindVO.getIdCard()).ne("user_id", userId));
        //通过 idcard和userid判断是否已经绑定
        Assert.isNull(userBind, ResponseEnum.USER_BIND_IDCARD_EXIST_ERROR);

        userBind = baseMapper.selectOne(Wrappers.<UserBind>query().eq("user_id", userId));
        if (userBind == null) {
            userBind = new UserBind();
            BeanUtils.copyProperties(userBindVO, userBind);
            userBind.setUserId(userId);
            userBind.setStatus(UserBindEnum.NO_BIND.getStatus());
            baseMapper.insert(userBind);
        } else {
            BeanUtils.copyProperties(userBindVO, userBind);
            baseMapper.updateById(userBind);
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentUserId", userId);
        paramMap.put("idCard", userBindVO.getIdCard());
        paramMap.put("personalName", userBindVO.getName());
        paramMap.put("bankType", userBindVO.getBankType());
        paramMap.put("bankNo", userBindVO.getBankNo());
        paramMap.put("mobile", userBindVO.getMobile());
        paramMap.put("returnUrl", HfbConst.USERBIND_RETURN_URL);
        paramMap.put("notifyUrl", HfbConst.USERBIND_NOTIFY_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        paramMap.put("sign", RequestHelper.getSign(paramMap));
        String formStr = FormHelper.buildForm(HfbConst.USERBIND_URL, paramMap);
        return formStr;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void notify(Map<String, Object> paramMap) {
        String bindCode = (String) paramMap.get("bindCode");
        String agentUserId = (String) paramMap.get("agentUserId");

        UserBind userBind = baseMapper.selectOne(Wrappers.<UserBind>query().eq("user_id", agentUserId));
        userBind.setBindCode(bindCode);
        userBind.setStatus(UserBindEnum.BIND_OK.getStatus());
        baseMapper.updateById(userBind);

        UserInfo userInfo = userInfoMapper.selectById(agentUserId);
        userInfo.setBindCode(bindCode);
        userInfo.setIdCard(userBind.getIdCard());
        userInfo.setName(userBind.getName());
        userInfo.setBindStatus(UserBindEnum.BIND_OK.getStatus());
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public String getBindCodeByUserId(Long investUserId) {
        return (String) baseMapper.selectObjs(Wrappers.<UserBind>query().select("bind_code").eq("user_id",investUserId)).get(0);
    }
}
