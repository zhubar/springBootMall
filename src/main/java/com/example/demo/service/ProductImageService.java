package com.example.demo.service;

import com.example.demo.mapper.ProductImageMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.pojo.Product;
import com.example.demo.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    @Autowired
    ProductImageMapper productImageMapper;
    @Autowired
    ProductMapper productMapper;

    public void insert(ProductImage bean){
        productImageMapper.insert(bean);

    }
    public void update(ProductImage bean){
        productImageMapper.update(bean);
    }
    public void delete(int id){
        productImageMapper.delete(id);
    }
    public ProductImage findById(int id){
        ProductImage pi = productImageMapper.findById(id);
        Product p = productMapper.findById(pi.getPid());
        pi.setProduct(p);
        return pi;
    }
    public List<ProductImage> findSingleByPid(int pid){
        List<ProductImage> pis = productImageMapper.findSingleByPid(pid);
        for(ProductImage pi : pis){
            Product p = productMapper.findById(pi.getPid());
            pi.setProduct(p);
        }
        return pis;

    }
    public List<ProductImage> findDetailByPid(int pid){
        List<ProductImage> pis = productImageMapper.findDetailByPid(pid);
        for(ProductImage pi : pis){
            Product p = productMapper.findById(pi.getPid());
            pi.setProduct(p);
        }
        return pis;

    }
}
