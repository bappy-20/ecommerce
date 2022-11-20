package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.IncomingProduct;

public interface IncomingProductService {

    Optional<IncomingProduct> findById(Long id);

    Optional<IncomingProduct> findByProductId(Long productId);

    List<IncomingProduct> findAll();

    IncomingProduct getIncomingProduct(Long id);

    IncomingProduct save(IncomingProduct incomingProduct, HttpServletRequest request);

    void deleteById(Long id);

    IncomingProduct update(IncomingProduct incomingProduct, Long id, HttpServletRequest request);

    public Long countTotalRecieve(long productId);

}
