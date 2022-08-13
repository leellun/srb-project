package com.newland.srb.core.controller.api;


import com.newland.srb.base.utils.JwtUtils;
import com.newland.srb.common.result.R;
import com.newland.srb.core.pojo.entity.BorrowInfo;
import com.newland.srb.core.service.IBorrowInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * <p>
 * 借款信息表 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Api(tags = "借款信息")
@RestController
@RequestMapping("/api/core/borrowInfo")
@Slf4j
public class BorrowInfoController {
    @Autowired
    private IBorrowInfoService borrowInfoService;

    @ApiOperation("获取借款额度")
    @GetMapping("/auth/getBorrowAmount")
    public R getBorrowAmount(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        BigDecimal borrowAmount = borrowInfoService.getBorrowAmount(userId);
        return R.ok().data("borrowAmount", borrowAmount);
    }

    @ApiOperation("提交借款申请")
    @PostMapping("/auth/save")
    public R submit(@RequestBody BorrowInfo borrowInfo, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        borrowInfoService.submit(borrowInfo, userId);
        return R.ok().message("提交成功");
    }

    @ApiOperation("获取借款申请审批状态")
    @GetMapping("/auth/getBorrowInfoStatus")
    public R getBorrowerStatus(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer status = borrowInfoService.getStatusByUserId(userId);
        return R.ok().data("borrowInfoStatus", status);
    }
}

