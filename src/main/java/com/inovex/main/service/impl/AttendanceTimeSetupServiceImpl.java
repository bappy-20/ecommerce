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

import com.inovex.main.entity.AttendanceTimeSetup;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.AttendanceTimeSetupRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.AttendanceTimeSetupService;

@Service
@Transactional
public class AttendanceTimeSetupServiceImpl implements AttendanceTimeSetupService {
    @Autowired
    AttendanceTimeSetupRepo attendanceTimeSetupRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public List<AttendanceTimeSetup> findAll() {
        // TODO Auto-generated method stub
        return attendanceTimeSetupRepo.findAll();
    }

    @Override
    public Optional<AttendanceTimeSetup> findById(long id) {
        // TODO Auto-generated method stub
        return attendanceTimeSetupRepo.findById(id);
    }

	@Override
	public AttendanceTimeSetup save(AttendanceTimeSetup entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		AttendanceTimeSetup attendanceTimeSetup = new AttendanceTimeSetup();
		if (request.getSession().getAttribute("orgId")!=null) {
			long id = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(id);
			Set<AttendanceTimeSetup> list = new HashSet<AttendanceTimeSetup>();		
			if (org.isPresent()) {
				list = org.get().getAttendanceTimeSetups();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				attendanceTimeSetup = attendanceTimeSetupRepo.save(entity);
				list.add(attendanceTimeSetup);
				org.get().setAttendanceTimeSetups(list);
				orgRepo.save(org.get());
			}
		
		} else {
			System.out.println("Org is Null");
		}
		return null;
	}

}
