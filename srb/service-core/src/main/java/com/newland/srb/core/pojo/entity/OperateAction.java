package com.newland.srb.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 操作行为记录表
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_operate_action")
@ApiModel(value="OperateAction对象", description="操作行为记录表")
public class OperateAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "操作主键id")
    private Long operateId;

    @ApiModelProperty(value = "操作类型")
    private String operateType;

    @ApiModelProperty(value = "操作内容")
    private String content;

    @ApiModelProperty(value = "操作用户id")
    private Long actUserId;

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
