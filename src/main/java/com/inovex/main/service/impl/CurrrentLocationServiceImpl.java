package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.CurrentLocation;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.CurrentLocationRepository;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CurrentLocationService;

@Service
@Transactional
public class CurrrentLocationServiceImpl implements CurrentLocationService {
	
	@Autowired
	CurrentLocationRepository currentLocationRepo;
	@Autowired
	OrganizationRepository orgRepo;

	@Override
	public CurrentLocation save(CurrentLocation entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		CurrentLocation currentLocation = new CurrentLocation();
		if (request.getSession().getAttribute("orgId")!=null) {
			long id = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(id);
			Set<CurrentLocation> list = new HashSet<CurrentLocation>();		
			if (org.isPresent()) {
				list = org.get().getCurrentLocations();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				currentLocation = currentLocationRepo.save(entity);
				list.add(currentLocation);
				org.get().setCurrentLocations(list);
				orgRepo.save(org.get());
			}
		
		} else {
			System.out.println("Org is Null");
		}
		return null;
	}

}
