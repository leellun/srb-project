package com.newland.srb.core.mapper;

import com.newland.srb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 借款信息表 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface BorrowInfoMapper extends BaseMapper<BorrowInfo> {
    List<BorrowInfo> selectBorrowInfoList();
}
