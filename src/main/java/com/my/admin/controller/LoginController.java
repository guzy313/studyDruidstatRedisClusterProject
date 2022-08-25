package com.my.admin.controller;

import com.my.admin.exception.TestException;
import com.my.admin.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gzy
 * @version 1.0
 */
@Controller
public class LoginController {

    @GetMapping(value = {"/","/login"})
    public String loginPage(){
        return "login";
    }

    @PostMapping(value = "/loginTo")
    public String loginTo(HttpServletRequest request,
                          HttpServletResponse response, HttpSession session) throws IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        //模拟数据库
        User user = new User();
        user.setUsername("钟离");
        user.setPassword("123");
        List<User> users = new ArrayList<>();
        users.add(user);
        for (User u:users) {
            if(u.getUsername().equals(userName) && u.getPassword().equals(password)){
                request.setAttribute("loginMsg","登录成功");
                response.sendRedirect("mainPage");
                session.setAttribute("loginUser",user);
                return null;
            }
        }
        request.setAttribute("loginMsg","登录失败");
        return "login";
    }

    @GetMapping("loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("loginUser");
        return "redirect:login";
    }


    @GetMapping("mainPage")
    public String mainPage(HttpServletRequest request,HttpSession session){
        if(session.getAttribute("loginUser") != null){
            return "main";
        }else{
            return "redirect:login";
        }

    }

    @ResponseBody
    @GetMapping("test11")
    public void test11(){
        int x = 1/0;
    }


    @ResponseBody
    @GetMapping("test22")
    public void test22(){
        throw new TestException("测试错误---");
    }

}
