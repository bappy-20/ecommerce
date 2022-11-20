package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.CompanyModel;

public interface CompanyModelService {

    Optional<CompanyModel> findById(Long id);

    List<CompanyModel> findAll();

    void deleteById(Long id, HttpServletRequest request);

    CompanyModel update(CompanyModel brand, Long id, HttpServletRequest request);

    CompanyModel save(CompanyModel entity, HttpServletRequest request);

}
