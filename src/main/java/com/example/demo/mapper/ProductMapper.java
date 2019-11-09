package com.example.demo.mapper;

import com.example.demo.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {
    void insert(Product bean);
    void update(Product bean);
    void delete(int id);
    List<Product> findByCid(int cid);
    Product findById(int id);
    List<Product>search(String keyword);
}
