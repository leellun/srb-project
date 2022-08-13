package com.newland.srb.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newland.srb.common.result.R;
import com.newland.srb.core.pojo.entity.Borrower;
import com.newland.srb.core.pojo.vo.BorrowerApprovalVO;
import com.newland.srb.core.pojo.vo.BorrowerDetailVO;
import com.newland.srb.core.service.IBorrowerService;
import com.newland.srb.core.service.impl.BorrowerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 借款人管理
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Api(tags = "借款人管理")
@RestController
@RequestMapping("/admin/core/borrower")
@Slf4j
public class AdminBorrowerController {

    @Autowired
    private IBorrowerService borrowerService;

    @ApiOperation("获取借款人分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(
            @ApiParam(value = "当前页码")@PathVariable
            Long page, @ApiParam("每页记录数") @PathVariable Long limit,@ApiParam(value = "关键字",required = false)  String keyword){
        Page<Borrower> pageParam=new Page<>(page,limit);
        IPage<Borrower> pagemodel=borrowerService.listPage(pageParam,keyword);
        return R.ok().data("pageModel",pagemodel);
    }

    @ApiOperation("获取借款人信息")
    @GetMapping("/show/{id}")
    public R show(@ApiParam(value = "借款人id",required = true)@PathVariable Long id){
        BorrowerDetailVO borrowerDetailVO=borrowerService.getBorrowerDetailVOById(id);
        return R.ok().data("borrowerDetailVO",borrowerDetailVO);
    }

    @ApiOperation("借款额度审批")
    @PostMapping("/approval")
    public R approval(@RequestBody BorrowerApprovalVO borrowerApprovalVO){
        borrowerService.approval(borrowerApprovalVO);
        return R.ok().message("审批完成");
    }
}

