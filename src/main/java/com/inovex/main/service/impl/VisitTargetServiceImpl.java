package com.inovex.main.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.VisitTarget;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.VisitTargetRepo;
import com.inovex.main.service.VisitTargetService;

@Service
@Transactional
public class VisitTargetServiceImpl implements VisitTargetService {
    @Autowired
    VisitTargetRepo visitTargetRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<VisitTarget> findById(Long id) {
        // TODO Auto-generated method stub
        return visitTargetRepo.findById(id);
    }

    @Override
    public List<VisitTarget> findAll() {
        // TODO Auto-generated method stub
        return visitTargetRepo.findAll();
    }

    @Override
    public VisitTarget getMonthlyTarget(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<VisitTarget> saveAll(List<VisitTarget> monthlyTargets) {
        // TODO Auto-generated method stub
        return visitTargetRepo.saveAll(monthlyTargets);
    }

    @Override
    public void deleteById(Long id) {
        visitTargetRepo.deleteById(id);
    }

    @Override
    public VisitTarget update(VisitTarget monthlyTarget, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<VisitTarget> findAllOfCurrentMonth(String employeeId, Date targetMonth) {
        // TODO Auto-generated method stub
        Calendar calendar1 = new GregorianCalendar();
        calendar1.setTime(targetMonth);
        int month1 = calendar1.get(Calendar.MONTH) + 1; // {0 - 11}
        int year1 = calendar1.get(Calendar.YEAR);
        return visitTargetRepo.findAllOfCurrentMonth(year1, month1, employeeId);
    }
    

    @Override
    public Optional<VisitTarget> findAllOfCurrentMonth(String employeeId) {
        // TODO Auto-generated method stub
        return visitTargetRepo.findAllOfCurrentMonth(employeeId);
    }

    @Override
    public VisitTarget save(VisitTarget entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        VisitTarget visitTarget = new VisitTarget();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<VisitTarget> list = new HashSet<VisitTarget>();
            if (org.isPresent()) {
                list = org.get().getVisitTargets();// ();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setOrgId(id);
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                visitTarget = visitTargetRepo.save(entity);
                list.add(visitTarget);
                org.get().setVisitTargets(list);/// (list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

}
