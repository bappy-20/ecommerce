package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.ProductSubCategory;

public interface ProductSubCategoryService {
    Optional<ProductSubCategory> findById(Long id);

    List<ProductSubCategory> findAll();

    ProductSubCategory getCategory(Long id);

    ProductSubCategory save(ProductSubCategory category, HttpServletRequest request);

    void deleteById(Long id, HttpServletRequest request);

    ProductSubCategory update(ProductSubCategory category, long id, HttpServletRequest request);

    Optional<ProductSubCategory> findByName(String name);

}
