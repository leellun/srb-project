package com.newland.srb.core.controller.admin;

import com.newland.srb.common.exception.Assert;
import com.newland.srb.common.result.R;
import com.newland.srb.common.result.ResponseEnum;
import com.newland.srb.core.pojo.entity.IntegralGrade;
import com.newland.srb.core.service.IIntegralGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * 积分等级管理
 */
@Api(tags = "积分等级管理")
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {
    @Qualifier("integralGradeService")
    @Autowired
    private IIntegralGradeService integralGradeService;

    @GetMapping("/list")
    public R listAll(){
        return R.ok().data("list",integralGradeService.list()).message("获取列表成功");
    }

    @ApiOperation(value="根据id删除数据记录",notes = "逻辑删除数据记录")
    @DeleteMapping("/remove/{id}")
    public R deleteById(@ApiParam(value = "数据id",example = "100",required = true) @PathVariable Long id){
        boolean result= integralGradeService.removeById(id);
        if(result){
            return R.ok().message("删除成功");
        }else{
            return R.error().message("删除失败");
        }
    }
    @ApiOperation("新增积分等级")
    @PostMapping("/save")
    public R save(@ApiParam(value = "积分等级对象",required = true) @RequestBody IntegralGrade integralGrade){
        Assert.notNull(integralGrade.getBorrowAmount(), ResponseEnum.BORROW_AMOUNT_NULL_ERROR);;
        boolean result=integralGradeService.saveIntegralGrade(integralGrade);
        if(result){
            return R.ok().message("保存成功");
        }else{
            return R.error().message("保存失败");
        }
    }
    @ApiOperation("根据id获取积分等级")
    @GetMapping("/get/{id}")
    public R getById(@ApiParam(value = "数据id",required = true)@PathVariable Long id){
        IntegralGrade integralGrade=integralGradeService.getById(id);
        if(integralGrade==null){
            return R.error().message("数据获取失败");
        }else{
            return R.ok().data("record",integralGrade);
        }
    }
    @ApiOperation("更新积分等级")
    @PutMapping("/update")
    public R updateById(@ApiParam(value = "积分等级对象",required = true)@RequestBody IntegralGrade integralGrade){
        boolean result=integralGradeService.updateById(integralGrade);
        if(result){
            return R.ok().message("更新成功");
        }else{
            return R.error().message("更新失败");
        }
    }
}
