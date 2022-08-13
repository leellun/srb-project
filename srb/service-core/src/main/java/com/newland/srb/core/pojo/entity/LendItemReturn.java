package com.newland.srb.core.pojo.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 标的出借回款记录表
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_lend_item_return")
@ApiModel(value="LendItemReturn对象", description="标的出借回款记录表")
public class LendItemReturn implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标的还款id")
    private Long lendReturnId;

    @ApiModelProperty(value = "标的项id")
    private Long lendItemId;

    @ApiModelProperty(value = "标的id")
    private Long lendId;

    @ApiModelProperty(value = "出借用户id")
    private Long investUserId;

    @ApiModelProperty(value = "出街金额")
    private BigDecimal investAmount;

    @ApiModelProperty(value = "实际发生的还款时间")
    private LocalDateTime realReturnTime;

    @ApiModelProperty(value = "是否预期")
    @TableField("is_overdue")
    private Boolean overdue;

    @ApiModelProperty(value = "预期金额")
    private BigDecimal overdueTotal;

    @ApiModelProperty(value = "状态（0-未归还 1-已归还）")
    private Integer status;

    @ApiModelProperty(value = "年化利率")
    private BigDecimal lendYearRate;

    @ApiModelProperty(value = "还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本")
    private Integer returnMethod;

    @ApiModelProperty(value = "本金")
    private BigDecimal principal;

    @ApiModelProperty(value = "利息")
    private BigDecimal interest;

    @ApiModelProperty(value = "本息")
    private BigDecimal total;

    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;

    @ApiModelProperty(value = "还款时指定的还款日期")
    private LocalDate returnDate;

    @ApiModelProperty(value = "当前的期数")
    private Integer currentPeriod;

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


}
