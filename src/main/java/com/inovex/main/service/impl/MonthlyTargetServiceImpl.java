package com.inovex.main.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.MonthlyTarget;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.MonthlyTargetRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MonthlyTargetService;

@Service
@Transactional
public class MonthlyTargetServiceImpl implements MonthlyTargetService {

	@Autowired
	MonthlyTargetRepo monthlyTargetRepo;
	@Autowired
	OrganizationRepository orgRepo;

	@Override
	public Optional<MonthlyTarget> findById(Long id) {
		// TODO Auto-generated method stub
		return monthlyTargetRepo.findById(id);
	}

	@Override
	public List<MonthlyTarget> findAll() {
		// TODO Auto-generated method stub
		return monthlyTargetRepo.findAll();
	}

	@Override
	public MonthlyTarget getMonthlyTarget(Long id) {
		Optional<MonthlyTarget> monthlyTarget = monthlyTargetRepo.findById(id);
		if (monthlyTarget.isPresent()) {
			return monthlyTarget.get();
		}
		throw new NotFoundException();
	}

	@Override
	public void deleteById(Long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("orgId") != null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				monthlyTargetRepo.deleteFromOrg(orgid, id);
				monthlyTargetRepo.deleteById(id);
			} else {
				System.out.println("org not found");
			}

		}
	}

	@Override
	public MonthlyTarget update(MonthlyTarget monthlyTarget, Long id, HttpServletRequest request) {

		Optional<MonthlyTarget> monthlyTargetUpdate = monthlyTargetRepo.findById(id);
		monthlyTargetUpdate.get().setEmpId(monthlyTarget.getEmpId());
		monthlyTargetUpdate.get().setProductName(monthlyTarget.getProductName());
		monthlyTargetUpdate.get().setQuantity(monthlyTarget.getQuantity());
		monthlyTargetUpdate.get().setTotalValue(monthlyTarget.getTotalValue());
		monthlyTargetUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));

		monthlyTargetRepo.save(monthlyTargetUpdate.get());
		return monthlyTargetUpdate.get();

	}

	@Override
	public List<MonthlyTarget> findAllOfCurrentMonth(String employeeId, long orgId) {
		// TODO Auto-generated method stub
		return monthlyTargetRepo.findAllOfCurrentMonth(employeeId, orgId);
	}

	@Override
	public List<MonthlyTarget> saveAll(Set<MonthlyTarget> monthlyTargets, HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<MonthlyTarget> monthlyTargets1 = new ArrayList<>();
		if (request.getSession().getAttribute("orgId") != null) {
			long id = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(id);
			Set<MonthlyTarget> list = new HashSet<MonthlyTarget>();
			if (org.isPresent()) {
				list = org.get().getMonthlyTargets();
				monthlyTargets1 = monthlyTargetRepo.saveAll(monthlyTargets);
				if (monthlyTargets1.size() > 0) {
					list.addAll(monthlyTargets);
				}
				org.get().setMonthlyTargets(list);
				orgRepo.save(org.get());
			}
		}
		return monthlyTargets1;
	}

	@Override
	public List<Object> findAllEmployeeOfCurrentMonth(HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("orgId") != null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				return monthlyTargetRepo.findAllEmployeeOfCurrentMonth(orgid);
			}
		}
		return null;
	}

	@Override
	public Optional<MonthlyTarget> findExistorNotOfCurrentMonth(String productName, String employeeId,
			Date targetMonth) {
		// TODO Auto-generated method stub
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(targetMonth);
		int month1 = calendar1.get(Calendar.MONTH) + 1; // {0 - 11}
		int year1 = calendar1.get(Calendar.YEAR);
		return monthlyTargetRepo.findExistorNotOfCurrentMonth(year1, month1, productName, employeeId);
	}

	@Override
	public MonthlyTarget save(MonthlyTarget entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		MonthlyTarget monthlyTarget = new MonthlyTarget();
		if (request.getSession().getAttribute("orgId") != null) {
			long id = (long) request.getSession().getAttribute("orgId");

			Optional<Organization> org = orgRepo.findById(id);
			Set<MonthlyTarget> list = new HashSet<MonthlyTarget>();
			if (org.isPresent()) {
				list = org.get().getMonthlyTargets();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				monthlyTarget = monthlyTargetRepo.save(entity);
				list.add(monthlyTarget);
				org.get().setMonthlyTargets(list);
				orgRepo.save(org.get());
			}

		} else {
			System.out.println("Org is Null");
		}
		return null;
	}
	@Override
	public List<Object> findAllEMployeeOfSelectedMonth7(HttpServletRequest request,String date) {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<>();
		String month, year;		
		if (request.getSession().getAttribute("orgId") != null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				year = date.substring(0, 4);
				month = date.substring(5, 7);
				//System.out.println(year +" "+month);
				System.out.println(year);
				System.out.println(month);
				
				list= monthlyTargetRepo.findAllEmployeeOfSelectedMonth7(year,month, orgid);
			}
		}
		return list;
	}
}
