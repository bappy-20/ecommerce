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

import com.inovex.main.entity.AttendanceType;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.AttendanceTypeRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.AttendanceTypeService;

@Service
@Transactional
public class AttendanceTypeServiceImpl implements AttendanceTypeService {
    @Autowired
    AttendanceTypeRepo attendanceTypeRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<AttendanceType> findById(Long id) {
        // TODO Auto-generated method stub
        return attendanceTypeRepo.findById(id);
    }

    @Override
    public List<AttendanceType> findAll() {
        // TODO Auto-generated method stub
        return attendanceTypeRepo.findAll();
    }

    @Override
    public AttendanceType update(AttendanceType entity) {
        // TODO Auto-generated method stub
        return attendanceTypeRepo.save(entity);
    }

    @Override
    public List<AttendanceType> saveAll(List<AttendanceType> entities) {
        // TODO Auto-generated method stub
        return attendanceTypeRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        attendanceTypeRepo.deleteById(id);
    }

	@Override
	public AttendanceType save(AttendanceType entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		AttendanceType attendenceType = new AttendanceType();
		if (request.getSession().getAttribute("orgId")!= null) {
			long id = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(id);
			Set<AttendanceType> list = new HashSet<AttendanceType>();		
			if (org.isPresent()) {
				list = org.get().getAttendanceTypes();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				attendenceType = attendanceTypeRepo.save(entity);
				list.add(attendenceType);
				org.get().setAttendanceTypes(list);
				orgRepo.save(org.get());
			}
		
		} else {
			System.out.println("Org is Null");
		}
		return null;
	}

}
