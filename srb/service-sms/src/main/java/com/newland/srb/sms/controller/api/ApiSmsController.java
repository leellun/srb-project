package com.newland.srb.sms.controller.api;

import com.newland.srb.common.exception.Assert;
import com.newland.srb.common.result.R;
import com.newland.srb.common.result.ResponseEnum;
import com.newland.srb.common.utils.RandomUtils;
import com.newland.srb.common.utils.RegexValidateUtils;
import com.newland.srb.sms.client.CoreUserInfoClient;
import com.newland.srb.sms.service.ISmsService;
import com.newland.srb.sms.utils.SmsProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Author: leell
 * Date: 2022/8/10 20:38:19
 */
@RestController
@RequestMapping("/api/sms")
@Api(tags = "短信管理")
@Slf4j
public class ApiSmsController {

    @Autowired
    private ISmsService smsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private CoreUserInfoClient coreUserInfoClient;

    /**
     * 获取验证码
     * @param mobile
     * @return
     */
    @ApiOperation("获取验证码")
    @GetMapping("/send/{mobile}")
    public R send(@ApiParam(value = "手机号",required = true) @PathVariable("mobile") String  mobile){
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile),ResponseEnum.MOBILE_ERROR);
        boolean result=coreUserInfoClient.checkMobile(mobile);
        Assert.isTrue(!result,ResponseEnum.MOBILE_EXIST_ERROR);
        String code= RandomUtils.getFourBitRandom();
        HashMap<String,Object> map=new HashMap<>();
        map.put("code",code);
        log.debug("发送验证码:"+code);
        try{
            smsService.send(mobile, SmsProperties.TEMPLATE_CODE,map);
        }catch (Exception e){
            e.printStackTrace();
        }
        redisTemplate.opsForValue().set("srb:sms:code:"+mobile,code,5, TimeUnit.MINUTES);
        return R.ok().message("短信发送成功");
    }
}
