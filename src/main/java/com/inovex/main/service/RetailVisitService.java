package com.inovex.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.RetailVisit;

public interface RetailVisitService {
    Optional<RetailVisit> findById(Long id);

    List<RetailVisit> findAll();

    void deleteById(Long id);

    RetailVisit update(RetailVisit retail, long id);

    List<RetailVisit> findAllByEmpId(long submittedBy);

    List<RetailVisit> findAllOfCurrentMonth(String employeeId);

    List<RetailVisit> findAllByEmpIdAndCurDate(String employeeId);

    List<RetailVisit> getReatilVisitOfAnEmployeeByName(String employeeId, Date selectedDate);

    RetailVisit save(RetailVisit entity, long orgId);

    RetailVisit save(RetailVisit entity, HttpServletRequest request);
}
