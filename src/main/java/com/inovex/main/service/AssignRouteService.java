package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.AssignRouteModel;

public interface AssignRouteService {
    Optional<AssignRouteModel> findById(Long id);

    List<AssignRouteModel> findAll();

    AssignRouteModel save(AssignRouteModel entity, HttpServletRequest request);

    List<AssignRouteModel> saveAll(List<AssignRouteModel> entities);

    void deleteById(Long id);
}
