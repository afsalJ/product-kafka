package com.afsal.project2.service;


import com.afsal.project2.dto.ProductDto;
import com.afsal.project2.entity.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProduct(int page, int size);
    public Product getProductById(Integer id);
    public String saveOrUpdateProduct(ProductDto productDto);
    public String deleteProduct(Integer id);
    public String deleteAllProduct();
}
