package com.newland.srb.core.service.impl;

import com.newland.srb.core.pojo.entity.IntegralGrade;
import com.newland.srb.core.mapper.IntegralGradeMapper;
import com.newland.srb.core.service.IIntegralGradeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 积分等级表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Service("integralGradeService")
public class IntegralGradeServiceImpl extends ServiceImpl<IntegralGradeMapper, IntegralGrade> implements IIntegralGradeService {

    @Override
    public boolean saveIntegralGrade(IntegralGrade integralGrade) {
        return baseMapper.insert(integralGrade)>0;
    }
}
