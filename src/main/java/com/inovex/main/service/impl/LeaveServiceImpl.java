package com.inovex.main.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Attendance;
import com.inovex.main.entity.LeaveModel;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.AttendanceRepo;
import com.inovex.main.repository.LeaveRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.LeaveService;

@Service
@Transactional
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    LeaveRepo leaveRepo;
    @Autowired
    AttendanceRepo attenRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<LeaveModel> findById(Long id) {
        // TODO Auto-generated method stub
        return leaveRepo.findById(id);
    }

    @Override
    public List<LeaveModel> findAll() {
        // TODO Auto-generated method stub
        return leaveRepo.findAll();
    }

    @Override
    public LeaveModel save(LeaveModel entity, HttpServletRequest request) {
        if (entity.getFromDate() != null && entity.getToDate() != null) {
            long difference = Math.abs(entity.getFromDate().getTime() - entity.getToDate().getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);
            String dayDifference = Long.toString(differenceDates);
            entity.setDayCount(Integer.parseInt(dayDifference) + 1);
        } else {
            entity.setDayCount(0);
        }

        entity.setActive(true);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        entity.setCreatedBy(0);
        entity.setStatus("Pending");
        return leaveRepo.save(entity);
    }

    @Override
    public LeaveModel save1(LeaveModel entity, HttpServletRequest request) {
        LeaveModel leave1 = new LeaveModel();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<LeaveModel> list = new HashSet<LeaveModel>();
            if (org.isPresent()) {
                list = org.get().getLeaveModel();

                if (entity.getFromDate() != null && entity.getToDate() != null) {
                    long difference = Math.abs(entity.getFromDate().getTime() - entity.getToDate().getTime());
                    long differenceDates = difference / (24 * 60 * 60 * 1000);
                    String dayDifference = Long.toString(differenceDates);
                    entity.setDayCount(Integer.parseInt(dayDifference) + 1);
                } else {
                    entity.setDayCount(0);
                }

                entity.setActive(true);
                entity.setCreatedAt(new Date());
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                entity.setStatus("Approved");

                leave1 = leaveRepo.save(entity);
                if (leave1 != null) {
                    Instant timestamp1 = entity.getFromDate().toInstant();
                    Instant timestamp2 = entity.getToDate().toInstant();
                    LocalDateTime date1 = LocalDateTime.ofInstant(timestamp1, ZoneId.systemDefault());
                    LocalDateTime date2 = LocalDateTime.ofInstant(timestamp2, ZoneId.systemDefault());
                    long numOfDaysBetween = ChronoUnit.DAYS.between(date1, date2) + 1;

                    List<LocalDateTime> dates = IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween)
                            .mapToObj(i -> date1.plusDays(i)).collect(Collectors.toList());
                    for (LocalDateTime localDateTime : dates) {
                        Attendance att = new Attendance();
                        Instant instant = Timestamp
                                .valueOf(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .toInstant();
                        Date date = Date.from(instant);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String format = formatter.format(date);
                        Date selectedDate;
                        try {
                            selectedDate = formatter.parse(format);
                            Optional<Attendance> exist = attenRepo.findExistOrNot(selectedDate, entity.getEmployeeId());
                            if (!exist.isPresent()) {
                                att.setCreatedAt(date);
                                att.setUpdatedAt(date);
                                att.setEmployeeId(entity.getEmployeeId());
                                att.setComment(entity.getComment());
                                att.setLogDate(date);
                                att.setStatus("On-Leave");
                                attenRepo.save(att);
                            } else {
                                exist.get().setEmployeeId(entity.getEmployeeId());
                                exist.get().setComment(entity.getComment());
                                exist.get().setLogDate(date);
                                exist.get().setStatus("On-Leave");
                                attenRepo.save(exist.get());
                            }
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }
                list.add(leave1);
                org.get().setLeaveModel(list);
                orgRepo.save(org.get());
            }
        }

        return leave1;
    }

    @Override
    public List<LeaveModel> saveAll(List<LeaveModel> entities) {
        // TODO Auto-generated method stub
        return leaveRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id,HttpServletRequest request) {
        //leaveRepo.deleteById(id);
    	if (request.getSession().getAttribute("orgId")!= null) {
    		long orgid = (long) request.getSession().getAttribute("orgId");
    		Optional<Organization> org = orgRepo.findById(orgid);
    			if (org.isPresent()) {    			
    				leaveRepo.deleteFromOrg(orgid, id);
    				leaveRepo.deleteById(id);
				} 
		} else {
			 System.out.println("org not found");

		}
    }

    @Override
    public List<LeaveModel> getAllBetweenDates(Date startDate, Date endDate, long empId) {
        // TODO Auto-generated method stub
        return leaveRepo.findAll();
    }

    @Override
    public List<LeaveModel> findAllByEmpId(long submittedBy) {
        // TODO Auto-generated method stub
        return leaveRepo.findAllByEmpId(submittedBy);
    }

    @Override
    public LeaveModel update(LeaveModel leave, long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        if (leave.getId() != id) {
            throw new NotFoundException();
        }
        Optional<LeaveModel> lev = leaveRepo.findById(id);
        if (lev.isPresent()) {
            if (leave.getFromDate() != null && leave.getToDate() != null) {
                long difference = Math.abs(leave.getFromDate().getTime() - leave.getToDate().getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);
                String dayDifference = Long.toString(differenceDates);
                lev.get().setDayCount(Integer.parseInt(dayDifference) + 1);
            } else {
                lev.get().setDayCount(0);
            }
            lev.get().setEmployeeId(leave.getEmployeeId());
            lev.get().setLeaveType(leave.getLeaveType());
            lev.get().setFromDate(leave.getFromDate());
            lev.get().setToDate(leave.getToDate());
            lev.get().setUpdatedAt(new Date());
            lev.get().setComment(leave.getComment());
            lev.get().setStatus(leave.getStatus());
            lev.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
            return leaveRepo.save(lev.get());
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public LeaveModel getLeave(Long id) {
        // TODO Auto-generated method stub
        Optional<LeaveModel> leave = leaveRepo.findById(id);
        if (leave.isPresent())
            return leave.get();
        throw new NotFoundException();
    }

    @Override
    public List<LeaveModel> getPendingLeaveMethod() {
        // TODO Auto-generated method stub
        return leaveRepo.getPendingLeave();
    }

    @Override
    public LeaveModel approveStatus(Long id) {
        LeaveModel leave1 = null;
        Optional<LeaveModel> leaveStatusApproved = leaveRepo.findById(id);
        leaveStatusApproved.get().setStatus("Approved");
        leaveStatusApproved.get().setUpdatedAt(new Date());
        leave1 = leaveRepo.save(leaveStatusApproved.get());

        if (leave1 != null) {
            Instant timestamp1 = leaveStatusApproved.get().getFromDate().toInstant();
            Instant timestamp2 = leaveStatusApproved.get().getToDate().toInstant();
            LocalDateTime date1 = LocalDateTime.ofInstant(timestamp1, ZoneId.systemDefault());
            LocalDateTime date2 = LocalDateTime.ofInstant(timestamp2, ZoneId.systemDefault());
            long numOfDaysBetween = ChronoUnit.DAYS.between(date1, date2) + 1;

            List<LocalDateTime> dates = IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween)
                    .mapToObj(i -> date1.plusDays(i)).collect(Collectors.toList());
            for (LocalDateTime localDateTime : dates) {
                Attendance att = new Attendance();
                Instant instant = Timestamp
                        .valueOf(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).toInstant();
                Date date = Date.from(instant);
                att.setCreatedAt(date);
                att.setUpdatedAt(date);
                att.setEmployeeId(leaveStatusApproved.get().getEmployeeId());
                att.setComment(leaveStatusApproved.get().getComment());
                att.setLogDate(date);
                att.setStatus("On-Leave");
                attenRepo.save(att);

            }
        }
        return leave1;
    }

    @Override
    public List<LeaveModel> getAllPendingLeave1() {
        // TODO Auto-generated method stub
        return leaveRepo.getPendingLeave();
    }

    @Override
    public List<LeaveModel> getApprovedLeave() {
        // TODO Auto-generated method stub
        return leaveRepo.getApprovedLeave();
    }

    @Override
    public LeaveModel rejectLave(Long id) {
        LeaveModel leave = new LeaveModel();
        Optional<LeaveModel> leaveStatusApproved = leaveRepo.findById(id);
        if (leaveStatusApproved.isPresent()) {
            leaveStatusApproved.get().setStatus("Rejected");
            leaveStatusApproved.get().setUpdatedAt(new Date());
            leave = leaveRepo.save(leaveStatusApproved.get());
        }
        return leave;
    }

    @Override
    public LeaveModel save(LeaveModel entity, long orgId) {
        Optional<Organization> org = orgRepo.findById(orgId);
        Set<LeaveModel> list = new HashSet<LeaveModel>();
        LeaveModel l = new LeaveModel();
        if (org.isPresent()) {
            list = org.get().getLeaveModel();
            entity.setCreatedAt(new Date());
            entity.setUpdatedAt(new Date());
            entity.setStatus("Pending");
            l = leaveRepo.save(entity);
            list.add(l);
            org.get().setLeaveModel(list);
            orgRepo.save(org.get());
        }
        return l;
    }
}
