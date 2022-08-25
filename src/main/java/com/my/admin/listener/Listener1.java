package com.my.admin.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 监听器测试
 * @author Gzy
 * @version 1.0
 */
//@WebListener
public class Listener1 implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Listener1 监听初始化..");
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Listener1 监听销毁..");
    }
}
