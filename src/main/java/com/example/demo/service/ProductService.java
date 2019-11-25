package com.example.demo.service;

import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.pojo.Category;
import com.example.demo.pojo.Product;
import com.example.demo.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductImageService productImageService;

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
        List<ProductImage> productSingleImages = productImageService.findSingleByPid(p.getId());
        List<ProductImage> productDetailImages = productImageService.findDetailByPid(p.getId());
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
        if(!productSingleImages.isEmpty())
            p.setFirstProductImage(productSingleImages.get(0));
        return p;
    }
    public List<Product> findByCid(int cid){
        List<Product> ps = productMapper.findByCid(cid);
        for(Product p:ps){
            List<ProductImage> productSingleImages = productImageService.findSingleByPid(p.getId());
            List<ProductImage> productDetailImages = productImageService.findDetailByPid(p.getId());
            p.setProductSingleImages(productSingleImages);
            p.setProductDetailImages(productDetailImages);
            if(!productSingleImages.isEmpty())
                p.setFirstProductImage(productSingleImages.get(0));
        }

        return ps;
    }

    public List<Product>search(String keyword){
        List<Product> ps = productMapper.search(keyword);
        for(Product p:ps){
            List<ProductImage> productSingleImages = productImageService.findSingleByPid(p.getId());
            List<ProductImage> productDetailImages = productImageService.findDetailByPid(p.getId());
            p.setProductSingleImages(productSingleImages);
            p.setProductDetailImages(productDetailImages);
            if(!productSingleImages.isEmpty())
                p.setFirstProductImage(productSingleImages.get(0));
        }
        return ps;
    }
}
