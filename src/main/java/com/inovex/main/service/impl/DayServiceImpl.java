package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.DayModel;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.DayModelRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.DayService;

@Service
@Transactional
public class DayServiceImpl implements DayService {
    @Autowired
    DayModelRepo dayModelRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<DayModel> findById(Long id) {
        // TODO Auto-generated method stub
        return dayModelRepo.findById(id);
    }

    @Override
    public List<DayModel> findAll() {
        // TODO Auto-generated method stub
        return dayModelRepo.findAll();
    }

    @Override
    public DayModel getDayModel(Long id) {
        Optional<DayModel> dayModel = dayModelRepo.findById(id);
        if (dayModel.isPresent())
            return dayModel.get();
        throw new NotFoundException();

    }

    @Override
    public List<DayModel> getDayListByEmpId(String empId) {
        // TODO Auto-generated method stub
        return dayModelRepo.getDayListByEmpId(empId);
    }
    
    @Override
    public List<DayModel> saveAll(List<DayModel> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
	public void deleteById(Long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
    	if (request.getSession().getAttribute("orgId")!=null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				dayModelRepo.deleteFromOrg(orgid, id);
				dayModelRepo.deleteById(id);
			} else {
				System.out.println("Org not found");
			}
		}
		
	}

    @Override
    public DayModel update(DayModel dayModel, Long id, HttpServletRequest request) {

        Optional<DayModel> dayModelUpdate = dayModelRepo.findById(id);

        dayModelUpdate.get().setEmpId(dayModel.getEmpId());
        dayModelUpdate.get().setDayName(dayModel.getDayName());
        dayModelUpdate.get().setRouteId(dayModel.getRouteId());
        dayModelUpdate.get().setUpdatedAt(new Date());
        dayModelUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        dayModelRepo.save(dayModelUpdate.get());
        return dayModelUpdate.get();
    }

	@Override
	public DayModel save(DayModel entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		DayModel dayModel = new DayModel();
		if (request.getSession().getAttribute("orgId")!=null) {
			long id = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(id);
			Set<DayModel> list = new HashSet<DayModel>();		
			if (org.isPresent()) {
				list = org.get().getDayModels();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				dayModel = dayModelRepo.save(entity);
				list.add(dayModel);
				org.get().setDayModels(list);
				orgRepo.save(org.get());
			}
		
		} else {
			System.out.println("Org is Null");
		}
		return null;
	}
}
