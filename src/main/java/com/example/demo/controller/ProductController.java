package com.example.demo.controller;

import com.example.demo.pojo.Category;
import com.example.demo.pojo.Product;
import com.example.demo.pojo.Property;
import com.example.demo.pojo.PropertyValue;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.PropertyService;
import com.example.demo.service.PropertyValueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    PropertyValueService propertyValueService;

    @RequestMapping("/listProduct")
    public String listProduct(HttpServletRequest request, Model m, Integer page){
        int cid = Integer.parseInt(request.getParameter("cid"));
        PageInfo<Product> pageInfo = null;
        if(page == null){
            page = 1;
        }
        int pageSize = 5;
        PageHelper.startPage(page,pageSize);
        List<Product> ps = productService.findByCid(cid);
        pageInfo = new PageInfo<>(ps);
        Category category = categoryService.findById(cid);
        m.addAttribute("pageInfo", pageInfo);
        m.addAttribute("category", category);
        return "admin/listProduct.jsp";

    }

    @RequestMapping("/insertProduct")
    public String insertProduct(HttpServletRequest request)
            throws Exception {
        String name = request.getParameter("name");
        int cid = Integer.parseInt(request.getParameter("cid"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        Product p= new Product();
        p.setName(name);
        p.setCid(cid);
        p.setStock(stock);
        p.setOriginalPrice(originalPrice);
        p.setPromotePrice(promotePrice);
        productService.insert(p);

        return  "redirect:listProduct?cid="+cid;
    }

    @RequestMapping("/deleteProduct")
    public String deleteProduct(HttpServletRequest request) throws Exception {
        int cid = Integer.parseInt(request.getParameter("cid"));
        int id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);


        return  "redirect:listProduct?cid="+cid;
    }

    @RequestMapping("/editProduct")
    public String editProduct(Model m,HttpServletRequest request) throws Exception {
        int cid = Integer.parseInt(request.getParameter("cid"));
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = productService.findById(id);
        m.addAttribute("p",p);
        m.addAttribute("cid",cid);
        return "admin/editProduct.jsp";

    }

    @RequestMapping("/updateProduct")
    public String updateProduct(HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        int cid = Integer.parseInt(request.getParameter("cid"));
        int id = Integer.parseInt(request.getParameter("id"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        Product p = new Product();
        p.setCid(cid);
        p.setId(id);
        p.setName(name);
        p.setStock(stock);
        p.setOriginalPrice(originalPrice);
        p.setPromotePrice(promotePrice);
        productService.update(p);

        return "redirect:listProduct?cid="+cid;

    }

    @RequestMapping("/editProductPropertyValue")
    public String editProductPropertyValue(Model m,HttpServletRequest request) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = productService.findById(id);
        m.addAttribute("p", p);
        List<PropertyValue> pvs = propertyValueService.findByPid(p.getId());
        m.addAttribute("pvs", pvs);
        return "admin/editProductValue.jsp";

    }

    @RequestMapping("/updateProductPropertyValue")
    @ResponseBody
    public String updateProductPropertyValue(HttpServletRequest request) throws Exception {
        int id = Integer.parseInt(request.getParameter("pvid"));
        String value = request.getParameter("value");
        int pid = Integer.parseInt(request.getParameter("pid"));
        int ptid = Integer.parseInt(request.getParameter("ptid"));
        PropertyValue pv = new PropertyValue();
        pv.setId(id);
        pv.setPid(pid);
        pv.setPtid(ptid);
        pv.setValue(value);
        propertyValueService.update(pv);
        return "success";

    }


}
