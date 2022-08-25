package com.my.admin.controller;

import com.my.admin.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author Gzy
 * @version 1.0
 */
@Controller
public class TableController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("basicTable")
    public String basicTable(){
        return "table/basic_table";
    }

    @GetMapping("dynamicTable")
    public String dynamicTable(){
        return "table/dynamic_table";
    }

    @GetMapping("responsiveTable")
    public String responsiveTable(){
        return "table/responsive_table";
    }

    @GetMapping("editTableTable")
    public String editTableTable(){
        return "table/editTable_table";
    }



    @PostMapping("saveBaseTable")
    public void saveBaseTable(@RequestParam("name")String name,
                              @RequestPart("file1")MultipartFile file1){
    }


    @ResponseBody
    @GetMapping("getUserList")
    public List<User> getUserList(){
        String sql = "select * from user ";
        List<User> users = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
        return users;
    }

    @ResponseBody
    @RequestMapping("delTable")
    public void delTable(){
        String sql = "drop table test2";
        jdbcTemplate.execute(sql);

    }



}
