package com.example.demo.controller;

import com.example.demo.pojo.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.service.PropertyService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.demo.pojo.Property;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
public class ProperyController {
    @Autowired
    PropertyService propertyService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/listProperty")
    public String listCategory(HttpServletRequest request, Model m, Integer page) throws Exception {
        int cid = Integer.parseInt(request.getParameter("cid"));
        PageInfo<Property> pageInfo = null;
        if(page == null){
            page = 1;
        }
        int pageSize = 5;
        PageHelper.startPage(page,pageSize);
        List<Property> cs=propertyService.findByCid(cid);
        pageInfo = new PageInfo<>(cs);
        Category category = categoryService.findById(cid);
        m.addAttribute("pageInfo", pageInfo);
        m.addAttribute("category", category);
        return "admin/listProperty.jsp";
    }

    @RequestMapping("/insertProperty")
    public String insertProperty(HttpServletRequest request)
            throws Exception {
        String name = request.getParameter("name");
        String subTitle= request.getParameter("subTitle");
        int cid = Integer.parseInt(request.getParameter("cid"));
        System.out.println(name);
        Property p = new Property();
        p.setName(name);
        p.setCid(cid);
        propertyService.insert(p);

        return  "redirect:listProperty?cid="+cid;
    }

    @RequestMapping("/deleteProperty")
    public String deleteCategory(HttpServletRequest request) throws Exception {
        int cid = Integer.parseInt(request.getParameter("cid"));
        int id = Integer.parseInt(request.getParameter("id"));
        propertyService.delete(id);


        return  "redirect:listProperty?cid="+cid;
    }

    @RequestMapping("/editProperty")
    public String editCategory(Model m,HttpServletRequest request) throws Exception {
        int cid = Integer.parseInt(request.getParameter("cid"));
        int id = Integer.parseInt(request.getParameter("id"));
        Property p = propertyService.findById(id);
        m.addAttribute("p",p);
        m.addAttribute("cid",cid);
        return "admin/editProperty.jsp";

    }

    @RequestMapping("/updateProperty")
    public String updateCategory(HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        int cid = Integer.parseInt(request.getParameter("cid"));
        int id = Integer.parseInt(request.getParameter("id"));
        Property p = new Property();
        p.setCid(cid);
        p.setId(id);
        p.setName(name);
        propertyService.update(p);


        return "redirect:listProperty?cid="+cid;

    }

}
