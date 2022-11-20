package com.inovex.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Attendance;

public interface AttendanceService {

    Optional<Attendance> findById(Long id);

    List<Attendance> findAll();

    List<Attendance> saveAll(List<Attendance> entities);

    void deleteById(Long id);

    List<Attendance> getAllBetweenDates(Date startDate, Date endDate, String empId);

    List<Object> findAllOfCurrentMonth(String employeeId);

    List<Attendance> getAttendanceDayWise(Date date1);

    List<Attendance> getAttendanceBetweenDates(Date stDate, Date enDate);

    public List<Object> getAllEmployeeAttendanceSummeryByDate(Date startDate, Date endDate, HttpServletRequest request);

    List<Attendance> getAttendanceOfAnEmployee(String employeeId, Date startDate, Date endDate);

    List<Attendance> getAttendanceOfAnEmployeeByName(String employeeId, Date startDate, Date endDate);

    Optional<Attendance> findByEmpIdAndLogDate(String empId);

    List<Attendance> findAllByCurDate();

    public List<Object> getAttendanceSummeryByADate(String startDate);

    Attendance save(Attendance entity, long orgId);

    Attendance save(Attendance entity, HttpServletRequest request);
}
