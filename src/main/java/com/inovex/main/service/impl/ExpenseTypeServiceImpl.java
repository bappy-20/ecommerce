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

import com.inovex.main.entity.ExpenseType;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.ExpenseTypeRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.ExpenseTypeService;

@Transactional
@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

	@Autowired
	ExpenseTypeRepo expenseTypeRepo;

	@Autowired
	OrganizationRepository orgRepo;

	@Override
	public List<ExpenseType> findAll() {
		// TODO Auto-generated method stub
		return expenseTypeRepo.findAll();
	}

	@Override
	public Set<ExpenseType> findByOrg(Long orgId) {
		Optional<Organization> org = orgRepo.findById(orgId);
		Set<ExpenseType> expList = new HashSet<ExpenseType>();
		if (org.isPresent()) {
			expList = org.get().getExpenseTypes();// getExpType();

		}
		return expList;

	}

	@Override
	public Optional<ExpenseType> findById(Long id) {
		// TODO Auto-generated method stub
		return expenseTypeRepo.findById(id);
	}

	@Override
	public ExpenseType getExpenseType(Long id) {
		// TODO Auto-generated method stub
		Optional<ExpenseType> expenseType = expenseTypeRepo.findById(id);
		if (expenseType.isPresent())
			return expenseType.get();
		throw new NotFoundException();
	}

	@Override
	public void deleteById(Long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("orgId") != null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				expenseTypeRepo.deleteFromOrg(orgid, id);
				expenseTypeRepo.deleteById(id);
			} else {

			}
		}

	}

	@Override
	public ExpenseType update(ExpenseType category, long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (category.getId() != id) {
			throw new NotFoundException();
		}
		Optional<ExpenseType> ext = expenseTypeRepo.findById(id);
		if (ext.isPresent()) {
			ext.get().setExpenseType(category.getExpenseType());

			// ext.get().setExpenseUser(category.getExpenseUser());

			ext.get().setUpdatedAt(new Date());
			ext.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
			return expenseTypeRepo.save(ext.get());
		} else {
			throw new NullPointerException();
		}

	}

//	@Override
//	public List<ExpenseType> findAllByExpenseUser(String expenseUser) {
//		// TODO Auto-generated method stub
//		return expenseTypeRepo.findAllByExpenseUser(expenseUser);
//	}

	@Override
	public ExpenseType save(ExpenseType entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		ExpenseType exptype = new ExpenseType();
		if (request.getSession().getAttribute("orgId") != null) {

			long id = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(id);
			Set<ExpenseType> list = new HashSet<ExpenseType>();
			if (org.isPresent()) {
				list = org.get().getExpenseTypes();
				entity.setActive(true);
				entity.setCreatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				entity.setUpdatedAt(new Date());
				exptype = expenseTypeRepo.save(entity);
				list.add(exptype);
				org.get().setExpenseTypes(list);
				orgRepo.save(org.get());
			}
		} else {
			System.out.println("Org is null");

		}
		return null;
	}
}
