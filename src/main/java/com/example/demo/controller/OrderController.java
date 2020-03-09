package com.example.demo.controller;

import com.example.demo.mapper.OrderItemMapper;
import com.example.demo.pojo.Category;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.OrderItem;
import com.example.demo.pojo.Product;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductImageService;
import com.example.demo.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;

    @RequestMapping("/listOrder")
    public String listOrder(Model m, Integer page) throws Exception {
        PageInfo<Order> pageInfo = null;
        if(page == null){
            page = 1;
        }
        int pageSize = 10;
        PageHelper.startPage(page,pageSize);
        List<Order> os = orderService.findAll();
        pageInfo = new PageInfo<>(os);
        m.addAttribute("pageInfo", pageInfo);

        return "/admin/listOrder.html";
    }

    @RequestMapping("/deliveryOrder")
    public String deliveryOrder(HttpServletRequest request) throws Exception {
        int oid = Integer.parseInt(request.getParameter("id"));
        Order order = orderService.findById(oid);
        order.setDeliveryDate(new Date());
        order.setStatus("waitConfirm");
        orderService.update(order);

        return  "redirect:/listOrder";
    }

}
