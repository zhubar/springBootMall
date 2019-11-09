package com.example.demo.service;

import com.example.demo.mapper.OrderItemMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.pojo.OrderItem;
import com.example.demo.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductMapper productMapper;

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
            Product p = productMapper.findById(oi.getPid());
            oi.setProduct(p);
        }
        return ois;
    }
    public List<OrderItem> findByOid(int oid){
        return orderItemMapper.findByOid(oid);
    }
    public List<OrderItem> findByPid(int pid){
        return orderItemMapper.findByPid(pid);
    }
    public List<OrderItem> findAll(){
        return orderItemMapper.findAll();
    }
    public OrderItem findById(int id){
        return orderItemMapper.findById(id);
    }


}
