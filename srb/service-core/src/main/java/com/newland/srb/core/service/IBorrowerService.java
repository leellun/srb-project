package com.newland.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newland.srb.core.pojo.entity.Borrower;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.srb.core.pojo.vo.BorrowerApprovalVO;
import com.newland.srb.core.pojo.vo.BorrowerDetailVO;
import com.newland.srb.core.pojo.vo.BorrowerVO;

/**
 * <p>
 * 借款人 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface IBorrowerService extends IService<Borrower> {

    IPage<Borrower> listPage(Page<Borrower> pageParam, String keyword);

    BorrowerDetailVO getBorrowerDetailVOById(Long id);

    void saveBorrowerVOByUserId(BorrowerVO borrowerVO, Long userId);

    Integer getStatusByUserId(Long userId);

    void approval(BorrowerApprovalVO borrowerApprovalVO);
}
