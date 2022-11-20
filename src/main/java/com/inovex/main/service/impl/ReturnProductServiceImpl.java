package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ReturnProduct;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.ReturnProductRepo;
import com.inovex.main.service.ReturnProductService;

@Service
@Transactional
public class ReturnProductServiceImpl implements ReturnProductService{
	
	@Autowired
	ReturnProductRepo returnProductRepo;
	@Autowired
	OrganizationRepository orgRepo;
	 
	@Override
	public ReturnProduct save(ReturnProduct entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		ReturnProduct returnProduct = new ReturnProduct();
		if (request.getSession().getAttribute("orgId")!=null) {
			long id = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(id);
			Set<ReturnProduct> list = new HashSet<ReturnProduct>();		
			if (org.isPresent()) {
				list = org.get().getReturnProducts();//();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				returnProduct = returnProductRepo.save(entity);
				list.add(returnProduct);
				org.get().setReturnProducts(list);//(list);
				orgRepo.save(org.get());
			}
		
		} else {
			System.out.println("Org is Null");
		}
		return null;
	}

}
