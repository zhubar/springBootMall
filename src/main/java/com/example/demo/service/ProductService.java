package com.example.demo.service;

import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.pojo.Category;
import com.example.demo.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;

    public void insert(Product bean){
        productMapper.insert(bean);
    }

    public void update(Product bean){
        productMapper.update(bean);
    }
    public void delete(int id){
        productMapper.delete(id);

    }
    public Product findById(int id){
        Product p = productMapper.findById(id);
        Category c = categoryMapper.findById(p.getCid());
        p.setCategory(c);

        return p;
    }
    public List<Product> findByCid(int cid){
        List<Product> ps = productMapper.findByCid(cid);
        for(Product p : ps){
            Category c = categoryMapper.findById(p.getCid());
            p.setCategory(c);
        }
        return ps;
    }

    public List<Product>search(String keyword){
        return productMapper.search(keyword);
    }
}
