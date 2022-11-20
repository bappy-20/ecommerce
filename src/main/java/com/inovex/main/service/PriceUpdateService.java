package com.inovex.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.PriceUpdateHistory;

public interface PriceUpdateService {
    Optional<PriceUpdateHistory> findById(Long id);

    Optional<PriceUpdateHistory> findByProductId(Long productId);

    List<PriceUpdateHistory> findAll();

    PriceUpdateHistory save(PriceUpdateHistory entity, HttpServletRequest request);

    List<PriceUpdateHistory> saveAll(List<PriceUpdateHistory> entities);

    void deleteById(Long id);

    List<PriceUpdateHistory> findAllByProduct(long productId, Date startDate, Date endDate);

    public Optional<Long> countTotalRecieve(long productId);
}
