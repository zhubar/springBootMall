package com.example.demo.mapper;

import com.example.demo.pojo.Category;
import com.example.demo.pojo.OrderItem;
import com.example.demo.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderItemMapper {
    void insert(OrderItem bean);
    void update(OrderItem bean);
    void delete(int id);
    List<OrderItem> findByUid(int uid);
    List<OrderItem> findByOid(int oid);
    List<OrderItem> findByPid(int pid);
    List<OrderItem> findAll();
    OrderItem findById(int id);


}
