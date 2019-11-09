
package com.example.demo.service;

import com.example.demo.pojo.Category;

import com.example.demo.mapper.CategoryMapper;

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

    public List<Category> findAll(){

        return categoryMapper.findAll();

    }

    public void insert(Category bean){
        categoryMapper.insert(bean);

    }

    public void delete(int id){
        categoryMapper.delete(id);

    }

    public Category findById(int id){
        return categoryMapper.findById(id);
    }

    public void update(Category bean){
        categoryMapper.update(bean);
    }

}

