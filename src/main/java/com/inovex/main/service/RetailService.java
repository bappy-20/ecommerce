package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Retail;

public interface RetailService {
    Optional<Retail> findById(Long id);

    List<Retail> findAll();

    Retail getRetail(Long id);

    void deleteById(long id, HttpServletRequest request);

    Retail update(Retail retail, long id, HttpServletRequest request);

    List<Retail> findAllByEmpId(String submittedBy);

    List<Retail> findAllPendingRetail();

    List<Retail> findAllApprovedRetail();

    Retail ApproveRetail(long id, HttpServletRequest request);

    Retail save(Retail entity, long orgId);

    Retail save(Retail entity, HttpServletRequest request);
}
