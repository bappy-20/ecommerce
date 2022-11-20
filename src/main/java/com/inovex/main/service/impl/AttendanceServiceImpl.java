package com.inovex.main.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Attendance;
import com.inovex.main.entity.AttendanceTime;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.AttendanceRepo;
import com.inovex.main.repository.AttendanceTimeRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.AttendanceService;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepo attendanceRepo;
    @Autowired
    AttendanceTimeRepo attendanceTimeRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<Attendance> findById(Long id) {
        // TODO Auto-generated method stub
        return attendanceRepo.findById(id);
    }

    @Override
    public List<Attendance> findAll() {
        // TODO Auto-generated method stub
        return attendanceRepo.findAll();
    }

    @Override
    public List<Attendance> saveAll(List<Attendance> entities) {
        // TODO Auto-generated method stub
        return attendanceRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        attendanceRepo.deleteById(id);
    }

    @Override
    public List<Attendance> getAllBetweenDates(Date startDate, Date endDate, String empId) {
        // TODO Auto-generated method stub
        return attendanceRepo.getAllBetweenDates(startDate, endDate, empId);
    }

    @Override
    public List<Object> findAllOfCurrentMonth(String employeeId) {
        // TODO Auto-generated method stub
        return attendanceRepo.findAllOfCurrentMonth(employeeId);
    }

    @Override
    public List<Attendance> getAttendanceDayWise(Date date1) {
        // TODO Auto-generated method stub
        return attendanceRepo.getAttendanceDayWise(date1);
    }

    @Override
    public List<Attendance> getAttendanceBetweenDates(Date stDate, Date enDate) {
        // TODO Auto-generated method stub
        return attendanceRepo.getAttendanceBetweenTwodates(stDate, enDate);
    }

    @Override
    public List<Object> getAllEmployeeAttendanceSummeryByDate(Date startDate, Date endDate,
            HttpServletRequest request) {
        List<Object> obj = new ArrayList<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            obj = attendanceRepo.getAllEmployeeAttendanceSummeryByDate(startDate, endDate, id);
        }
        return obj;
    }

    @Override
    public List<Attendance> getAttendanceOfAnEmployee(String employeeId, Date startDate, Date endDate) {
        // TODO Auto-generated method stub
        return attendanceRepo.getAttendanceofAnEmployee(employeeId, startDate, endDate);
    }

    @Override
    public List<Attendance> getAttendanceOfAnEmployeeByName(String employeeId, Date startDate, Date endDate) {
        // TODO Auto-generated method stub
        return attendanceRepo.getAttendanceofAnEmployeeByName(employeeId, startDate, endDate);
    }

    @Override
    public Optional<Attendance> findByEmpIdAndLogDate(String empId) {
        // TODO Auto-generated method stub
        return attendanceRepo.findByEmpIdAndLogDate(empId);
    }

    @Override
    public List<Attendance> findAllByCurDate() {
        // TODO Auto-generated method stub
        return attendanceRepo.findAllByCurDate();
    }

    @Override
    public List<Object> getAttendanceSummeryByADate(String startDate) {
        // TODO Auto-generated method stub
        return attendanceRepo.getAttendanceSummeryByADate(startDate);
    }

    @Override
    public Attendance save(Attendance entity, HttpServletRequest request) {
        Attendance attendance = new Attendance();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<Attendance> list = new HashSet<Attendance>();
            if (org.isPresent()) {
                list = org.get().getAttendances();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                attendance = attendanceRepo.save(entity);
                list.add(attendance);
                org.get().setAttendances(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

    @Override
    public Attendance save(Attendance entity, long orgId) {
        Attendance attendance = new Attendance();
        Optional<Organization> org = orgRepo.findById(orgId);
        Set<Attendance> list = new HashSet<Attendance>();
        long id = 1;
        System.out.println("entity " + entity.getAttendanceType());
        if (org.isPresent()) {
            /*
             * list = org.get().getAttendances(); entity.setCreatedAt(new Date());
             * entity.setActive(true); entity.setUpdatedAt(new Date());
             * entity.setCreatedBy(0); attendance = attendanceRepo.save(entity);
             * list.add(attendance); org.get().setAttendances(list);
             * orgRepo.save(org.get());
             */

            Optional<AttendanceTime> attenTime = attendanceTimeRepo.findById(id);
            String definedInTime = "00:00:00";
            String definedOutTime = "00:00:00";
            SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm:ss");
            if (attenTime.isPresent()) {

                definedInTime = printFormat.format(attenTime.get().getInTime());
                definedOutTime = printFormat.format(attenTime.get().getOutTime());
            }

            if (entity.getAttendanceType().equals("in") || entity.getAttendanceType() == "in") {
                Optional<Attendance> checkExistorNot = attendanceRepo.findByEmpIdAndLogDate(entity.getEmployeeId());
                if (checkExistorNot.isPresent()) {
                    System.out.println("attendance already given ");

                } else {
                    String userGivenInTime = printFormat.format(entity.getInTime());
                    if (userGivenInTime.compareTo(definedInTime) > 0) {
                        entity.setStatus("Lat-In");
                    } else {
                        entity.setStatus("Pending");
                    }
                    entity.setCreatedAt(new Date());
                    entity.setActive(true);
                    entity.setUpdatedAt(new Date());
                    entity.setCreatedBy(1);
                    attendance = attendanceRepo.save(entity);
                    list = org.get().getAttendances();
                    list.add(entity);
                    org.get().setAttendances(list);
                    orgRepo.save(org.get());
                }

            } else {

                Optional<Attendance> checkExistorNot = attendanceRepo.findByEmpIdAndLogDate(entity.getEmployeeId());
                if (checkExistorNot.isPresent()) {

                    checkExistorNot.get().setOutTime(entity.getOutTime());
                    checkExistorNot.get().setUpdatedAt(new Date());
                    checkExistorNot.get().setComment(entity.getComment());
                    String userGivenOutTime = printFormat.format(entity.getOutTime());
                    if (userGivenOutTime.compareTo(definedOutTime) < 0) {
                        System.out.println("userGivenOutTime is smaller definedOutTime");
                        if (checkExistorNot.get().getStatus().equals("Pending")
                                || checkExistorNot.get().getStatus() == "Pending") {
                            checkExistorNot.get().setStatus("Early-Out");
                        } else {
                            checkExistorNot.get().setStatus(checkExistorNot.get().getStatus());
                        }

                    } else {
                        checkExistorNot.get().setStatus("Present");
                    }

                    attendance = attendanceRepo.save(checkExistorNot.get());
                }

            }
        }
        return attendance;

    }

}
