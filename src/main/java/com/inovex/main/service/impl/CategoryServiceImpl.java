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

import com.inovex.main.entity.Category;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.CategoryRepo;
import com.inovex.main.repository.DistributorRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    DistributorRepo distService;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<Category> findById(Long id) {
        // TODO Auto-generated method stub
        return categoryRepo.findById(id);
    }

    @Override
    public List<Category> findAll() {
        // TODO Auto-generated method stub
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent())
            return category.get();
        throw new NotFoundException();
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {
    	if (request.getSession().getAttribute("orgId")!=null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				categoryRepo.deleteFromOrg(orgid, id);
				categoryRepo.deleteById(id);
			} else {
				System.out.println("Org not found");

			}
		}
    }

    @Override
    public Category update(Category category, long id, HttpServletRequest request) {
        Optional<Category> categoryUpdate = categoryRepo.findById(id);

        categoryUpdate.get().setName(category.getName());
        categoryUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        categoryRepo.save(categoryUpdate.get());
        return categoryUpdate.get();

    }

    @Override
    public Optional<Category> findByName(String name) {
        // TODO Auto-generated method stub
        return categoryRepo.findByName(name);
    }

	@Override
	public Category save(Category category, HttpServletRequest request) {
		Category prd = new Category();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<Category> list = new HashSet<Category>();
            
            if (org.isPresent()) {
                list = org.get().getCategories();
                category.setCreatedAt(new Date());
                category.setActive(true);
                category.setUpdatedAt(new Date());
                category.setCreatedBy((long) request.getSession().getAttribute("userId"));
                prd = categoryRepo.save(category);
                list.add(prd);
                org.get().setCategories(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is null");
        }
        return prd;	
	}
}