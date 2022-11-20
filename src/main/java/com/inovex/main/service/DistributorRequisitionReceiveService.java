package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.DistributorRequisitionReceive;

public interface DistributorRequisitionReceiveService {

    Optional<DistributorRequisitionReceive> findById(Long id);

    List<DistributorRequisitionReceive> findAll();

    void deleteById(Long id);

    DistributorRequisitionReceive update(DistributorRequisitionReceive entity, Long id, HttpServletRequest request);

    DistributorRequisitionReceive save(DistributorRequisitionReceive entity, HttpServletRequest request);

}
