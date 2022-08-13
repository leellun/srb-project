package com.newland.srb.core.service;

import com.newland.srb.core.pojo.entity.BorrowInfo;
import com.newland.srb.core.pojo.entity.Lend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.srb.core.pojo.vo.BorrowInfoApprovalVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface ILendService extends IService<Lend> {

    void createLend(BorrowInfoApprovalVO borrowInfoApprovalVO, BorrowInfo borrowInfo);

    List<Lend> selectList();

    Map<String, Object> getLendDetail(Long id);

    BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, Integer totalmonth, Integer returnMethod);

    void makeLoan(Long id);
}
