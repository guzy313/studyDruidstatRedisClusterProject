package com.my.admin.config;

import com.my.admin.filter.Filter1;
import com.my.admin.listener.Listener1;
import com.my.admin.servlet.MyServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author Gzy
 * @version 1.0
 */
@Configuration
public class MyRegisConfig {
    @Bean
    public ServletRegistrationBean myServlet(){
        MyServlet myServlet = new MyServlet();
        return new ServletRegistrationBean(myServlet,"/myServlet1");
    }
    @Bean
    public ServletListenerRegistrationBean myListener(){
        Listener1 listener1 = new Listener1();
        return new ServletListenerRegistrationBean(listener1);
    }

    @Bean
    public FilterRegistrationBean myFilter(){
        Filter1 filter1 = new Filter1();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(filter1);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/my","/css/*"));
        return filterRegistrationBean;
    }

}
