package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.DistributorRequisitionProduct;

public interface DistributorRequisitionProductService {

    Optional<DistributorRequisitionProduct> findById(Long id);

    List<DistributorRequisitionProduct> findAll();

    void deleteById(Long id);

    DistributorRequisitionProduct update(DistributorRequisitionProduct entity, Long id, HttpServletRequest request);

    DistributorRequisitionProduct save(DistributorRequisitionProduct entity, HttpServletRequest request);
}
