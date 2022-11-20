package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.RetailType;

public interface RetailTypeService {

    Optional<RetailType> findById(Long id);

    List<RetailType> findAll();

    RetailType getRetailType(Long id);

    List<RetailType> saveAll(List<RetailType> entities);

    void deleteById(long id, HttpServletRequest request);

    RetailType update(RetailType retailType, long id, HttpServletRequest request);
    
    RetailType save(RetailType entity, HttpServletRequest request);
}
