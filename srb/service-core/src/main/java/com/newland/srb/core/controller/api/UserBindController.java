package com.newland.srb.core.controller.api;


import com.alibaba.fastjson.JSON;
import com.newland.srb.base.utils.JwtUtils;
import com.newland.srb.common.result.R;
import com.newland.srb.core.hfb.RequestHelper;
import com.newland.srb.core.pojo.vo.UserBindVO;
import com.newland.srb.core.service.IUserBindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Api(tags = "会员账号绑定")
@RestController
@RequestMapping("/api/core/userBind")
@Slf4j
public class UserBindController {

    @Autowired
    private IUserBindService userBindService;

    @ApiOperation("账户绑定提交数据")
    @PostMapping("/auth/bind")
    public R bind(@RequestBody UserBindVO userBindVO, HttpServletRequest request){
        String token= request.getHeader("token");
        Long userId= JwtUtils.getUserId(token);
        String formatStr=userBindService.commitBindUser(userBindVO,userId);
        return R.ok().data("formStr",formatStr);
    }

    @ApiOperation("账户绑定异步回调")
    @PostMapping("/notify")
    public String notify(HttpServletRequest request){
        Map<String,Object> paramMap= RequestHelper.switchMap(request.getParameterMap());
        log.info("账户绑定异步回调接收的参数如下：" + JSON.toJSONString(paramMap));
        //验签
        if(!RequestHelper.isSignEquals(paramMap)){
            log.error("用户账号绑定异步回调签名验证错误：" + JSON.toJSONString(paramMap));
            return "fail";
        }
        log.info("验签成功！开始账户绑定");
        userBindService.notify(paramMap);

        return "success";
    }
}

