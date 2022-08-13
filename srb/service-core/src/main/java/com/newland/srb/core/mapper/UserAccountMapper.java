package com.newland.srb.core.mapper;

import com.newland.srb.core.pojo.entity.UserAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * <p>
 * 用户账户 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Repository
public interface UserAccountMapper extends BaseMapper<UserAccount> {
    void updateAccount(
            @Param("bindCode")String bindCode,
            @Param("amount") BigDecimal amount,
            @Param("freezeAmount")BigDecimal freezeAmount);
}
