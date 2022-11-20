package com.inovex.main.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.DistributorType;
import com.inovex.main.repository.DistributorTypeRepository;
import com.inovex.main.service.DistributorTypeService;

@Transactional
@Service
public class DistributorTypeServiceImpl  implements DistributorTypeService {
	
	@Autowired
	DistributorTypeRepository distributorTypeRepo;

	@Override
	public Optional<DistributorType> findById(long id) {
		// TODO Auto-generated method stub
		return distributorTypeRepo.findById(id);
	}

	@Override
	public List<DistributorType> findAll() {
		// TODO Auto-generated method stub
		   return distributorTypeRepo.findAll();
	}

	@Override
	public DistributorType getDistributorType(long id) {
		// TODO Auto-generated method stub
		 Optional<DistributorType> distributorType = distributorTypeRepo.findById(id);
	        if (distributorType.isPresent())
	            return distributorType.get();
	        throw new NotFoundException();
	}

//	@Override
//	public void deleteById(long id, HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		if (request.getSession().getAttribute("orgId")!= null) {
//			long orgid = (long) request.getSession().getAttribute("orgId");
//			Optional<Organization> org = orgRepo.findById(orgid);
//			if (org.isPresent()) {
//				expenseTypeRepo.deleteFromOrg(orgid, id);
//				expenseTypeRepo.findById(id);
//			} else {
//
//			}
//		}
//		
//	}
//
//	@Override
//	public DistributorType save(DistributorType entity, HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public DistributorType update(DistributorType distributorType, Long id, HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
