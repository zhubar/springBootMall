package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.Category;
import com.example.demo.pojo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/listUser")
    public String listUser(Model m, Integer page) throws Exception {
        PageInfo<User> pageInfo = null;
        if(page == null){
            page = 1;
        }
        int pageSize = 10;
        PageHelper.startPage(page,pageSize);
        List<User> us=userMapper.findAll();
        pageInfo = new PageInfo<>(us);
        m.addAttribute("pageInfo", pageInfo);

        return "/admin/listUser.html";
    }
}
