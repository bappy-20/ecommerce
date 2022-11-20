package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;

import com.inovex.main.entity.ReturnPurchaseProduct;

public interface ReturnPurchaseProductService {
    Optional<ReturnPurchaseProduct> findById(Long id);

    List<ReturnPurchaseProduct> findAll();

    ReturnPurchaseProduct save(ReturnPurchaseProduct entity, HttpServletRequest request);

    void deleteById(Long id,HttpServletRequest request);

    ReturnPurchaseProduct update(ReturnPurchaseProduct returnPurchase, Long id, HttpServletRequest request);

    void save(long supplierId, long totalPrice, String purchaseNumber, String purchaseDate, String purchaseNote,
            JSONArray producList, HttpServletRequest request);

    Optional<ReturnPurchaseProduct> findByPurchaseNumber(String purchaseNumber);
}
