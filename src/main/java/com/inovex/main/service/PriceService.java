package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Price;
import com.inovex.main.json.response.PriceResponse;

public interface PriceService {
	
    Optional<Price> findById(Long id);

    Optional<Price> findByProductId(Long productId);
    
    List<Price> findAll();

    Price getPrice(Long id);

    Price save(Price entity, HttpServletRequest request);

    List<Price> saveAll(List<Price> entities);

    void deleteById(Long id, HttpServletRequest request);

    Price update(Price price, Long id, HttpServletRequest request);
    
    PriceResponse getPrice1(Long id);
}
