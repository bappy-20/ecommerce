package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.PurchaseProduct;

public interface PurchaseOrderService {

    Optional<PurchaseProduct> findById(Long id);

    Optional<PurchaseProduct> findByPurchaseNumber(String purchaseNumber);

    List<PurchaseProduct> findAll();

    void deleteById(Long id, HttpServletRequest request);

    PurchaseProduct update(PurchaseProduct purchaseOrder, Long id, HttpServletRequest request);

    void save(HttpServletRequest request, long supplierId, long totalPrice, String purchaseNumber, long discount,
            long grandTotal, String purchaseDate, String purchaseNote, String producList);

    Optional<String> getTotalPurchase();

    Optional<String> getTotalPurchaseToday();

    Optional<String> getTotalPurchaseMonth();

    PurchaseProduct save(PurchaseProduct entity, HttpServletRequest request);

}
