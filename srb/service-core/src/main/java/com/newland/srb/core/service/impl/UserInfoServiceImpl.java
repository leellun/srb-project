package com.newland.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newland.srb.base.utils.JwtUtils;
import com.newland.srb.common.exception.Assert;
import com.newland.srb.common.result.ResponseEnum;
import com.newland.srb.common.utils.MD5;
import com.newland.srb.core.mapper.UserAccountMapper;
import com.newland.srb.core.mapper.UserLoginRecordMapper;
import com.newland.srb.core.pojo.entity.UserAccount;
import com.newland.srb.core.pojo.entity.UserInfo;
import com.newland.srb.core.mapper.UserInfoMapper;
import com.newland.srb.core.pojo.entity.UserLoginRecord;
import com.newland.srb.core.pojo.query.UserInfoQuery;
import com.newland.srb.core.pojo.vo.LoginVO;
import com.newland.srb.core.pojo.vo.RegisterVO;
import com.newland.srb.core.pojo.vo.UserIndexVO;
import com.newland.srb.core.pojo.vo.UserInfoVO;
import com.newland.srb.core.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private UserLoginRecordMapper userLoginRecordMapper;

    @Transactional
    @Override
    public void register(RegisterVO registerVO) {
        Integer count = baseMapper.selectCount(Wrappers.<UserInfo>query().eq("mobile", registerVO.getMobile()));
        Assert.isTrue(count == 0, ResponseEnum.MOBILE_EXIST_ERROR);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(registerVO.getUserType());
        userInfo.setNickName(registerVO.getMobile());
        userInfo.setMobile(registerVO.getMobile());
        userInfo.setPassword(MD5.encrypt(registerVO.getPassword()));
        userInfo.setStatus(UserInfo.STATUS_NORMAL);
        userInfo.setHeadImg(UserInfo.USER_AVATAR);
        baseMapper.insert(userInfo);
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userInfo.getId());
        userAccountMapper.insert(userAccount);
    }

    @Transactional
    @Override
    public UserInfoVO login(LoginVO loginVO, String ip) {
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        Integer userType = loginVO.getUserType();
        UserInfo userInfo = baseMapper.selectOne(Wrappers.<UserInfo>query().eq("mobile", mobile).eq("user_type", userType));
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);
        Assert.equals(MD5.encrypt(password), userInfo.getPassword(), ResponseEnum.LOGIN_PASSWORD_ERROR);
        Assert.equals(userInfo.getStatus(), UserInfo.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(userInfo.getId());
        userLoginRecord.setIp(ip);
        userLoginRecordMapper.insert(userLoginRecord);
        String token = JwtUtils.createToken(userInfo.getId(), userInfo.getName());
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setToken(token);
        userInfoVO.setUserType(userType);
        userInfoVO.setMobile(mobile);
        userInfoVO.setHeadImg(userInfo.getHeadImg());
        userInfoVO.setName(userInfo.getName());
        return userInfoVO;
    }

    @Override
    public IPage<UserInfo> listpage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery) {
        if (userInfoQuery == null) {
            return baseMapper.selectPage(pageParam, null);
        }
        String mobile = userInfoQuery.getMobile();
        Integer status = userInfoQuery.getStatus();
        Integer userType = userInfoQuery.getUserType();

        return baseMapper.selectPage(pageParam, Wrappers.<UserInfo>query()
                .eq(StringUtils.isNotBlank(mobile), "mobile", mobile)
                .eq(status != null, "status", status)
                .eq(userType != null, "user_type", userType)
        );
    }

    @Override
    public boolean checkMobile(String mobile) {
        return baseMapper.selectCount(Wrappers.<UserInfo>query().eq("mobile", mobile)) > 0;
    }

    @Override
    public void lock(Long id, Integer status) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setStatus(status);
        baseMapper.updateById(userInfo);
    }
    @Override
    public String getMobileByBindCode(String bindCode) {

        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("bind_code", bindCode);
        UserInfo userInfo = baseMapper.selectOne(userInfoQueryWrapper);
        return userInfo.getMobile();
    }

    @Override
    public UserIndexVO getIndexUserInfo(Long userId) {
        //用户信息
        UserInfo userInfo = baseMapper.selectById(userId);

        //账户信息
        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        userAccountQueryWrapper.eq("user_id", userId);
        UserAccount userAccount = userAccountMapper.selectOne(userAccountQueryWrapper);

        //登录日志
        QueryWrapper<UserLoginRecord> userLoginRecordQueryWrapper = new QueryWrapper<>();
        userLoginRecordQueryWrapper
                .eq("user_id", userId)
                .orderByDesc("id")
                .last("limit 1");
        UserLoginRecord userLoginRecord = userLoginRecordMapper.selectOne(userLoginRecordQueryWrapper);

        //组装结果对象
        UserIndexVO userIndexVO = new UserIndexVO();
        userIndexVO.setUserId(userId);
        userIndexVO.setUserType(userInfo.getUserType());
        userIndexVO.setName(userInfo.getName());
        userIndexVO.setNickName(userInfo.getNickName());
        userIndexVO.setHeadImg(userInfo.getHeadImg());
        userIndexVO.setBindStatus(userInfo.getBindStatus());
        userIndexVO.setAmount(userAccount.getAmount());
        userIndexVO.setFreezeAmount(userAccount.getFreezeAmount());
        userIndexVO.setLastLoginTime(userLoginRecord.getCreateTime());

        return userIndexVO;
    }
}
