package com.my.admin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author Gzy
 * @version 1.0
 */
//@WebFilter(urlPatterns = {"/xxx/*","/yyy"})
public class Filter1 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter 初始化..");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤..");
    }

    @Override
    public void destroy() {

    }
}
