package com.inovex.main.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.DistributorRequisition;
import com.inovex.main.entity.DistributorRequisitionResponse;

public interface DistributorRequisitionService {

    Optional<DistributorRequisition> findById(Long id);

    List<DistributorRequisition> findAll();

    List<DistributorRequisitionResponse> findAllResponse(HttpServletRequest request);

    public List<DistributorRequisitionResponse> findAllConfrimRequiResponse(HttpServletRequest request);

    public List<DistributorRequisitionResponse> findAllProcessedRequisitionResponse(HttpServletRequest request);

    public List<DistributorRequisitionResponse> findAllApprovedRequisitionResponse(HttpServletRequest request);

    void deleteById(Long id, HttpServletRequest request);

    DistributorRequisition update(DistributorRequisition entity, Long id, HttpServletRequest request);

    DistributorRequisition updateByOtherOperation(DistributorRequisition entity, Long id, HttpServletRequest request);

    DistributorRequisition update(Long reqId, String dept);

    DistributorRequisition save(DistributorRequisition entity, HttpServletRequest request);

    List<DistributorRequisitionResponse> findAllVerifiedRequisitionResponse(HttpServletRequest request);

    List<DistributorRequisitionResponse> findAllDeliveredRequisitionResponse(HttpServletRequest request);

    List<DistributorRequisitionResponse> findAllReceivedRequisitionResponse(HttpServletRequest request);

    DistributorRequisition update1(DistributorRequisition entity, Long id, HttpServletRequest request);
    
    List<DistributorRequisition> findDistReqByDealerId(HttpServletRequest request,Long id);

}
