package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Market;

public interface MarketService {
    Optional<Market> findById(Long id);

    List<Market> findAll();

    Market getMarket(Long id);

    Market save(Market mkt, HttpServletRequest request);

    void deleteById(Long id,HttpServletRequest request);

    Market update(Market market, Long id, HttpServletRequest request);

    void saveUserMapping(List<Long> mktId, Long id);
}
