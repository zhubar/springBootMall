package com.example.demo.service;

import com.example.demo.mapper.OrderItemMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.OrderItem;
import com.example.demo.pojo.Product;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    public void insert(OrderItem bean){
        orderItemMapper.insert(bean);
    }
    public void update(OrderItem bean){
        orderItemMapper.update(bean);
    }
    public void delete(int id){
        orderItemMapper.delete(id);
    }
    public List<OrderItem> findByUid(int uid){
        List<OrderItem> ois = orderItemMapper.findByUid(uid);
        for(OrderItem oi :ois){
            Product p = productService.findById(oi.getPid());
            User u = userService.findById(oi.getUid());
            oi.setProduct(p);
            oi.setUser(u);
        }
        return ois;
    }
    public List<OrderItem> findByOid(int oid){

        List<OrderItem> ois = orderItemMapper.findByOid(oid);
        for(OrderItem oi :ois){
            Product p = productService.findById(oi.getPid());
            User u = userService.findById(oi.getUid());
            oi.setProduct(p);
            oi.setUser(u);
        }
        return ois;
    }
    public List<OrderItem> findByPid(int pid){
        List<OrderItem> ois = orderItemMapper.findByPid(pid);
        for(OrderItem oi :ois){
            Product p = productService.findById(oi.getPid());
            User u = userService.findById(oi.getUid());
            oi.setProduct(p);
            oi.setUser(u);
        }
        return ois;
    }
    public List<OrderItem> findAll(){

        List<OrderItem> ois = orderItemMapper.findAll();
        for(OrderItem oi :ois){
            Product p = productService.findById(oi.getPid());
            User u = userService.findById(oi.getUid());
            oi.setProduct(p);
            oi.setUser(u);
        }
        return ois;
    }
    public OrderItem findById(int id){
        OrderItem oi = orderItemMapper.findById(id);
            Product p = productService.findById(oi.getPid());
            User u = userService.findById(oi.getUid());
            oi.setProduct(p);
            oi.setUser(u);
        return oi;
    }


}
