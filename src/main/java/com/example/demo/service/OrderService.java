package com.example.demo.service;

import com.example.demo.mapper.OrderMapper;
import com.example.demo.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderMapper orderMapper;

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
        return orderMapper.findByUid(uid);
    }
    public List<Order> findAll(){
        return orderMapper.findAll();
    }
    public Order findById(int id){
        return orderMapper.findById(id);
    }
    public void delivery(int id){
        orderMapper.delivery(id);
    }
}
