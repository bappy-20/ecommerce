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

import com.inovex.main.entity.MeasurementUnit;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.MeasurementUnitRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MeasurementUnitService;

@Service
@Transactional
public class MeasurementUnitServiceImpl implements MeasurementUnitService {
    @Autowired
    MeasurementUnitRepo measurementUnitRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<MeasurementUnit> findById(Long id) {
        // TODO Auto-generated method stub
        return measurementUnitRepo.findById(id);
    }

    @Override
    public List<MeasurementUnit> findAll() {
        // TODO Auto-generated method stub
        return measurementUnitRepo.findAll();
    }

    @Override
    public MeasurementUnit getMeasurementUnit(Long id) {
        // TODO Auto-generated method stub
        Optional<MeasurementUnit> measurementUnit = measurementUnitRepo.findById(id);
        if (measurementUnit.isPresent())
            return measurementUnit.get();
        throw new NotFoundException();
    }
    
	@Override
	public void deleteById(Long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("orgId")!= null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				measurementUnitRepo.deleteFromOrg(orgid, id);
				measurementUnitRepo.deleteById(id);
			} else {
				System.out.println("org not found");

			}
		}
		
	}
	
    @Override
    public MeasurementUnit update(MeasurementUnit category, long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        if (category.getId() != id) {
            throw new NotFoundException();
        }
        Optional<MeasurementUnit> msu = measurementUnitRepo.findById(id);
        if (msu.isPresent()) {
            msu.get().setName(category.getName());
//        	 msu.get().setBrandOrigin(category.getBrandOrigin());
//        	 msu.get().setLogo(category.getLogo());
            msu.get().setUpdatedAt(new Date());
            msu.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
            return measurementUnitRepo.save(msu.get());
        } else {
            throw new NullPointerException();
        }

    }

	@Override
	public MeasurementUnit save(MeasurementUnit entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		MeasurementUnit measurementUnit = new MeasurementUnit();
		if (request.getSession().getAttribute("orgId")!=null) {
			long id = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(id);
			Set<MeasurementUnit> list = new HashSet<MeasurementUnit>();		
			if (org.isPresent()) {
				list = org.get().getMeasurementUnits();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				measurementUnit = measurementUnitRepo.save(entity);
				list.add(measurementUnit);
				org.get().setMeasurementUnits(list);
				orgRepo.save(org.get());
			}
		
		} else {
			System.out.println("Org is Null");
		}
		return null;
	}

}
