package com.newland.srb.core.config;

import com.newland.srb.core.config.converter.DecimalMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Author: leell
 * Date: 2022/8/11 22:28:22
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ServiceCoreHandlerInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new DecimalMessageConverter());
    }
}
