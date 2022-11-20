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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.MonthlyOrderTarget;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.MonthlyOrderTargetRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MonthlyOrderTargetService;

@Service
@Transactional
public class MonthlyOrderTargetServiceImpl implements MonthlyOrderTargetService {

	@Autowired
	MonthlyOrderTargetRepo monthlyOrderTargetRepo;
	@Autowired
	OrganizationRepository orgRepo;

	@Override
	public Optional<MonthlyOrderTarget> findById(Long id) {
		// TODO Auto-generated method stub
		return monthlyOrderTargetRepo.findById(id);
	}

	@Override
	public List<MonthlyOrderTarget> findAll() {
		// TODO Auto-generated method stub
		return monthlyOrderTargetRepo.findAll();
	}

	@Override
	public MonthlyOrderTarget getMonthlyTarget(Long id) {
		// TODO Auto-generated method stub
		return monthlyOrderTargetRepo.getOne(id);
	}

	@Override
	public List<MonthlyOrderTarget> saveAll(List<MonthlyOrderTarget> target, HttpServletRequest request) {
		List<MonthlyOrderTarget> allTarget = new ArrayList<MonthlyOrderTarget>();
		List<MonthlyOrderTarget> allTarget1 = new ArrayList<MonthlyOrderTarget>();
		if (request.getSession().getAttribute("orgId") != null) {
			long id = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(id);
			Set<MonthlyOrderTarget> list = new HashSet<MonthlyOrderTarget>();
			if (org.isPresent()) {
				list = org.get().getMonthlyOrderTargets();
				for (MonthlyOrderTarget tr : target) {
					Optional<MonthlyOrderTarget> check = findAllOfCurrentMonth(tr.getEmpId(), tr.getTargetMonth());
					if (check.isPresent()) {
						check.get().setOrderQuantity(tr.getOrderQuantity());
						check.get().setOrderValue(tr.getOrderValue());
						check.get().setUpdatedAt(new Date());
						allTarget.add(check.get());
					} else {
						MonthlyOrderTarget mt = new MonthlyOrderTarget();
						mt.setCreatedAt(new Date());
						mt.setUpdatedAt(new Date());
						mt.setCreatedBy(1);
						mt.setActive(true);
						mt.setEmpId(tr.getEmpId());
						mt.setEmpName(tr.getEmpName());
						mt.setOrderQuantity(tr.getOrderQuantity());
						mt.setOrderValue(tr.getOrderValue());
						mt.setTargetMonth(tr.getTargetMonth());
						mt.setOrgId(id);
						allTarget.add(mt);
						list.add(mt);
					}

				}
				allTarget1 = monthlyOrderTargetRepo.saveAll(allTarget);
				org.get().setMonthlyOrderTargets(list);
				orgRepo.save(org.get());
			}

		}

		return allTarget1;
	}

	@Override
	public void deleteById(Long id, HttpServletRequest request) {
		if (request.getSession().getAttribute("orgId") != null) {
			long orgid = (long) request.getSession().getAttribute("orgId");

			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				monthlyOrderTargetRepo.deleteFromOrg(orgid, id);
				monthlyOrderTargetRepo.deleteById(id);
			}
		} else {
			System.out.println("org not found");
		}
	}

	@Override
	public MonthlyOrderTarget update(MonthlyOrderTarget monthlyTarget, Long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MonthlyOrderTarget> findAllOfCurrentMonth(String employeeId, Date targetMonth1) {
		// TODO Auto-generated method stub
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(targetMonth1);
		int month1 = calendar1.get(Calendar.MONTH) + 1; // {0 - 11}
		int year1 = calendar1.get(Calendar.YEAR);
		System.out.println("year1 " + year1 + " month1 " + month1);
		return monthlyOrderTargetRepo.findAllOfCurrentMonth(year1, month1, employeeId);
	}

	@Override
	public List<MonthlyOrderTarget> findAllByCurMonth(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<MonthlyOrderTarget> mtList = new ArrayList<>();
		if (request.getSession().getAttribute("orgId") != null) {
			long id = (long) request.getSession().getAttribute("orgId");
			mtList = monthlyOrderTargetRepo.findAllByCurMonth(id);
		}
		return mtList;
	}

	@Override
	public List<MonthlyOrderTarget> findAllByMonth(Date targetMonth) {

		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(targetMonth);
		int month1 = calendar1.get(Calendar.MONTH) + 1; // {0 - 11}
		int year1 = calendar1.get(Calendar.YEAR);
		return monthlyOrderTargetRepo.findAllByMonth(year1, month1);
	}

	@Override
	public Optional<MonthlyOrderTarget> findAllByCurMonthAndEmpId(String employeeId) {
		// TODO Auto-generated method stub
		return monthlyOrderTargetRepo.findAllByCurMonthAndEmpId(employeeId);
	}

	@Override
	public MonthlyOrderTarget save(MonthlyOrderTarget entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		MonthlyOrderTarget monthlyOrderTarget = new MonthlyOrderTarget();
		if (request.getSession().getAttribute("orgId") != null) {
			long id = (long) request.getSession().getAttribute("orgId");

			Optional<Organization> org = orgRepo.findById(id);
			Set<MonthlyOrderTarget> list = new HashSet<MonthlyOrderTarget>();
			if (org.isPresent()) {
				list = org.get().getMonthlyOrderTargets();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				monthlyOrderTarget = monthlyOrderTargetRepo.save(entity);
				list.add(monthlyOrderTarget);
				org.get().setMonthlyOrderTargets(list);
				orgRepo.save(org.get());
			}

		} else {
			System.out.println("Org is Null");
		}
		return null;
	}

	@Override
	public List<Object> findAllDeliveryEMployeeOfSelectedMonth7(HttpServletRequest request, String date) {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<>();
		String month, year;
		if (request.getSession().getAttribute("orgId") != null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				year = date.substring(0, 4);
				month = date.substring(5, 7);
				// System.out.println(year +" "+month);
				System.out.println(year);
				System.out.println(month);

				list = monthlyOrderTargetRepo.findAllDeliveryExecutiveOfSelectedMonth7(year, month, orgid);
			}
		}
		return list;
	}

}