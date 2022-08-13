package com.newland.srb.core.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newland.srb.common.exception.Assert;
import com.newland.srb.common.result.ResponseEnum;
import com.newland.srb.core.enums.BorrowerStatusEnum;
import com.newland.srb.core.enums.IntegralEnum;
import com.newland.srb.core.mapper.BorrowerAttachMapper;
import com.newland.srb.core.mapper.UserInfoMapper;
import com.newland.srb.core.mapper.UserIntegralMapper;
import com.newland.srb.core.pojo.entity.Borrower;
import com.newland.srb.core.mapper.BorrowerMapper;
import com.newland.srb.core.pojo.entity.UserInfo;
import com.newland.srb.core.pojo.entity.UserIntegral;
import com.newland.srb.core.pojo.vo.BorrowerApprovalVO;
import com.newland.srb.core.pojo.vo.BorrowerAttachVO;
import com.newland.srb.core.pojo.vo.BorrowerDetailVO;
import com.newland.srb.core.pojo.vo.BorrowerVO;
import com.newland.srb.core.service.IBorrowerAttachService;
import com.newland.srb.core.service.IBorrowerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.srb.core.service.IDictService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 借款人 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Service
public class BorrowerServiceImpl extends ServiceImpl<BorrowerMapper, Borrower> implements IBorrowerService {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserIntegralMapper userIntegralMapper;

    @Resource
    private BorrowerAttachMapper borrowerAttachMapper;

    @Resource
    private IBorrowerAttachService borrowerAttachService;

    @Resource
    private IDictService dictService;

    @Override
    public IPage<Borrower> listPage(Page<Borrower> pageParam, String keyword) {
        return baseMapper.selectPage(pageParam, Wrappers.<Borrower>query().like("name", keyword).or().like("id_card", keyword).or().like("mobile", keyword).orderByDesc("id"));
    }

