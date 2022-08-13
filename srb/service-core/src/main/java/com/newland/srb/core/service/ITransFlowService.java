package com.newland.srb.core.service;

import com.newland.srb.core.pojo.bo.TransFlowBO;
import com.newland.srb.core.pojo.entity.TransFlow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 交易流水表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface ITransFlowService extends IService<TransFlow> {
    void saveTransFlow(TransFlowBO transFlowBO);

    boolean isSaveTransFlow(String agentBillNo);

    List<TransFlow> selectByUserId(Long userId);
}
