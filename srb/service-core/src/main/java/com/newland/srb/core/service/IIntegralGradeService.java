package com.newland.srb.core.service;

import com.newland.srb.core.pojo.entity.IntegralGrade;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 积分等级表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface IIntegralGradeService extends IService<IntegralGrade> {
    boolean saveIntegralGrade(IntegralGrade integralGrade);
}
