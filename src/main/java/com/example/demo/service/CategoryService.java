
package com.example.demo.service;

import com.example.demo.pojo.Category;

import com.example.demo.mapper.CategoryMapper;

import com.example.demo.pojo.OrderItem;
import com.example.demo.pojo.Product;
import com.example.demo.pojo.Property;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import com.example.demo.util.ImageUtil;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductService productService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    PropertyService propertyService;

    public List<Category> findAll(){
        List<Category> cs= categoryMapper.findAll();

        for(Category c :cs){
            List<Product>ps = productService.findByCid(c.getId());
            c.setProducts(ps);
            int sell = 0;
            for(Product p : ps){
                List<OrderItem> ois = orderItemService.findByPid(p.getId());
                for( OrderItem oi : ois){
                    if(oi.getOid()>0)
                        sell += oi.getNumber();
                }
            }
            c.setSell(sell);
        }
        return cs;

    }

    public void insert(Category bean){
        categoryMapper.insert(bean);

    }

    public void delete(int id){
        Category c = this.findById(id);
        if(!c.getProducts().isEmpty()){
            for(Product p:c.getProducts()){
                productService.delete(p.getId());
            }
        }
        if(!c.getPropertys().isEmpty()){
            for(Property py : c.getPropertys()){
                propertyService.delete(py.getId());
            }
        }
        categoryMapper.delete(id);

    }

    public Category findById(int id){
        Category c= categoryMapper.findById(id);
            List<Product>ps = productService.findByCid(c.getId());
            c.setProducts(ps);
            int sell = 0;
            for(Product p : ps){
                List<OrderItem> ois = orderItemService.findByPid(p.getId());
                for( OrderItem oi : ois){
                    if(oi.getOid()>0)
                        sell += oi.getNumber();
                }
            }
            c.setSell(sell);
        List<Property>pys = propertyService.findByCid(c.getId());
        c.setPropertys(pys);
        return c;

    }

    public void update(Category bean){
        categoryMapper.update(bean);
    }

}

