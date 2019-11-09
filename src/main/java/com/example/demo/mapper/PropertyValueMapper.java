package com.example.demo.mapper;


import com.example.demo.pojo.Property;
import com.example.demo.pojo.PropertyValue;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PropertyValueMapper {

    void insert(PropertyValue bean);
    void update(PropertyValue bean);
    void delete(int id);
    List<PropertyValue> findByPid(int pid);
    PropertyValue findById(int id);
}
