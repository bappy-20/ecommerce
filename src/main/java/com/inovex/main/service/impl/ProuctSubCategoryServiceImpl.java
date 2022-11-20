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
import com.inovex.main.entity.ProductSubCategory;
import com.inovex.main.repository.CategoryRepo;
import com.inovex.main.repository.DistributorRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.ProductSubCategoryRepo;
import com.inovex.main.service.ProductSubCategoryService;

@Service
@Transactional
public class ProuctSubCategoryServiceImpl implements ProductSubCategoryService {

    @Autowired
    DistributorRepo distService;
    @Autowired
    ProductSubCategoryRepo categoryRepo;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    CategoryRepo catService;

    @Override
    public Optional<ProductSubCategory> findById(Long id) {
        // TODO Auto-generated method stub
        return categoryRepo.findById(id);
    }

    @Override
    public List<ProductSubCategory> findAll() {
        // TODO Auto-generated method stub
        return categoryRepo.findAll();
    }

    @Override
    public ProductSubCategory getCategory(Long id) {
        Optional<ProductSubCategory> category = categoryRepo.findById(id);
        if (category.isPresent())
            return category.get();
        throw new NotFoundException();
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {

        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            categoryRepo.deleteFromCategory(id);
            categoryRepo.deleteFromOrg(orgId, id);
            categoryRepo.deleteById(id);

        }

    }

    @Override
    public ProductSubCategory update(ProductSubCategory category, long id, HttpServletRequest request) {
        Optional<ProductSubCategory> categoryUpdate = categoryRepo.findById(id);

        categoryUpdate.get().setName(category.getName());
        categoryUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        categoryRepo.save(categoryUpdate.get());
        return categoryUpdate.get();

    }

    @Override
    public Optional<ProductSubCategory> findByName(String name) {
        // TODO Auto-generated method stub
        return categoryRepo.findByName(name);
    }

    @Override
    public ProductSubCategory save(ProductSubCategory category, HttpServletRequest request) {
        // TODO Auto-generated method stub
        ProductSubCategory prd = new ProductSubCategory();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<ProductSubCategory> list = new HashSet<ProductSubCategory>();
            if (org.isPresent()) {
                list = org.get().getProductSubCategory();
                category.setCreatedAt(new Date());
                category.setActive(true);
                category.setUpdatedAt(new Date());
                category.setCreatedBy((long) request.getSession().getAttribute("userId"));
                prd = categoryRepo.save(category);

                if (request.getSession().getAttribute("cateId") != null) {
                    Set<ProductSubCategory> listIncat = new HashSet<ProductSubCategory>();
                    long catId = (long) request.getSession().getAttribute("cateId");
                    Optional<Category> cate = catService.findById(catId);
                    if (org.isPresent()) {
                        listIncat = cate.get().getProductSubCategory();
                        listIncat.add(prd);
                        cate.get().setProductSubCategory(listIncat);
                        catService.save(cate.get());
                    }
                }

                list.add(prd);
                org.get().setProductSubCategory(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is null");
        }
        return prd;
    }

}
