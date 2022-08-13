package com.newland.srb.core.pojo.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 标的准备表
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_lend")
@ApiModel(value="Lend对象", description="标的准备表")
public class Lend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "借款用户id")
    private Long userId;

    @ApiModelProperty(value = "借款信息id")
    private Long borrowInfoId;

    @ApiModelProperty(value = "标的编号")
    private String lendNo;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "标的金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "投资期数")
    private Integer period;

    @ApiModelProperty(value = "年化利率")
    private BigDecimal lendYearRate;

    @ApiModelProperty(value = "平台服务费")
    private BigDecimal serviceRate;

    @ApiModelProperty(value = "还款方式")
    private Integer returnMethod;

    @ApiModelProperty(value = "最低投资金额")
    private BigDecimal lowestAmount;

    @ApiModelProperty(value = "投资金额")
    private BigDecimal investAmount;

    @ApiModelProperty(value = "投资人数")
    private Integer investNum;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishDate;

    @ApiModelProperty(value = "开始时间")
    private LocalDate lendStartDate;

    @ApiModelProperty(value = "结束时间")
    private LocalDate lendEndDate;

    @ApiModelProperty(value = "说明")
    private String lendInfo;

    @ApiModelProperty(value = "平台预期收益")
    private BigDecimal expectAmount;

    @ApiModelProperty(value = "实际收益")
    private BigDecimal realAmount;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime checkTime;

    @ApiModelProperty(value = "审核用户id")
    private Long checkAdminId;

    @ApiModelProperty(value = "放款时间")
    private LocalDateTime paymentTime;

    @ApiModelProperty(value = "放款人id")
    private Long paymentAdminId;

    @ApiModelProperty(value = "开始时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "结束时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "其他参数")
    @TableField(exist = false)
    private Map<String,Object> param = new HashMap<>();
}
