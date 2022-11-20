package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.AttendanceTimeSetup;

public interface AttendanceTimeSetupService {
    List<AttendanceTimeSetup> findAll();

    Optional<AttendanceTimeSetup> findById(long id);
    
    AttendanceTimeSetup save(AttendanceTimeSetup entity, HttpServletRequest request);
}
