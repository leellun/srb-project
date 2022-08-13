package com.newland.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: leell
 * Date: 2022/8/10 21:33:56
 */
@Data
@ApiModel(description = "用户信息")
public class UserInfoVO {
    @ApiModelProperty(value = "用户姓名")
    private String name;
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    @ApiModelProperty(value = "头像")
    private String headImg;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "用户类型")
    private Integer userType;
    @ApiModelProperty(value = "JWT访问令牌")
    private String token;
}
