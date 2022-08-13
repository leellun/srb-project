package com.newland.srb.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.newland.srb.core.pojo.entity.BorrowerAttach;
import com.newland.srb.core.mapper.BorrowerAttachMapper;
import com.newland.srb.core.pojo.vo.BorrowerAttachVO;
import com.newland.srb.core.service.IBorrowerAttachService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 借款人上传资源表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Service
public class BorrowerAttachServiceImpl extends ServiceImpl<BorrowerAttachMapper, BorrowerAttach> implements IBorrowerAttachService {

    @Override
    public List<BorrowerAttachVO> selectBorrowerAttachVOList(Long borrowerId) {
        List<BorrowerAttach> borrowerAttachList=baseMapper.selectList(Wrappers.<BorrowerAttach>query().eq("borrower_id",borrowerId));

        List<BorrowerAttachVO> borrowerAttachVOS=new ArrayList<>();
        borrowerAttachList.forEach(borrowerAttach -> {
            BorrowerAttachVO borrowerAttachVO = new BorrowerAttachVO();
            borrowerAttachVO.setImageType(borrowerAttach.getImageType());
            borrowerAttachVO.setImageUrl(borrowerAttach.getImageUrl());
            borrowerAttachVOS.add(borrowerAttachVO);
        });
        return borrowerAttachVOS;
    }
}
