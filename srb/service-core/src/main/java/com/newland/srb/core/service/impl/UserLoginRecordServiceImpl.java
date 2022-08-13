package com.newland.srb.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.newland.srb.core.pojo.entity.UserLoginRecord;
import com.newland.srb.core.mapper.UserLoginRecordMapper;
import com.newland.srb.core.service.IUserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements IUserLoginRecordService {

    @Override
    public List<UserLoginRecord> listTop50(Long userId) {
        return baseMapper.selectList(Wrappers.<UserLoginRecord>query().eq("user_id", userId).orderByDesc("id").last("limit 50"));
    }
}
