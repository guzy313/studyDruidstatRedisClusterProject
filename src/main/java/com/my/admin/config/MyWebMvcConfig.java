package com.my.admin.config;

import com.my.admin.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 * 1.注册登录相关拦截器
 * @author Gzy
 * @version 1.0
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**") //拦截所有请求
                .excludePathPatterns("/loginTo","/loginOut","/","/login")//不拦截登录相关请求
                .excludePathPatterns("/static","/templates","/templates/error");//不拦截静态资源
    }
}
