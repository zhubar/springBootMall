package com.example.demo.service;

import com.example.demo.mapper.PropertyMapper;
import com.example.demo.pojo.Property;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    PropertyMapper propertyMapper;

    public void insert(Property bean){
        propertyMapper.insert(bean);

    }
    public void update(Property bean){
        propertyMapper.update(bean);

    }
    public void delete(int id){
        propertyMapper.delete(id);

    }
    public List<Property> findByCid(int cid){
        return propertyMapper.findByCid(cid);
    }
    public Property findById(int id){
        return propertyMapper.findById(id);
    }
}
