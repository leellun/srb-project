package com.newland.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: leell
 * Date: 2022/8/10 21:32:10
 */
@Data
@ApiModel(description = "登录对象")
public class LoginVO {
    @ApiModelProperty(value = "用户类型")
    private Integer userType;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
}
