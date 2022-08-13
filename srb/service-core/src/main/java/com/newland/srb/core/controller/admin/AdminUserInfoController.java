package com.newland.srb.core.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newland.srb.common.result.R;
import com.newland.srb.core.pojo.entity.UserInfo;
import com.newland.srb.core.pojo.query.UserInfoQuery;
import com.newland.srb.core.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Author: leell
 * Date: 2022/8/10 21:21:17
 */
@Api(tags = "会员管理")
@Slf4j
@RestController
@RequestMapping("/admin/core/userInfo")
public class AdminUserInfoController {
    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 获取会员分页列表
     * @param page
     * @param limit
     * @param userInfoQuery
     * @return
     */
    @ApiOperation("获取会员分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码",required = true) @PathVariable Long page, @ApiParam(value = "每页记录数",required = true) @PathVariable Long limit , @ApiParam(value = "查询对象",required = false) UserInfoQuery userInfoQuery){
        Page<UserInfo> pageParam=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page,limit);
        IPage<UserInfo> pageModel=userInfoService.listpage(pageParam,userInfoQuery);
        return R.ok().data("pageModel",pageModel);
    }
    @ApiOperation("锁定和解锁")
    @PutMapping("/lock/{id}/{status}")
    public R lock(@ApiParam(value = "用户id",required = true)
                  @PathVariable("id") Long id,
                  @ApiParam(value = "锁定状态(0:锁定 1:正常)",required = true)
                  @PathVariable("status") Integer status
                  ){
        userInfoService.lock(id,status);
        return R.ok().message(status==1?"解锁成功":"锁定成功");
    }
}
