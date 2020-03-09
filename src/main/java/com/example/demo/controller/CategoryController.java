package com.example.demo.controller;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.mapper.CategoryMapper;
import com.example.demo.pojo.Category;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.util.ImageUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@RestController
@Controller
public class CategoryController  {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/listCategory")
    public String listCategory(Model m,Integer page) throws Exception {
        PageInfo<Category>pageInfo = null;
        if(page == null){
            page = 1;
        }
        int pageSize = 5;
        PageHelper.startPage(page,pageSize);
        List<Category> cs=categoryService.findAll();
        pageInfo = new PageInfo<>(cs);
        m.addAttribute("pageInfo", pageInfo);

        return "/admin/listCategory.html";
    }

    @RequestMapping("/insertCategory")
    public String insertCategory(HttpServletRequest request,@RequestParam("file") MultipartFile file)
            throws Exception {
        String name = request.getParameter("name");
        Category c = new Category();
        c.setName(name);
        categoryService.insert(c);

        try {
            String fileName = c.getId() + ".jpg";
            String destFileName = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\category\\"+ fileName;
            File destFile = new File(destFileName);
            file.transferTo(destFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "error" + e.getMessage();
        }

        return  "redirect:/listCategory";
    }

    @RequestMapping("/editCategory")
    public String editCategory(Model m,HttpServletRequest request) throws Exception {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryService.findById(cid);
        m.addAttribute("category",c);
        return "/admin/editCategory.html";

    }

    @RequestMapping("/updateCategory")
    public String updateCategory(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws Exception {
        String name = request.getParameter("name");
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = new Category();
        c.setId(cid);
        c.setName(name);
        categoryService.update(c);
        if(!file.isEmpty()){
            try{
                String fileName = c.getId() + ".jpg";
                String destFileName = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\category\\"+ fileName;
                File destFile = new File(destFileName);
                file.transferTo(destFile);

            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
        }



        return "redirect:/listCategory";

    }

    @RequestMapping("/deleteCategory")
    public String deleteCategory(HttpServletRequest request) throws Exception {
        String cid = request.getParameter("cid");
        categoryService.delete(Integer.parseInt(cid));
        String fileName = cid + ".jpg";
        String destFileName = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\category\\"+ fileName;
        File destFile = new File(destFileName);
        destFile.delete();

        return  "redirect:/listCategory";
    }


}
