package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.AreaModel;

public interface AreaService {

    Optional<AreaModel> findById(Long id);

    List<AreaModel> findAll();

    AreaModel getArea(Long id);

    void deleteById(Long id, HttpServletRequest request);

    AreaModel update(AreaModel area, Long id, HttpServletRequest request);

    AreaModel save(AreaModel entity, HttpServletRequest request);

    void saveAreaMapping(List<Long> areaId, Long id);
}
