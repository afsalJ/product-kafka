package com.afsal.project2.dao;

import com.afsal.project2.entity.Product;

import java.util.List;

public interface ProductDao {
    void deleteById(Integer id);
    void deleteAll();
    Product isPresent(Product product);
}
