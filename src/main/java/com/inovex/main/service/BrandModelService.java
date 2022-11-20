package com.inovex.main.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.BrandModel;

public interface BrandModelService {
    List<BrandModel> findAllByCompanyId(Long companyId);

    Optional<BrandModel> findById(Long id);

    List<BrandModel> findAll();

    Set<BrandModel> findAllByOrgId(Long orgId);

    void deleteById(Long id, HttpServletRequest request);

    BrandModel update(BrandModel brand, Long id, HttpServletRequest request);

    BrandModel save(BrandModel entity, HttpServletRequest request);

}
