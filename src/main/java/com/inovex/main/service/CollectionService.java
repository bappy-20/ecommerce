package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.CollectionModel;

public interface CollectionService {
    Optional<CollectionModel> findById(Long id);

    List<CollectionModel> findAll();

    List<CollectionModel> saveAll(List<CollectionModel> entities);

    void deleteById(Long id, HttpServletRequest request);

    public List<CollectionModel> findCollectionDetails(String orderId);

    public long getReceivedAmount(String orderId);

    CollectionModel save(CollectionModel entity, long orgId);

    CollectionModel save(CollectionModel entity, HttpServletRequest request);
    
    List<Object> findAllByDateRange(HttpServletRequest request, String date1,String date2);
    
    List<Object> findtotalSumByDateRange(HttpServletRequest request, String date1,String date2);
}
