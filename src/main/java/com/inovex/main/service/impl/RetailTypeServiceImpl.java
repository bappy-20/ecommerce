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

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.RetailType;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.RetailTypeRepo;
import com.inovex.main.service.RetailTypeService;

@Service
@Transactional
public class RetailTypeServiceImpl implements RetailTypeService {
    @Autowired
    RetailTypeRepo retailTypeRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<RetailType> findById(Long id) {
        // TODO Auto-generated method stub
        return retailTypeRepo.findById(id);
    }

    @Override
    public List<RetailType> findAll() {
        // TODO Auto-generated method stub
        return retailTypeRepo.findAll();
    }

    @Override
    public List<RetailType> saveAll(List<RetailType> entities) {
        // TODO Auto-generated method stub
        return retailTypeRepo.saveAll(entities);
    }

    @Override
    public void deleteById(long id, HttpServletRequest request) {
       // retailTypeRepo.deleteById(id);
    	if (request.getSession().getAttribute("orgId")!= null) {
    		long orgid = (long) request.getSession().getAttribute("orgId");
    		Optional<Organization> org = orgRepo.findById(orgid);
    		if (org.isPresent()) {
    			retailTypeRepo.deleteFromOrg(orgid, id);
    			retailTypeRepo.deleteById(id);
				
			} else {
				System.out.println("org not found");

			}
			
		}
    }

    @Override
    public RetailType update(RetailType retailType, long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        Optional<RetailType> retailTypeUpdate = retailTypeRepo.findById(id);

        retailTypeUpdate.get().setRetailType(retailType.getRetailType());
        retailTypeUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        retailTypeRepo.save(retailTypeUpdate.get());
        return retailTypeUpdate.get();
    }

    @Override
    public RetailType getRetailType(Long id) {
        Optional<RetailType> retailType = retailTypeRepo.findById(id);
        if (retailType.isPresent())
            return retailType.get();
        throw new NotFoundException();
    }

	@Override
	public RetailType save(RetailType entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		RetailType retailType = new RetailType();
		
		if (request.getSession().getAttribute("orgId")!= null) {
			long id = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(id);
			Set<RetailType> list = new HashSet<RetailType>();
			if (org.isPresent()) {
			list = org.get().getRetailType();
			entity.setActive(true);
			entity.setCreatedAt(new Date());
			entity.setUpdatedAt(new Date());
			entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
			retailType = retailTypeRepo.save(entity);
			list.add(retailType);
			org.get().setRetailType(list);
			orgRepo.save(org.get());
			}			
		} else {
			System.out.println("Org is null");
		}	
		return null;
	}

}
