package com.example.demo.service;

import com.example.demo.mapper.OrderMapper;
import com.example.demo.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    UserService userService;

    public void insert(Order bean){
        orderMapper.insert(bean);

    }
    public void update(Order bean){
        orderMapper.update(bean);
    }
    public void delete(int id){
        orderMapper.delete(id);
    }
    public List<Order> findByUid(int uid){
        List<Order> os = orderMapper.findByUid(uid);
        User user = userService.findById(uid);
        for (Order o : os) {
            o.setUser(user);
            List<OrderItem> ois = orderItemService.findByOid(o.getId());
            float total = 0;
            int totalNumber = 0;
            for (OrderItem oi : ois) {
                Product product = productService.findById(oi.getPid());
                List<ProductImage>pis = productImageService.findSingleByPid(product.getId());
                product.setFirstProductImage(pis.get(0));
                oi.setProduct(product);
                total += oi.getNumber() * oi.getProduct().getPromotePrice();
                totalNumber += oi.getNumber();
            }
            o.setTotal(total);
            o.setOrderItems(ois);
            o.setTotalNumber(totalNumber);
        }

        return os;
    }
    public List<Order> findAll(){
        List<Order> os = orderMapper.findAll();
        for (Order o : os) {
            User user = userService.findById(o.getUid());
            o.setUser(user);
            List<OrderItem> ois = orderItemService.findByOid(o.getId());
            float total = 0;
            int totalNumber = 0;
            for (OrderItem oi : ois) {
                Product product = productService.findById(oi.getPid());
                List<ProductImage>pis = productImageService.findSingleByPid(product.getId());
                product.setFirstProductImage(pis.get(0));
                oi.setProduct(product);
                total += oi.getNumber() * oi.getProduct().getPromotePrice();
                totalNumber += oi.getNumber();
            }
            o.setTotal(total);
            o.setOrderItems(ois);
            o.setTotalNumber(totalNumber);
        }

        return os;
    }
    public Order findById(int id){

        Order o = orderMapper.findById(id);
        User user = userService.findById(o.getUid());
        o.setUser(user);
            List<OrderItem> ois = orderItemService.findByOid(o.getId());
            float total = 0;
            int totalNumber = 0;
            for (OrderItem oi : ois) {
                Product product = productService.findById(oi.getPid());
                List<ProductImage>pis = productImageService.findSingleByPid(product.getId());
                product.setFirstProductImage(pis.get(0));
                oi.setProduct(product);
                total += oi.getNumber() * oi.getProduct().getPromotePrice();
                totalNumber += oi.getNumber();
            }
            o.setTotal(total);
            o.setOrderItems(ois);
            o.setTotalNumber(totalNumber);

        return o;
    }
    public void delivery(int id){
        orderMapper.delivery(id);
    }
}
