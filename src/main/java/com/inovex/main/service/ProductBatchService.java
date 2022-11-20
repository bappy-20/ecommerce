package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.ProductBatch;

public interface ProductBatchService {

    Optional<ProductBatch> findById(Long id);

    List<ProductBatch> findAll();

    void deleteById(Long id, HttpServletRequest request);

    ProductBatch update(ProductBatch brand, Long id, HttpServletRequest request);

    ProductBatch save(ProductBatch entity, HttpServletRequest request);
    
    List<ProductBatch> findAllPrdBatchByProductdId(Long productId);

}
