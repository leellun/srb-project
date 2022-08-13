package com.newland.srb.core.service;

import com.newland.srb.core.pojo.entity.LendItemReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借回款记录表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface ILendItemReturnService extends IService<LendItemReturn> {
    List<LendItemReturn> selectByLendId(Long lendId, Long userId);

    List<Map<String, Object>> addReturnDetail(Long lendReturnId);
    /**
     * 根据还款记录的id查询对应的回款记录
     * @param lendReturnId
     * @return
     */
    List<LendItemReturn> selectLendItemReturnList(Long lendReturnId);
}
