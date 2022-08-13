package com.newland.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newland.srb.core.pojo.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.srb.core.pojo.query.UserInfoQuery;
import com.newland.srb.core.pojo.vo.LoginVO;
import com.newland.srb.core.pojo.vo.RegisterVO;
import com.newland.srb.core.pojo.vo.UserIndexVO;
import com.newland.srb.core.pojo.vo.UserInfoVO;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface IUserInfoService extends IService<UserInfo> {

    void register(RegisterVO registerVO);
    UserInfoVO login(LoginVO loginVO,String ip);
    IPage<UserInfo> listpage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery);

    boolean checkMobile(String mobile);

    void lock(Long id, Integer status);

    String getMobileByBindCode(String bindCode);

    UserIndexVO getIndexUserInfo(Long userId);
}
