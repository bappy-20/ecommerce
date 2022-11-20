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

import com.inovex.main.entity.CompanyModel;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.CompanyModelRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CompanyModelService;

@Service
@Transactional
public class CompanyModelServiceImpl implements CompanyModelService {
    @Autowired
    CompanyModelRepo companyModelRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<CompanyModel> findById(Long id) {
        // TODO Auto-generated method stub
        return companyModelRepo.findById(id);
    }

    @Override
    public List<CompanyModel> findAll() {
        // TODO Auto-generated method stub
        return companyModelRepo.findAll();
    }

    @Override
    public CompanyModel update(CompanyModel company, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub

        Optional<CompanyModel> brd = companyModelRepo.findById(id);
        if (brd.isPresent()) {
            brd.get().setCompanyName(company.getCompanyName());
            brd.get().setCompanyOrigin(company.getCompanyOrigin());
            brd.get().setLogo(company.getLogo());
            brd.get().setUpdatedAt(new Date());
            brd.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
            return companyModelRepo.save(brd.get());
        } else {
            throw new NullPointerException();
        }

    }

    @Override
    public CompanyModel save(CompanyModel entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        CompanyModel brandModel = new CompanyModel();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<CompanyModel> list = new HashSet<CompanyModel>();
            if (org.isPresent()) {
                list = org.get().getCompanyModel();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                brandModel = companyModelRepo.save(entity);
                list.add(brandModel);
                org.get().setCompanyModel(list);
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
        try {
            if (request.getSession().getAttribute("orgId") != null) {
                long orgid = (long) request.getSession().getAttribute("orgId");

                Optional<Organization> org = orgRepo.findById(orgid);
                if (org.isPresent()) {
                    companyModelRepo.deleteFromOrg(orgid, id);
                    companyModelRepo.deleteById(id);
                }
            } else {
                System.out.println("Org not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}