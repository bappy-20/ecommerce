package com.inovex.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.VisitTarget;

public interface VisitTargetService {

    Optional<VisitTarget> findById(Long id);

    List<VisitTarget> findAll();

    VisitTarget getMonthlyTarget(Long id);

    //VisitTarget save(VisitTarget monthlyTarget, HttpServletRequest request);

    List<VisitTarget> saveAll(List<VisitTarget> monthlyTargets);

    void deleteById(Long id);

    VisitTarget update(VisitTarget monthlyTarget, Long id, HttpServletRequest request);

    Optional<VisitTarget> findAllOfCurrentMonth(String employeeId, Date targetMonth1);

    Optional<VisitTarget> findAllOfCurrentMonth(String employeeId);
    
    VisitTarget save(VisitTarget entity, HttpServletRequest request);
}
