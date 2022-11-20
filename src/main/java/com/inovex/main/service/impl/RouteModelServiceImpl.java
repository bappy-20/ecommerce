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

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.RouteModel;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.RouteModelRepo;
import com.inovex.main.service.RouteModelService;

@Service
@Transactional
public class RouteModelServiceImpl implements RouteModelService {

	@Autowired
	RouteModelRepo routeRepo;
	@Autowired
	OrganizationRepository orgRepo;

	@Override
	public Optional<RouteModel> findById(Long id) {
		// TODO Auto-generated method stub
		return routeRepo.findById(id);
	}

	@Override
	public List<RouteModel> findAll() {
		// TODO Auto-generated method stub
		return routeRepo.findAll();
	}

	@Override
	public RouteModel getArea(Long id) {
		// TODO Auto-generated method stub
		return routeRepo.findById(id).get();
	}

	@Override
	public RouteModel save(RouteModel entity, HttpServletRequest request) {

		RouteModel route = new RouteModel();

		if (request.getSession().getAttribute("orgId") != null) {
			long id = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(id);
			Set<RouteModel> list = new HashSet<RouteModel>();
			if (org.isPresent()) {
				list = org.get().getRoutes();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				route = routeRepo.save(entity);
				list.add(route);
				org.get().setRoutes(list);
				orgRepo.save(org.get());
			}

		} else {
			System.out.println("org is null");
		}
		return route;
	}

	@Override
	public void deleteById(Long routeId, HttpServletRequest request) {
		if (request.getSession().getAttribute("orgId") != null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				routeRepo.deleteFromOrg(orgid, routeId);
				routeRepo.deleteMkt(routeId);
				routeRepo.deleteById(routeId);
			} else {
				System.out.println("org not found");
			}
		}
	}

	@Override
	public RouteModel update(RouteModel area, Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
