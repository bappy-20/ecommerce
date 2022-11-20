package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.RouteModel;

public interface RouteModelService {

    Optional<RouteModel> findById(Long id);

    List<RouteModel> findAll();

    RouteModel getArea(Long id);

    RouteModel save(RouteModel area, HttpServletRequest request);

    void deleteById(Long routeId, HttpServletRequest request);

    RouteModel update(RouteModel entity, Long id);

}
