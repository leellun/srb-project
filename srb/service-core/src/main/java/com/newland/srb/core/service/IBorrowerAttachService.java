package com.newland.srb.core.service;

import com.newland.srb.core.pojo.entity.BorrowerAttach;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.srb.core.pojo.vo.BorrowerAttachVO;

import java.util.List;

/**
 * <p>
 * 借款人上传资源表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface IBorrowerAttachService extends IService<BorrowerAttach> {
    List<BorrowerAttachVO> selectBorrowerAttachVOList(Long borrowerId);
}
