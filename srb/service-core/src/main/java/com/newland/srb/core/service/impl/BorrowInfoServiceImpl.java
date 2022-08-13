package com.newland.srb.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.newland.srb.common.exception.Assert;
import com.newland.srb.common.result.ResponseEnum;
import com.newland.srb.core.enums.BorrowInfoStatusEnum;
import com.newland.srb.core.enums.BorrowerStatusEnum;
import com.newland.srb.core.enums.UserBindEnum;
import com.newland.srb.core.mapper.BorrowerMapper;
import com.newland.srb.core.mapper.IntegralGradeMapper;
import com.newland.srb.core.mapper.UserInfoMapper;
import com.newland.srb.core.pojo.entity.BorrowInfo;
import com.newland.srb.core.mapper.BorrowInfoMapper;
import com.newland.srb.core.pojo.entity.Borrower;
import com.newland.srb.core.pojo.entity.IntegralGrade;
import com.newland.srb.core.pojo.entity.UserInfo;
import com.newland.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.newland.srb.core.pojo.vo.BorrowerDetailVO;
import com.newland.srb.core.service.IBorrowInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.srb.core.service.IBorrowerService;
import com.newland.srb.core.service.IDictService;
import com.newland.srb.core.service.ILendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 借款信息表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Service
public class BorrowInfoServiceImpl extends ServiceImpl<BorrowInfoMapper, BorrowInfo> implements IBorrowInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private IntegralGradeMapper integralGradeMapper;
    @Autowired
    private IDictService dictService;

    @Autowired
    private BorrowerMapper borrowerMapper;
    @Autowired
    private IBorrowerService borrowerService;

    @Autowired
    private ILendService lendService;

    @Override
    public BigDecimal getBorrowAmount(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);

        Integer integral = userInfo.getIntegral();
        IntegralGrade integralGrade = integralGradeMapper.selectOne(Wrappers.<IntegralGrade>query().le("integral_start", integral).ge("integral_end", integral));
        if (integralGrade == null) {
            return new BigDecimal(0);
        }
        return integralGrade.getBorrowAmount();
    }

    @Override
    public void submit(BorrowInfo borrowInfo, Long userId) {
        //获取userInfo信息
        UserInfo userInfo = userInfoMapper.selectById(userId);
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);
        //判断用户绑定状态
        Assert.isTrue(userInfo.getBindStatus().intValue() == UserBindEnum.BIND_OK.getStatus(), ResponseEnum.USER_NO_BIND_ERROR);
        //判断借款人额度申请状态
        Assert.isTrue(userInfo.getBorrowAuthStatus().intValue() == BorrowerStatusEnum.AUTH_OK.getStatus(), ResponseEnum.USER_NO_AMOUNT_ERROR);
        //判断借款人额度是否充足
        BigDecimal borrowAmount = this.getBorrowAmount(userId);
        Assert.isTrue(borrowInfo.getAmount().doubleValue() <= borrowAmount.doubleValue(), ResponseEnum.USER_AMOUNT_LESS_ERROR);

        //存储borrowInfo数据
        borrowInfo.setUserId(userId);
        //百分比转小数
        borrowInfo.setBorrowYearRate(borrowInfo.getBorrowYearRate().divide(new BigDecimal(100)));
        //设置借款申请的审核状态
        borrowInfo.setStatus(BorrowInfoStatusEnum.CHECK_RUN.getStatus());
        baseMapper.insert(borrowInfo);
    }

    @Override
    public Integer getStatusByUserId(Long userId) {
        List<Object> objects = baseMapper.selectObjs(Wrappers.<BorrowInfo>query().select("status").eq("user_id", userId));
        if (objects.size() == 0) {
            return BorrowInfoStatusEnum.NO_AUTH.getStatus();
        }
        Integer status = (Integer) objects.get(0);
        return status;
    }

    @Override
    public List<BorrowInfo> selectList() {
        List<BorrowInfo> borrowInfoList = baseMapper.selectBorrowInfoList();
        borrowInfoList.forEach(borrowInfo -> {
            String returnMethod = dictService.getNameByParentDictCodeAndValue("returnMethod", borrowInfo.getReturnMethod());
            String moneyUse = dictService.getNameByParentDictCodeAndValue("moneyUse", borrowInfo.getMoneyUse());
            String status = BorrowInfoStatusEnum.getMsgByStatus(borrowInfo.getStatus());

            borrowInfo.getParam().put("returnMethod", returnMethod);
            borrowInfo.getParam().put("moneyUse", moneyUse);
            borrowInfo.getParam().put("status", status);
        });

        return borrowInfoList;
    }

    @Override
    public Map<String, Object> getBorrowInfoDetail(Long id) {

        //查询借款对象：BorrowInfo
        BorrowInfo borrowInfo = baseMapper.selectById(id);
        String returnMethod = dictService.getNameByParentDictCodeAndValue("returnMethod", borrowInfo.getReturnMethod());
        String moneyUse = dictService.getNameByParentDictCodeAndValue("moneyUse", borrowInfo.getMoneyUse());
        String status = BorrowInfoStatusEnum.getMsgByStatus(borrowInfo.getStatus());
        borrowInfo.getParam().put("returnMethod", returnMethod);
        borrowInfo.getParam().put("moneyUse", moneyUse);
        borrowInfo.getParam().put("status", status);

        //查询借款人对象：Borrower(BorrowerDetailVO)
        Borrower borrower = borrowerMapper.selectOne(Wrappers.<Borrower>query().eq("user_id", borrowInfo.getUserId()));
        BorrowerDetailVO borrowerDetailVO = borrowerService.getBorrowerDetailVOById(borrower.getId());

        //组装集合结果
        Map<String, Object> result = new HashMap<>();
        result.put("borrowInfo", borrowInfo);
        result.put("borrower", borrowerDetailVO);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void approval(BorrowInfoApprovalVO borrowInfoApprovalVO) {
        //修改借款审核的状态 borrow_info
        Long borrowInfoId = borrowInfoApprovalVO.getId();
        BorrowInfo borrowInfo = baseMapper.selectById(borrowInfoId);
        borrowInfo.setStatus(borrowInfoApprovalVO.getStatus());
        baseMapper.updateById(borrowInfo);

        //如果审核通过，则产生新的标的记录 lend
        if (borrowInfoApprovalVO.getStatus().intValue() == BorrowInfoStatusEnum.CHECK_OK.getStatus().intValue()) {
            //创建新标的
            lendService.createLend(borrowInfoApprovalVO, borrowInfo);
        }
    }
}
