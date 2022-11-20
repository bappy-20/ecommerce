package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.RetailComplain;

public interface RetailComplainService {
    Optional<RetailComplain> findById(Long id);

    List<RetailComplain> findAll();

    void deleteById(long id, HttpServletRequest request);

    RetailComplain update(RetailComplain area, Long id);

    List<RetailComplain> findAllByEmployeeId(String employeeId);

    RetailComplain getRetailComplain(Long id);

    RetailComplain save(RetailComplain entity, long orgId);

    RetailComplain save(RetailComplain entity, HttpServletRequest request);
}
