package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;

import com.inovex.main.entity.ReturnOrder;

public interface ReturnOrderService {
    Optional<ReturnOrder> findById(Long id);

    List<ReturnOrder> findAll();

    void deleteById(Long id);

    ReturnOrder update(ReturnOrder returnPurchase, Long id, HttpServletRequest request);

//    void save(long retailId, long salePrice, String orderNumber, String returnDate, String returnNote,
//            String producList);
    
    ReturnOrder save(ReturnOrder entity, HttpServletRequest request);
    
    void save1(long retailId, long salePrice, String orderNumber, String returnDate, String returnNote,
          String producList, HttpServletRequest request );
    
    void save2(long retailId, long salePrice, String orderNumber, String returnDate, String returnNote,
    		 JSONArray producList, HttpServletRequest request );
    
    
}
