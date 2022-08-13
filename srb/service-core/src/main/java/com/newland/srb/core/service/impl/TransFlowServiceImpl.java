package com.newland.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.srb.core.mapper.TransFlowMapper;
import com.newland.srb.core.mapper.UserInfoMapper;
import com.newland.srb.core.pojo.bo.TransFlowBO;
import com.newland.srb.core.pojo.entity.TransFlow;
import com.newland.srb.core.pojo.entity.UserInfo;
import com.newland.srb.core.service.ITransFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 交易流水表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Service
public class TransFlowServiceImpl extends ServiceImpl<TransFlowMapper, TransFlow> implements ITransFlowService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void saveTransFlow(TransFlowBO transFlowBO) {

        String bindCode = transFlowBO.getBindCode();
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>query().eq("bind_code", bindCode));

        TransFlow transFlow = new TransFlow();
        transFlow.setTransAmount(transFlowBO.getAmount());
        transFlow.setMemo(transFlowBO.getMemo());
        transFlow.setTransTypeName(transFlowBO.getTransTypeEnum().getTransTypeName());
        transFlow.setTransType(transFlowBO.getTransTypeEnum().getTransType());
        transFlow.setTransNo(transFlowBO.getAgentBillNo());//流水号
        transFlow.setUserId(userInfo.getId());
        transFlow.setUserName(userInfo.getName());
        baseMapper.insert(transFlow);
    }

    @Override
    public boolean isSaveTransFlow(String agentBillNo) {
        Integer count = baseMapper.selectCount(Wrappers.<TransFlow>query().eq("trans_no", agentBillNo));
        return count > 0;
    }

    @Override
    public List<TransFlow> selectByUserId(Long userId) {
        QueryWrapper<TransFlow> transFlowQueryWrapper = new QueryWrapper<>();
        transFlowQueryWrapper.eq("user_id", userId).orderByDesc("id");
        return baseMapper.selectList(transFlowQueryWrapper);
    }
}
