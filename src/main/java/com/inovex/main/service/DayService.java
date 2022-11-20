package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.DayModel;

public interface DayService {
    Optional<DayModel> findById(Long id);

    List<DayModel> findAll();

    DayModel getDayModel(Long id);

    List<DayModel> getDayListByEmpId(String empId);

    List<DayModel> saveAll(List<DayModel> entities);

    void deleteById(Long id, HttpServletRequest request);

    DayModel update(DayModel dayModel, Long id, HttpServletRequest request);
    
    DayModel save(DayModel entity, HttpServletRequest request);   
}
