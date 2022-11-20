package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.MeasurementUnit;

public interface MeasurementUnitService {
    Optional<MeasurementUnit> findById(Long id);

    List<MeasurementUnit> findAll();

    MeasurementUnit getMeasurementUnit(Long id);

    void deleteById(Long id, HttpServletRequest request);

    MeasurementUnit update(MeasurementUnit category, long id, HttpServletRequest request);
    
    MeasurementUnit save(MeasurementUnit entity, HttpServletRequest request);
}
