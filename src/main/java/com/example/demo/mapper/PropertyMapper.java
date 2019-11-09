package com.example.demo.mapper;

import com.example.demo.pojo.Property;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PropertyMapper {
    void insert(Property bean);
    void update(Property bean);
    void delete(int id);
    List<Property> findByCid(int cid);
    Property findById(int id);

}
