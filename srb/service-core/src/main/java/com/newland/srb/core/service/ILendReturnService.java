package com.newland.srb.core.service;

import com.newland.srb.core.pojo.entity.LendItem;
import com.newland.srb.core.pojo.entity.LendReturn;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.srb.core.pojo.vo.InvestVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 还款记录表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface ILendReturnService extends IService<LendReturn> {


    List<LendReturn> selectByLendId(Long lendId);

    String commitReturn(Long lendReturnId, Long userId);

    void notify(Map<String, Object> paramMap);
}
