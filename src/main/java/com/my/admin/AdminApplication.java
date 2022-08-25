package com.my.admin;

import com.my.admin.servlet.MyServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

//@ServletComponentScan(basePackages = {"com.my.admin.servlet",
//        "com.my.admin.listener","com.my.admin.filter"})
@MapperScan(value = {"com.my.admin.mapper"})
@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AdminApplication.class, args);

    }

}
