package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.pojo.Category;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CategoryMapper {
    List<Category> findAll();
    void update(Category bean);
    void insert(Category bean);
    void delete(int id);
    Category findById(int id);
}
