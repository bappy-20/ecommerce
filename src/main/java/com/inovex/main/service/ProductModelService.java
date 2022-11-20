package com.inovex.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.ProductModel;

public interface ProductModelService {
    Optional<ProductModel> findById(Long id);

    List<ProductModel> findAll();

    ProductModel getProduct(Long id);

    ProductModel save(ProductModel product, HttpServletRequest request);

    void deleteById(Long id, HttpServletRequest request);

    ProductModel update(ProductModel product, Long id, HttpServletRequest request);

    public ArrayList<Object> getPagination(int start, int length, String searchParam, HttpServletRequest request);

    public Long getCountWithSearchParm(String searchParam, long orgId);
    
    ProductModel getProduct1(Long id);

}
