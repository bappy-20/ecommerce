package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.AttendanceTime;

public interface AttendanceTimeService {

    Optional<AttendanceTime> findById(Long id);

    List<AttendanceTime> findAll();

    List<AttendanceTime> saveAll(List<AttendanceTime> entities);

    void deleteById(Long id);
    
    AttendanceTime save(AttendanceTime entity, HttpServletRequest request);
}
