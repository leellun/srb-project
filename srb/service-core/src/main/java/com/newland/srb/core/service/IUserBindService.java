package com.newland.srb.core.service;

import com.newland.srb.core.pojo.entity.UserBind;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.srb.core.pojo.vo.UserBindVO;

import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface IUserBindService extends IService<UserBind> {

    String commitBindUser(UserBindVO userBindVO, Long userId);

    void notify(Map<String, Object> paramMap);

    String getBindCodeByUserId(Long investUserId);
}
