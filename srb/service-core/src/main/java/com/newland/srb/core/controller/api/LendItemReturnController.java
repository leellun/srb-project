package com.newland.srb.core.controller.api;


import com.newland.srb.base.utils.JwtUtils;
import com.newland.srb.common.result.R;
import com.newland.srb.core.pojo.entity.LendItemReturn;
import com.newland.srb.core.service.ILendItemReturnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 标的出借回款记录表 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Api(tags = "回款计划")
@RestController
@RequestMapping("/api/core/lendItemReturn")
@Slf4j
public class LendItemReturnController {

    @Resource
    private ILendItemReturnService lendItemReturnService;

    @ApiOperation("获取列表")
    @GetMapping("/list/{lendId}")
    public R list(
            @ApiParam(value = "标的id", required = true)
            @PathVariable Long lendId, HttpServletRequest request) {

        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        List<LendItemReturn> list = lendItemReturnService.selectByLendId(lendId, userId);
        return R.ok().data("list", list);
    }
}

