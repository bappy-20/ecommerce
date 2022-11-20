package com.inovex.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.MonthlyOrderTarget;

public interface MonthlyOrderTargetService {

    Optional<MonthlyOrderTarget> findById(Long id);

    List<MonthlyOrderTarget> findAll();

    MonthlyOrderTarget getMonthlyTarget(Long id);

    List<MonthlyOrderTarget> saveAll(List<MonthlyOrderTarget> monthlyTargets, HttpServletRequest request);

    void deleteById(Long id,HttpServletRequest request);

    MonthlyOrderTarget update(MonthlyOrderTarget monthlyTarget, Long id, HttpServletRequest request);

    Optional<MonthlyOrderTarget> findAllOfCurrentMonth(String employeeId, Date targetMonth1);

    List<MonthlyOrderTarget> findAllByCurMonth(HttpServletRequest request);

    List<MonthlyOrderTarget> findAllByMonth(Date targetMonth1);

    Optional<MonthlyOrderTarget> findAllByCurMonthAndEmpId(String employeeId);

    MonthlyOrderTarget save(MonthlyOrderTarget entity, HttpServletRequest request);
    
    List<Object> findAllDeliveryEMployeeOfSelectedMonth7(HttpServletRequest request, String date);
}
