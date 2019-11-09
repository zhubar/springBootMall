package com.example.demo.service;

import com.example.demo.mapper.ReviewMapper;
import com.example.demo.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewMapper reviewMapper;

    public List<Review> findAll(){
        return reviewMapper.findAll();
    }
    public void update(Review bean){
        reviewMapper.update(bean);
    }
    public void insert(Review bean){
        reviewMapper.insert(bean);
    }
    public void delete(int id){
        reviewMapper.delete(id);
    }
    public Review findById(int id){
        return reviewMapper.findById(id);

    }
   public List<Review> findByPid(int pid){
        return reviewMapper.findByPid(pid);
    }
    public List<Review> findByUid(int uid){
        return reviewMapper.findByUid(uid);
    }
}
