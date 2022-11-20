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
import com.inovex.main.entity.ProductBatch;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.ProductBatchRepo;
import com.inovex.main.service.ProductBatchService;

@Service
@Transactional
public class ProductBatchServiceImpl implements ProductBatchService {
    @Autowired
    ProductBatchRepo productBatchRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<ProductBatch> findById(Long id) {
        // TODO Auto-generated method stub
        return productBatchRepo.findById(id);
    }

    @Override
    public List<ProductBatch> findAll() {
        // TODO Auto-generated method stub
        return productBatchRepo.findAll();
    }

    @Override
    public ProductBatch update(ProductBatch entity, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub

        Optional<ProductBatch> brd = productBatchRepo.findById(id);
        if (brd.isPresent()) {
            brd.get().setBatchNumber(entity.getBatchNumber());
            brd.get().setExpireDate(entity.getExpireDate());
            brd.get().setManufatureDate(entity.getManufatureDate());
            brd.get().setUpdatedAt(new Date());
            brd.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
            return productBatchRepo.save(brd.get());
        } else {
            throw new NullPointerException();
        }

    }

    @Override
    public ProductBatch save(ProductBatch entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        ProductBatch brandModel = new ProductBatch();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<ProductBatch> list = new HashSet<ProductBatch>();
            if (org.isPresent()) {
                list = org.get().getProductBatch();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                brandModel = productBatchRepo.save(entity);
                list.add(brandModel);
                org.get().setProductBatch(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
                productBatchRepo.deleteFromOrg(orgid, id);
                productBatchRepo.deleteById(id);
            }
        } else {
            System.out.println("Org not found");
        }

    }

	@Override
	public List<ProductBatch> findAllPrdBatchByProductdId(Long productId) {
		// TODO Auto-generated method stub
		 return productBatchRepo.findAllBatchByProductId(productId);
	}
}