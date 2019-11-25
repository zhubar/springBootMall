package com.example.demo.controller;

import com.example.demo.mapper.ProductImageMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.pojo.Category;
import com.example.demo.pojo.Product;
import com.example.demo.pojo.ProductImage;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductImageService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import com.example.demo.util.ImageUtil;

@Controller
public class ProductImageController {

    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/listProductImage")
    public String listProductImage(Model m, HttpServletRequest request){
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product p =productService.findById(pid);
        Category c = categoryService.findById(p.getCid());

        List<ProductImage> pisSingle = productImageService.findSingleByPid(pid);
        List<ProductImage> pisDetail = productImageService.findDetailByPid(pid);

        m.addAttribute("p", p);
        m.addAttribute("c", c);
        m.addAttribute("pisSingle", pisSingle);
        m.addAttribute("pisDetail", pisDetail);

        return "/admin/listProductImage.html";

    }

    @RequestMapping("/deleteProductSingleImage")
    public String deleteProductSingleImage(HttpServletRequest request) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        int pid = Integer.parseInt(request.getParameter("pid"));
        productImageService.delete(id);

        String fileName = id + ".jpg";
        //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
        String destFileName = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\productSingle\\"+ fileName;
        //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
        File destFile = new File(destFileName);
        destFile.delete();

        String destFileName_middle = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\productSingle_middle\\"+ fileName;
        //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
        File destFile_middle = new File(destFileName_middle);
        destFile_middle.delete();

        String destFileName_small = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\productSingle_small\\"+ fileName;
        //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
        File destFile_small = new File(destFileName_small);
        destFile_small.delete();

        return  "redirect:/listProductImage?id="+pid;
    }

    @RequestMapping("/deleteProductDetailImage")
    public String deleteProductDetailImage(HttpServletRequest request) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        int pid = Integer.parseInt(request.getParameter("pid"));
        productImageService.delete(id);

        String fileName = id + ".jpg";
        //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
        String destFileName = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\productDetail\\"+ fileName;
        //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
        File destFile = new File(destFileName);
        destFile.delete();


        return  "redirect:/listProductImage?id="+pid;
    }

    @RequestMapping("/insertProductDetailImage")
    public String insertProductDetailImage(HttpServletRequest request,@RequestParam("file") MultipartFile file)
            throws Exception {
        int pid = Integer.parseInt(request.getParameter("pid"));
        ProductImage pi = new ProductImage();
        pi.setPid(pid);
        pi.setType("type_detail");
        productImageService.insert(pi);

        try {
            String fileName = pi.getId() + ".jpg";
            String destFileName = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\productDetail\\"+ fileName;
            File destFile = new File(destFileName);
            file.transferTo(destFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        }

        return  "redirect:/listProductImage?id="+pid;
    }

    @RequestMapping("/insertProductSingleImage")
    public String insertProductSingleImage(HttpServletRequest request,@RequestParam("file") MultipartFile file)
            throws Exception {
        int pid = Integer.parseInt(request.getParameter("pid"));
        ProductImage pi = new ProductImage();
        pi.setPid(pid);
        pi.setType("type_single");
        productImageService.insert(pi);

        try {
            String fileName = pi.getId() + ".jpg";
            String destFileName = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\productSingle\\"+ fileName;
            File destFile = new File(destFileName);
            file.transferTo(destFile);


            String destFileName_middle = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\productSingle_middle\\"+ fileName;
            File destFile_middle = new File(destFileName_middle);
            //file.transferTo(destFile_middle);
            ImageUtil.resizeImage(destFile,217,190,destFile_middle);

            String destFileName_small = "E:\\project\\springBootMall\\src\\main\\resources\\static\\img\\productSingle_small\\"+ fileName;
            File destFile_small = new File(destFileName_small);
            //file.transferTo(destFile_small);
            ImageUtil.resizeImage(destFile,56,56,destFile_small);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        }

        return  "redirect:/listProductImage?id="+pid;
    }

}
