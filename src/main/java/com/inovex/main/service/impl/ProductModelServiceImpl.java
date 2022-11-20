package com.inovex.main.service.impl;

import java.util.ArrayList;
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
import com.inovex.main.entity.ProductModel;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.ProductModelRepo;
import com.inovex.main.service.ProductModelService;

@Service
@Transactional
public class ProductModelServiceImpl implements ProductModelService {

    @Autowired
    ProductModelRepo productRepo;
    @Autowired
    OrganizationRepository orgRepository;

    @Override
    public Optional<ProductModel> findById(Long id) {
        // TODO Auto-generated method stub
        return productRepo.findById(id);
    }

    @Override
    public List<ProductModel> findAll() {
        // TODO Auto-generated method stub
        return productRepo.findAll();
    }

    @Override
    public ProductModel getProduct(Long id) {
        // TODO Auto-generated method stub
        Optional<ProductModel> product = productRepo.findById(id);
        return product.get();
    }

    @Override
    public ProductModel save(ProductModel product1, HttpServletRequest request) {
        Set<ProductModel> list = new HashSet<>();
        ProductModel product = new ProductModel();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepository.findById(orgId);
            if (org.isPresent()) {
                list = org.get().getProductModel();
                product1.setCreatedAt(new Date());
                product1.setUpdatedAt(new Date());
                product1.setActive(true);
                product1.setCreatedBy((long) request.getSession().getAttribute("userId"));
                product = productRepo.save(product1);
                list.add(product);
                org.get().setProductModel(list);
                orgRepository.save(org.get());
            }

        }
        return product;
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            productRepo.deleteFromOrg(orgId, id);
            productRepo.deleteById(id);
        }

    }

    @Override
    public ProductModel update(ProductModel product, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        Optional<ProductModel> productUpdate = productRepo.findById(id);
        productUpdate.get().setProductName(product.getProductName());
        productUpdate.get().setProductLabel(product.getProductLabel());
        productUpdate.get().setShortDiscription(product.getShortDiscription());
        productUpdate.get().setProductImage(product.getProductImage());
        productUpdate.get().setUpdatedAt(new Date());
        productUpdate.get().setUpdatedBy((long) request.getSession().getAttribute("userId"));
        ProductModel pro1 = productRepo.save(productUpdate.get());
        return pro1;
    }

    @Override
    public ArrayList<Object> getPagination(int start, int length, String searchParam, HttpServletRequest request) {

        ArrayList<Object> obj = new ArrayList<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            if (searchParam == null || searchParam.isEmpty()) {
                obj = productRepo.getPagination(start, length, orgId);
            } else {
                obj = productRepo.getPaginationWithSerachParam(searchParam, start, length, orgId);
            }
        }
        return obj;
    }

    @Override
    public Long getCountWithSearchParm(String searchParam, long orgId) {

        if (searchParam == null || searchParam.isEmpty()) {
            return productRepo.count(orgId);
        } else {

            return productRepo.countBySearchParam(searchParam, orgId);

        }

    }

	@Override
	public ProductModel getProduct1(Long id) {
		// TODO Auto-generated method stub
	    Optional<ProductModel> product = productRepo.findById(id);
        return product.get();
	}
}
