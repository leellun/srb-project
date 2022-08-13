package com.newland.srb.core.service;

import com.newland.srb.core.pojo.entity.UserLoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface IUserLoginRecordService extends IService<UserLoginRecord> {

    List<UserLoginRecord> listTop50(Long userId);
}
