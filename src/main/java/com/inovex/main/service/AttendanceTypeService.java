package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.AttendanceType;

public interface AttendanceTypeService {
    Optional<AttendanceType> findById(Long id);

    List<AttendanceType> findAll();

    AttendanceType update(AttendanceType entity);

    List<AttendanceType> saveAll(List<AttendanceType> entities);

    void deleteById(Long id);
    
    AttendanceType save(AttendanceType entity, HttpServletRequest request);
}
