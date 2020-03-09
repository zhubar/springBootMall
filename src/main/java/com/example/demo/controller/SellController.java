package com.example.demo.controller;

import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.pojo.Category;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.OrderItem;
import com.example.demo.pojo.Product;
import com.example.demo.service.OrderItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SellController {
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;

    @RequestMapping("/listSell")
    public String listOrder(Model m, Integer page,HttpServletRequest request) throws Exception {
        List<Category> cs = categoryMapper.findAll();
        for(Category c : cs){
            List<Product>ps = productMapper.findByCid(c.getId());
            c.setProducts(ps);
            int sell = 0;
            for(Product p : ps){
                List<OrderItem> ois = orderItemService.findByPid(p.getId());
                for( OrderItem oi : ois){
                    if(oi.getOid()>0)
                        sell += oi.getNumber();
                }
            }
            c.setSell(sell);
        }

        List<String>cname = new ArrayList<>();
        List<Integer>csell = new ArrayList<>();
        List<Integer>cid = new ArrayList<>();
        for(Category c : cs){
            cname.add(c.getName());
            csell.add(c.getSell());
            cid.add(c.getId());
        }

        List<Product>pro = new ArrayList<>();
        if(cid.size()>=1){
            pro = cs.get(0).getProducts();
            for(Product p : pro){
                List<OrderItem> ois = orderItemService.findByPid(p.getId());
                for( OrderItem oi : ois){
                    if(oi.getOid()>0)
                        p.setSell(p.getSell()+oi.getNumber());
                }
            }
        }

        List<String>pname = new ArrayList<>();
        List<Integer>psell = new ArrayList<>();
        for(Product p : pro){
            pname.add(p.getName());
            psell.add(p.getSell());

        }
        m.addAttribute("pname",pname);
        m.addAttribute("psell",psell);

        m.addAttribute("cname",cname);
        m.addAttribute("csell",csell);
        m.addAttribute("cid",cid);
        m.addAttribute("control",1);


        return "/admin/listSell.html";
    }

    @RequestMapping("/sellAjax")
    @ResponseBody
    public String ajax(HttpServletRequest request, HttpServletResponse response){
        int cid = Integer.parseInt(request.getParameter("id"));
        List<Product>pro = productMapper.findByCid(cid);
        for(Product p : pro){
            List<OrderItem> ois = orderItemService.findByPid(p.getId());
            for( OrderItem oi : ois){
                if(oi.getOid()>0)
                    p.setSell(p.getSell()+oi.getNumber());
            }
        }
        response.setContentType("text/html;charset=utf-8");
        List<String>pname = new ArrayList<>();
        List<Integer>psell = new ArrayList<>();
        for(Product p : pro){
            pname.add(p.getName());
            psell.add(p.getSell());

        }
        try{
            PrintWriter out = response.getWriter();
            out.print(pname);
            out.print("?");
            out.print(psell);
            out.flush();
        }
        catch(IOException e){

        }
        return "success";
    }



}
