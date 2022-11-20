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

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.RetailComplain;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.RetailComplainRepo;
import com.inovex.main.service.RetailComplainService;

@Service
@Transactional
public class RetailComplainServiceImpl implements RetailComplainService {
    @Autowired
    RetailComplainRepo retailComplainRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<RetailComplain> findById(Long id) {
        // TODO Auto-generated method stub
        return retailComplainRepo.findById(id);
    }

    @Override
    public List<RetailComplain> findAll() {
        // TODO Auto-generated method stub
        return retailComplainRepo.findAll();
    }

    @Override
    public void deleteById(long id, HttpServletRequest request) {
        // retailComplainRepo.deleteById(id);
        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
                retailComplainRepo.deleteFromOrg(orgid, id);
                retailComplainRepo.deleteById(id);

            } else {
                System.out.println("org not found");
            }
        }
    }

    @Override
    public RetailComplain update(RetailComplain area, Long id) {

        Optional<RetailComplain> rtlc = retailComplainRepo.findById(id);
        if (rtlc.isPresent()) {
            rtlc.get().setTitle(area.getTitle());
            rtlc.get().setRetailId(area.getRetailId());
            rtlc.get().setNote(area.getNote());
            rtlc.get().setUpdatedAt(new Date());
            return retailComplainRepo.save(rtlc.get());
        } else {
            return null;
        }

    }

    @Override
    public List<RetailComplain> findAllByEmployeeId(String employeeId) {
        // TODO Auto-generated method stub
        return retailComplainRepo.findAllByEmployeeId(employeeId);
    }

    @Override
    public RetailComplain getRetailComplain(Long id) {
        // TODO Auto-generated method stub
        Optional<RetailComplain> retailComplain = retailComplainRepo.findById(id);
        if (retailComplain.isPresent())
            return retailComplain.get();
        throw new NotFoundException();
    }

    @Override
    public RetailComplain save(RetailComplain entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        RetailComplain retailComplain = new RetailComplain();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<RetailComplain> list = new HashSet<RetailComplain>();
            if (org.isPresent()) {
                list = org.get().getRetailComplains();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                retailComplain = retailComplainRepo.save(entity);
                list.add(retailComplain);
                org.get().setRetailComplains(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

    @Override
    public RetailComplain save(RetailComplain entity, long orgId) {
        RetailComplain retailComplain = new RetailComplain();
        Optional<Organization> org = orgRepo.findById(orgId);
        Set<RetailComplain> list = new HashSet<RetailComplain>();
        if (org.isPresent()) {
            list = org.get().getRetailComplains();
            entity.setCreatedAt(new Date());
            entity.setActive(true);
            entity.setUpdatedAt(new Date());
            entity.setCreatedBy(0);
            retailComplain = retailComplainRepo.save(entity);
            list.add(retailComplain);
            org.get().setRetailComplains(list);
            orgRepo.save(org.get());
        }

        return retailComplain;
    }
}