    @Override
    public BorrowerDetailVO getBorrowerDetailVOById(Long id) {
        Borrower borrower = baseMapper.selectById(id);

        BorrowerDetailVO borrowerDetailVO = new BorrowerDetailVO();
        BeanUtils.copyProperties(borrower, borrowerDetailVO);

        borrowerDetailVO.setMarry(borrower.getMarry() ? "是" : "否");
        borrowerDetailVO.setSex(borrower.getSex() == 1 ? "男" : "女");

        //下拉列表
        borrowerDetailVO.setEducation(dictService.getNameByParentDictCodeAndValue("education", borrower.getEducation()));
        borrowerDetailVO.setIndustry(dictService.getNameByParentDictCodeAndValue("industry", borrower.getIndustry()));
        borrowerDetailVO.setIncome(dictService.getNameByParentDictCodeAndValue("income", borrower.getIncome()));
        borrowerDetailVO.setReturnSource(dictService.getNameByParentDictCodeAndValue("returnSource", borrower.getReturnSource()));
        borrowerDetailVO.setContactsRelation(dictService.getNameByParentDictCodeAndValue("relation", borrower.getContactsRelation()));


        //审批状态
        String status = BorrowerStatusEnum.getMsgByStatus(borrower.getStatus());
        borrowerDetailVO.setStatus(status);

        List<BorrowerAttachVO> borrowerAttachVOList = borrowerAttachService.selectBorrowerAttachVOList(id);
        borrowerDetailVO.setBorrowerAttachVOList(borrowerAttachVOList);

        return borrowerDetailVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveBorrowerVOByUserId(BorrowerVO borrowerVO, Long userId) {
        Borrower dbBorrower = baseMapper.selectOne(Wrappers.<Borrower>query().eq("user_id", userId));

        Assert.notEquals(dbBorrower.getStatus(), BorrowerStatusEnum.AUTH_RUN.getStatus(), ResponseEnum.RUNNING_WAIT_AUTH_ERROR);

        UserInfo userInfo = userInfoMapper.selectById(userId);

        //保存借款人信息
        Borrower borrower = new Borrower();
        BeanUtils.copyProperties(borrowerVO, borrower);
        borrower.setUserId(userId);
        borrower.setName(userInfo.getName());
        borrower.setIdCard(userInfo.getIdCard());
        borrower.setMobile(userInfo.getMobile());
        borrower.setStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        baseMapper.insert(borrower);
        if (dbBorrower != null) {
            baseMapper.deleteById(dbBorrower.getId());
        }

        //保存附件
        borrowerVO.getBorrowerAttachList().forEach(borrowerAttach -> {
            borrowerAttach.setBorrowerId(borrower.getId());
            borrowerAttachMapper.insert(borrowerAttach);
        });

        //更新会员状态，更新为认证中
        userInfo.setBorrowAuthStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public Integer getStatusByUserId(Long userId) {
        List<Object> statusList = baseMapper.selectObjs(Wrappers.<Borrower>query().select("status").eq("user_id", userId).orderByDesc("id"));
        if (statusList.size() == 0) {
            return BorrowerStatusEnum.NO_AUTH.getStatus();
        }
        return ((Integer) statusList.get(0));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void approval(BorrowerApprovalVO borrowerApprovalVO) {

        //获取借款额度申请id
        Long borrowerId = borrowerApprovalVO.getBorrowerId();

        //获取借款额度申请对象
        Borrower borrower = baseMapper.selectById(borrowerId);

        //设置审核状态
        borrower.setStatus(borrowerApprovalVO.getStatus());
        baseMapper.updateById(borrower);

        //获取用户id
        Long userId = borrower.getUserId();

        //获取用户对象
        UserInfo userInfo = userInfoMapper.selectById(userId);

        //用户的原始积分
        Integer integral = userInfo.getIntegral();

        //计算基本信息积分
        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setUserId(userId);
        userIntegral.setIntegral(borrowerApprovalVO.getInfoIntegral());
        userIntegral.setContent("借款人基本信息");
        userIntegralMapper.insert(userIntegral);
        int currentIntegral = integral + borrowerApprovalVO.getInfoIntegral();

        //身份证积分
        if (borrowerApprovalVO.getIsIdCardOk()) {
            userIntegral = new UserIntegral();
            userIntegral.setUserId(userId);
            userIntegral.setIntegral(IntegralEnum.BORROWER_IDCARD.getIntegral());
            userIntegral.setContent(IntegralEnum.BORROWER_IDCARD.getMsg());
            userIntegralMapper.insert(userIntegral);
            currentIntegral += IntegralEnum.BORROWER_IDCARD.getIntegral();
        }

        //房产积分
        if (borrowerApprovalVO.getIsHouseOk()) {
            userIntegral = new UserIntegral();
            userIntegral.setUserId(userId);
            userIntegral.setIntegral(IntegralEnum.BORROWER_HOUSE.getIntegral());
            userIntegral.setContent(IntegralEnum.BORROWER_HOUSE.getMsg());
            userIntegralMapper.insert(userIntegral);
            currentIntegral += IntegralEnum.BORROWER_HOUSE.getIntegral();
        }

        //车辆积分
        if (borrowerApprovalVO.getIsCarOk()) {
            userIntegral = new UserIntegral();
            userIntegral.setUserId(userId);
            userIntegral.setIntegral(IntegralEnum.BORROWER_CAR.getIntegral());
            userIntegral.setContent(IntegralEnum.BORROWER_CAR.getMsg());
            userIntegralMapper.insert(userIntegral);
            currentIntegral += IntegralEnum.BORROWER_CAR.getIntegral();
        }

        //设置用户总积分
        userInfo.setIntegral(currentIntegral);

        //修改审核状态
        userInfo.setBorrowAuthStatus(borrowerApprovalVO.getStatus());

        //更新userInfo
        userInfoMapper.updateById(userInfo);
    }
}
