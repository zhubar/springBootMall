package com.example.demo.mapper;

import com.example.demo.pojo.Category;
import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> findAll();
    void update(User bean);
    void insert(User bean);
    void delete(int id);
    User findById(int id);
    User findByName(String name);
    User findByNameAndPassword(String name,String password);
}
