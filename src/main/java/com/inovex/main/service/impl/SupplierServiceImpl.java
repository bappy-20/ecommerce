package com.inovex.main.service.impl;

import java.util.ArrayList;
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
import com.inovex.main.entity.SubMenu;
import com.inovex.main.entity.Supplier;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.SupplierRepo;
import com.inovex.main.service.SupplierService;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepo supplierRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<Supplier> findById(Long id) {
        // TODO Auto-generated method stub
        return supplierRepo.findById(id);
    }

    @Override
    public List<Supplier> findAll() {
        // TODO Auto-generated method stub
        return supplierRepo.findAll();
    }

    @Override
    public Supplier getSupplier(Long id) {
        Optional<Supplier> supplier = supplierRepo.findById(id);
        if (supplier.isPresent())
            return supplier.get();
        throw new NotFoundException();
    }
    
    @Override
	public void deleteById(long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
    	if (request.getSession().getAttribute("orgId")!= null) {
    		long orgid = (long) request.getSession().getAttribute("orgId");
    		Optional<Organization> org = orgRepo.findById(orgid);
    		if (org.isPresent()) {
    			supplierRepo.deleteFromOrg(orgid, id);
    			supplierRepo.deleteById(id);
				
			} else {
				System.out.println("org not found");			}
		}
		
	}
    
    @Override
    public Supplier update(Supplier supplier, long id, HttpServletRequest request) {
        Optional<Supplier> supplierUpdate = supplierRepo.findById(id);

        supplierUpdate.get().setName(supplier.getName());
        supplierUpdate.get().setAddress(supplier.getAddress());
        supplierUpdate.get().setNote(supplier.getNote());
        supplierUpdate.get().setPhone(supplier.getPhone());
        supplierUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        supplierRepo.save(supplierUpdate.get());
        return supplierUpdate.get();
    }

	@Override
	public Supplier save(Supplier entity, HttpServletRequest request) {
		// TODO Auto-generated method stub	
		Supplier supplier = new Supplier();
		if (request.getSession().getAttribute("orgId")!=null) {
			long id = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(id);
			Set<Supplier> list = new HashSet<Supplier>();		
			if (org.isPresent()) {
				list = org.get().getSuppliers();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				supplier = supplierRepo.save(entity);
				list.add(supplier);
				org.get().setSuppliers(list);
				orgRepo.save(org.get());
			}
		
		} else {
			System.out.println("Org is Null");
		}
		return null;
	}
}