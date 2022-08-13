package com.newland.srb.core.service;

import com.newland.srb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.srb.core.pojo.vo.BorrowInfoApprovalVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 借款信息表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface IBorrowInfoService extends IService<BorrowInfo> {

    BigDecimal getBorrowAmount(Long userId);

    void submit(BorrowInfo borrowInfo, Long userId);

    Integer getStatusByUserId(Long userId);

    List<BorrowInfo> selectList();

    Map<String, Object> getBorrowInfoDetail(Long id);

    void approval(BorrowInfoApprovalVO borrowInfoApprovalVO);
}
