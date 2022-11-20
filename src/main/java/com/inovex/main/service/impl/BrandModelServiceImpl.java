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

import com.inovex.main.entity.BrandModel;
import com.inovex.main.entity.CompanyModel;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.BrandModelRepo;
import com.inovex.main.repository.CompanyModelRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.BrandModelService;

@Service
@Transactional
public class BrandModelServiceImpl implements BrandModelService {
    @Autowired
    BrandModelRepo brandModelRepo;
    @Autowired
    CompanyModelRepo companyModelRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<BrandModel> findById(Long id) {
        // TODO Auto-generated method stub
        return brandModelRepo.findById(id);
    }

    @Override
    public List<BrandModel> findAll() {
        // TODO Auto-generated method stub
        return brandModelRepo.findAll();
    }

    @Override
    public BrandModel update(BrandModel brand, Long id, HttpServletRequest request) {

        Optional<BrandModel> brd = brandModelRepo.findById(id);
        if (brd.isPresent()) {
            Optional<CompanyModel> company = companyModelRepo.findById(brand.getCompanyId());
            if (company.isPresent()) {
                brd.get().setBrandOrigin(company.get().getCompanyOrigin());
            }
            brd.get().setBrandName(brand.getBrandName());
            brd.get().setLogo(brand.getLogo());
            brd.get().setUpdatedAt(new Date());
            brd.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
            return brandModelRepo.save(brd.get());
        } else {
            throw new NullPointerException();
        }

    }

    @Override
    public BrandModel save(BrandModel entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        BrandModel brandModel = new BrandModel();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<BrandModel> list = new HashSet<BrandModel>();
            if (org.isPresent()) {
                Optional<CompanyModel> company = companyModelRepo.findById(entity.getCompanyId());
                if (company.isPresent()) {
                    entity.setBrandOrigin(company.get().getCompanyOrigin());
                    entity.setCreatedAt(new Date());
                    entity.setActive(true);
                    entity.setUpdatedAt(new Date());
                    entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                    brandModel = brandModelRepo.save(entity);
                    list = org.get().getBrandModels();
                    list.add(brandModel);
                    org.get().setBrandModels(list);
                    orgRepo.save(org.get());
                }

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
            	brandModelRepo.deleteFromCompanyBrand(id);
                brandModelRepo.deleteFromOrg(orgid, id);
                brandModelRepo.deleteById(id);
                
            }
        } else {
            System.out.println("Org not found");
        }

    }

    @Override
    public Set<BrandModel> findAllByOrgId(Long orgId) {
        Set<BrandModel> list = new HashSet<BrandModel>();
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            list = org.get().getBrandModels();
        }
        return list;
    }

    @Override
    public List<BrandModel> findAllByCompanyId(Long companyId) {
        // TODO Auto-generated method stub
        return brandModelRepo.findAllByCompanyId(companyId);
    }
}