package com.example.demo.mapper;

import com.example.demo.pojo.Product;
import com.example.demo.pojo.ProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductImageMapper {
    void insert(ProductImage bean);
    void update(ProductImage bean);
    void delete(int id);
    ProductImage findById(int id);
    List<ProductImage> findSingleByPid(int pid);
    List<ProductImage> findDetailByPid(int pid);
}
