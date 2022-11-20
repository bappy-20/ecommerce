package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Category;

public interface CategoryService {
    Optional<Category> findById(Long id);

    List<Category> findAll();

    Category getCategory(Long id);
    
    Category save(Category category, HttpServletRequest request);

    void deleteById(Long id,HttpServletRequest request);

    Category update(Category category, long id, HttpServletRequest request);

    Optional<Category> findByName(String name);

}
