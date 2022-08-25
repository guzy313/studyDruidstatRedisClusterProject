package com.my.admin.servlet;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * testServlet
 * @author Gzy
 * @version 1.0
 */
//@WebServlet(urlPatterns = "/testS")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        System.out.println("sssservlet");
        resp.getOutputStream().write("测试Servlet".getBytes());
    }
}
