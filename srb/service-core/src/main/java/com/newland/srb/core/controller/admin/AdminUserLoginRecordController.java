package com.newland.srb.core.controller.admin;

import com.newland.srb.common.result.R;
import com.newland.srb.core.pojo.entity.UserLoginRecord;
import com.newland.srb.core.service.IUserLoginRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: leell
 * Date: 2022/8/12 00:35:31
 */
@Api(tags = "会员登录日志接口")
@RestController
@RequestMapping("/admin/core/userLoginRecord")
@Slf4j
public class AdminUserLoginRecordController {
    @Autowired
    private IUserLoginRecordService userLoginRecordService;

    @ApiOperation("获取会员登录日志列表")
    @GetMapping("/listTop50/{userId}")
    public R listTop50(
            @ApiParam(value ="用户id", required = true)
            @PathVariable Long userId){
        List<UserLoginRecord> userLoginRecordList = userLoginRecordService.listTop50(userId);
        return R.ok().data("list", userLoginRecordList);
    }
}
