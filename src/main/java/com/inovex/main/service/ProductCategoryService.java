package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Category;

public interface ProductCategoryService {
    Optional<Category> findById(Long id);

    List<Category> findAll();

    Category save(Category cat, HttpServletRequest request);

    void deleteById(Long id);
}
