package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.ProductMapping;

public interface ProductMappingService {

    Optional<ProductMapping> findById(Long id);

    Optional<ProductMapping> findByProductId(Long productId);

    List<ProductMapping> findAll();

    ProductMapping getProduct(Long id);

    ProductMapping save(ProductMapping product, HttpServletRequest request);

    void deleteById(Long id, HttpServletRequest request);

    ProductMapping update(ProductMapping product, Long id, HttpServletRequest request);

    public List<ProductMapping> getproductById(long productCategory);
    
    Long getProductId(Long id);

}
