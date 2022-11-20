package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.AttendanceTime;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.AttendanceTimeRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.AttendanceTimeService;

@Service
@Transactional
public class AttendanceTimeServiceImpl implements AttendanceTimeService {

    @Autowired
    AttendanceTimeRepo attendanceTimeRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<AttendanceTime> findById(Long id) {
        // TODO Auto-generated method stub
        return attendanceTimeRepo.findById(id);
    }

    @Override
    public List<AttendanceTime> findAll() {
        // TODO Auto-generated method stub
        return attendanceTimeRepo.findAll();
    }
    
    @Override
    public List<AttendanceTime> saveAll(List<AttendanceTime> entities) {
        // TODO Auto-generated method stub
        return attendanceTimeRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        attendanceTimeRepo.deleteById(id);

    }

	@Override
	public AttendanceTime save(AttendanceTime entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		AttendanceTime attendanceTime = new AttendanceTime();
		if (request.getSession().getAttribute("orgId")!=null) {
			long id = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(id);
			Set<AttendanceTime> list = new HashSet<AttendanceTime>();		
			if (org.isPresent()) {
				list = org.get().getAttendanceTimes();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				attendanceTime = attendanceTimeRepo.save(entity);
				list.add(attendanceTime);
				org.get().setAttendanceTimes(list);
				orgRepo.save(org.get());
			}
		
		} else {
			System.out.println("Org is Null");
		}
		return null;
	}
}
