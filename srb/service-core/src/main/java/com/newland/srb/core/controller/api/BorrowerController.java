package com.newland.srb.core.controller.api;


import com.newland.srb.base.utils.JwtUtils;
import com.newland.srb.common.result.R;
import com.newland.srb.core.pojo.vo.BorrowerVO;
import com.newland.srb.core.service.IBorrowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 借款人 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Api(tags = "借款人")
@RestController
@RequestMapping("/api/core/borrower")
@Slf4j
public class BorrowerController {

    @Autowired
    private IBorrowerService borrowerService;

    @ApiOperation("保存借款人的信息")
    @PostMapping("/auth/save")
    public R save(@RequestBody BorrowerVO borrowerVO, HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        borrowerService.saveBorrowerVOByUserId(borrowerVO, userId);
        return R.ok().message("信息提交成功");
    }
    @ApiOperation("获取借款人认证状态")
    @GetMapping("/auth/getBorrowerStatus")
    public R getBorrowerStatus(HttpServletRequest request){

        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer status = borrowerService.getStatusByUserId(userId);
        return R.ok().data("borrowerStatus", status);
    }
}

