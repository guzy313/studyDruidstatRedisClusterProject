package com.my.admin.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录验证拦截
 * @author Gzy
 * @version 1.0
 */
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUser = request.getSession().getAttribute("loginUser");
        String requestURI = request.getRequestURI();
//        System.out.println("user:"+loginUser);
//        System.out.println("uri"+requestURI);
        if(loginUser != null || ("/".equals(requestURI) || "/login".equals(requestURI))){
            return true;
        }else{
            response.sendRedirect("/login");//如果没登录 访问任何资源 将跳转到登录页面
            return false;
        }


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
