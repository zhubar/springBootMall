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

        return "admin/listCategory.jsp";
    }

    @RequestMapping("/insertCategory")
    public String insertCategory(HttpServletRequest request,@RequestParam("file") MultipartFile file)
            throws Exception {
        String name = request.getParameter("name");
        Category c = new Category();
        c.setName(name);
        categoryService.insert(c);

        try {
            //2.根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            String fileName = c.getId() + ".jpg";
            //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
            String destFileName = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\category\\"+ fileName;
            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
            File destFile = new File(destFileName);
            //5.把浏览器上传的文件复制到希望的位置
            file.transferTo(destFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        }

        return  "redirect:listCategory";
    }

    @RequestMapping("/editCategory")
    public String editCategory(Model m,HttpServletRequest request) throws Exception {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryService.findById(cid);
        m.addAttribute("category",c);
        return "admin/editCategory.jsp";

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



        return "redirect:listCategory";

    }

    @RequestMapping("/deleteCategory")
    public String deleteCategory(HttpServletRequest request) throws Exception {
        String cid = request.getParameter("cid");
        categoryService.delete(Integer.parseInt(cid));

        String fileName = cid + ".jpg";
        //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
        String destFileName = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\category\\"+ fileName;
        //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
        File destFile = new File(destFileName);
        destFile.delete();

        return  "redirect:listCategory";
    }


}
