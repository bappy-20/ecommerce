package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.RegionModel;

public interface RegionModelService {

    Optional<RegionModel> findById(Long id);

    List<RegionModel> findAll();

    RegionModel save(RegionModel entity, HttpServletRequest request);

    RegionModel update(RegionModel entity, Long id, HttpServletRequest request);

    List<RegionModel> saveAll(List<RegionModel> entities, HttpServletRequest request);

    void deleteById(long id, HttpServletRequest request);

    void saveUserMapping(List<Long> regionId, Long id);
}
