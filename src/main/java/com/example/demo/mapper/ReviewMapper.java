package com.example.demo.mapper;

import com.example.demo.pojo.Category;
import com.example.demo.pojo.Review;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReviewMapper {
    List<Review> findAll();
    void update(Review bean);
    void insert(Review bean);
    void delete(int id);
    Review findById(int id);
    List<Review>findByPid(int pid);
    List<Review>findByUid(int uid);
}
