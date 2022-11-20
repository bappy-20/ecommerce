package com.inovex.main.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Category;
import com.inovex.main.repository.ProductCategoryRepo;
import com.inovex.main.service.ProductCategoryService;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryRepo categoryRepo;

    @Override
    public Optional<Category> findById(Long id) {
        // TODO Auto-generated method stub
        return categoryRepo.findById(id);
    }

    @Override
    public List<Category> findAll() {
        // TODO Auto-generated method stub
        return categoryRepo.findAll();
    }

    @Override
    public Category save(Category entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        entity.setActive(true);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
        return categoryRepo.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepo.deleteById(id);
    }

}
