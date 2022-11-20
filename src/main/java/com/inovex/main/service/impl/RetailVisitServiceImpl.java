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
import com.inovex.main.entity.RetailVisit;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.RetailVisitRepo;
import com.inovex.main.service.RetailVisitService;

@Service
@Transactional
public class RetailVisitServiceImpl implements RetailVisitService {
    @Autowired
    RetailVisitRepo retailVisitRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<RetailVisit> findById(Long id) {
        // TODO Auto-generated method stub
        return retailVisitRepo.findById(id);
    }

    @Override
    public List<RetailVisit> findAll() {
        // TODO Auto-generated method stub
        return retailVisitRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        retailVisitRepo.deleteById(id);
    }

    @Override
    public RetailVisit update(RetailVisit retail, long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RetailVisit> findAllByEmpId(long submittedBy) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RetailVisit> findAllOfCurrentMonth(String employeeId) {
        // TODO Auto-generated method stub
        return retailVisitRepo.findAllOfCurrentMonth(employeeId);
    }

    @Override
    public List<RetailVisit> findAllByEmpIdAndCurDate(String srId) {
        // TODO Auto-generated method stub
        return retailVisitRepo.findAllByEmpIdAndCurDate(srId);
    }

    @Override
    public List<RetailVisit> getReatilVisitOfAnEmployeeByName(String employeeId, Date selectedDate) {
        // TODO Auto-generated method stub
        return retailVisitRepo.getRetailVisitofAnEmployeeByName(employeeId, selectedDate);
    }

    @Override
    public RetailVisit save(RetailVisit entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        RetailVisit retailVisit = new RetailVisit();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<RetailVisit> list = new HashSet<RetailVisit>();
            if (org.isPresent()) {
                list = org.get().getRetailVisits();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                retailVisit = retailVisitRepo.save(entity);
                list.add(retailVisit);
                org.get().setRetailVisits(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

    @Override
    public RetailVisit save(RetailVisit entity, long orgId) {
        // TODO Auto-generated method stub
        Optional<Organization> org = orgRepo.findById(orgId);
        RetailVisit entity1 = new RetailVisit();
        if (org.isPresent()) {
            Set<RetailVisit> clList = new HashSet<>();
            clList = org.get().getRetailVisits();
            entity.setCreatedAt(new Date());
            entity.setUpdatedAt(new Date());
            entity.setVisitDate(new Date());
            entity.setCreatedBy(1);

            entity1 = retailVisitRepo.save(entity);
            clList.add(entity1);
            org.get().setRetailVisits(clList);
            orgRepo.save(org.get());
        }
        return entity;

    }

}
