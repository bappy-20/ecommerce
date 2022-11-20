package com.inovex.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.LeaveModel;

public interface LeaveService {
    Optional<LeaveModel> findById(Long id);

    List<LeaveModel> findAll();

    LeaveModel save(LeaveModel entity, long orgId);

    LeaveModel save(LeaveModel entity, HttpServletRequest request);

    LeaveModel save1(LeaveModel entity, HttpServletRequest request);

    List<LeaveModel> saveAll(List<LeaveModel> entities);

    void deleteById(Long id,HttpServletRequest request);

    List<LeaveModel> getAllBetweenDates(Date startDate, Date endDate, long empId);

    List<LeaveModel> findAllByEmpId(long submittedBy);

    LeaveModel update(LeaveModel leave, long id, HttpServletRequest request);

    LeaveModel getLeave(Long id);

    List<LeaveModel> getPendingLeaveMethod();

    LeaveModel approveStatus(Long id);

    public List<LeaveModel> getAllPendingLeave1();

    List<LeaveModel> getApprovedLeave();

    LeaveModel rejectLave(Long id);
}
