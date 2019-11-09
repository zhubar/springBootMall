package com.example.demo.service;

import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.PropertyMapper;
import com.example.demo.mapper.PropertyValueMapper;
import com.example.demo.pojo.Product;
import com.example.demo.pojo.Property;
import com.example.demo.pojo.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueService {
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyMapper propertyMapper;
    @Autowired
    ProductMapper productMapper;

    public void insert(PropertyValue bean){
        propertyValueMapper.insert(bean);

    }
    public void update(PropertyValue bean){
        propertyValueMapper.update(bean);

    }
    public void delete(int id){
       propertyValueMapper.delete(id);

    }
    public List<PropertyValue> findByPid(int pid){
        List<PropertyValue> pvs = propertyValueMapper.findByPid(pid);
        for(PropertyValue pv :pvs){
            Product product = productMapper.findById(pv.getPid());
            Property property = propertyMapper.findById(pv.getPtid());
            pv.setProduct(product);
            pv.setProperty(property);

        }
        return pvs;
    }
    public PropertyValue findById(int id){
        PropertyValue pv = propertyValueMapper.findById(id);
        Product product = productMapper.findById(pv.getPid());
        Property property = propertyMapper.findById(pv.getPtid());
        pv.setProduct(product);
        pv.setProperty(property);

        return pv;
    }
}
