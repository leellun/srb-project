package com.heepay.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 注入公共字段自动填充,任选注入方式即可
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("createTime")) {
            setFieldValByName("createTime", LocalDateTime.now().withNano(0), metaObject);//mybatis-plus版本2.0.9+
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("updateTime")) {
            setFieldValByName("updateTime", LocalDateTime.now().withNano(0), metaObject);//mybatis-plus版本2.0.9+
        }
    }
}
