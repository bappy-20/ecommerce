package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Category;

@Repository
public interface ProductCategoryRepo extends JpaRepository<Category, Long> {

}
