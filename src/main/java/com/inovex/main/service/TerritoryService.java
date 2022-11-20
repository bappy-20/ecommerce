package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.TerritoryModel;

public interface TerritoryService {
    Optional<TerritoryModel> findById(Long id);

    List<TerritoryModel> findAll();

    TerritoryModel getTerritory(Long id);

    void deleteById(long id, HttpServletRequest request);

    TerritoryModel update(TerritoryModel territory, Long id, HttpServletRequest request);

    TerritoryModel save(TerritoryModel entity, HttpServletRequest request);

    void saveUserMapping(List<Long> territoryId, Long id);

}
