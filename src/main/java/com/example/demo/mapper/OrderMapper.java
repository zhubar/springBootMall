package com.example.demo.mapper;

import com.example.demo.pojo.Order;
import com.example.demo.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    void insert(Order bean);
    void update(Order bean);
    void delete(int id);
    List<Order> findByUid(int uid);
    List<Order> findAll();
    Order findById(int id);
    void delivery(int id);


}
