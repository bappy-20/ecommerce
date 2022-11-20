package com.inovex.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.MonthlyTarget;

public interface MonthlyTargetService {

    Optional<MonthlyTarget> findById(Long id);

    List<MonthlyTarget> findAll();

    MonthlyTarget getMonthlyTarget(Long id);

    List<MonthlyTarget> saveAll(Set<MonthlyTarget> monthlyTargets, HttpServletRequest request);

    void deleteById(Long id, HttpServletRequest request);

    MonthlyTarget update(MonthlyTarget monthlyTarget, Long id, HttpServletRequest request);

    List<MonthlyTarget> findAllOfCurrentMonth(String employeeId, long orgId);

    List<Object> findAllEmployeeOfCurrentMonth(HttpServletRequest request);

    Optional<MonthlyTarget> findExistorNotOfCurrentMonth(String productName, String employeeId, Date date);

    MonthlyTarget save(MonthlyTarget entity, HttpServletRequest request);  
    
    List<Object> findAllEMployeeOfSelectedMonth7(HttpServletRequest request, String date);


}
