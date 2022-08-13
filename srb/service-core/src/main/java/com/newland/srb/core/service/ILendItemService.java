package com.newland.srb.core.service;

import com.newland.srb.core.pojo.entity.LendItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.srb.core.pojo.vo.InvestVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借记录表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface ILendItemService extends IService<LendItem> {
    /**
     * 会员投资提交数据
     * @param investVO
     * @return
     */
    String commitInvest(InvestVO investVO);

    /**
     * 会员投资异步回调
     * @param paramMap
     */
    void notify(Map<String,Object> paramMap);

    /**
     * 通过标的查询出借记录
     * @param lendId
     * @param status
     * @return
     */
    List<LendItem> selectByLendId(Long lendId, Integer status);
    /**
     * 通过标的查询出借记录
     * @param lendId
     * @return
     */
    List<LendItem> selectByLendId(Long lendId);
}
