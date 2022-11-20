package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.MonthlyTarget;

public interface TargetService {
    Optional<MonthlyTarget> findById(Long id);

    List<MonthlyTarget> findAll();

    MonthlyTarget save(MonthlyTarget entity, HttpServletRequest request);

    List<MonthlyTarget> saveAll(List<MonthlyTarget> entities);

    void deleteById(Long id);
}
