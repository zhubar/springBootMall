package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public List<User> findAll(){
        return userMapper.findAll();
    }
    public void update(User bean){
        userMapper.update(bean);
    }
    public void insert(User bean){
        userMapper.insert(bean);

    }
    public void delete(int id){
        userMapper.delete(id);

    }
    public User findById(int id){
        return userMapper.findById(id);
    }
    public User findByNameAndPassword(String name,String password){
        return userMapper.findByNameAndPassword(name,password);
    }
    public User findByName(String name){
        return userMapper.findByName(name);
    }
}
