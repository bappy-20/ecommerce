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

import com.inovex.main.entity.Market;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.Retail;
import com.inovex.main.repository.MarketRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.RetailRepo;
import com.inovex.main.service.RetailService;

@Service
@Transactional
public class RetailServiceImpl implements RetailService {

    @Autowired
    RetailRepo retailRepo;
    @Autowired
    MarketRepo mktRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<Retail> findById(Long id) {
        // TODO Auto-generated method stub
        return retailRepo.findById(id);
    }

    @Override
    public List<Retail> findAll() {
        // TODO Auto-generated method stub
        return retailRepo.findAll();
    }

    @Override
    public void deleteById(long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
                retailRepo.deleteFromOrg(orgid, id);
                retailRepo.deleteRef(id);
                retailRepo.deleteById(id);

            } else {
                System.out.println("org not found");

            }

        }
    }

    @Override
    public Retail update(Retail retail, long id, HttpServletRequest request) {
        Retail rt = new Retail();
        Optional<Retail> retailUpdate = retailRepo.findById(id);
        if (retailUpdate.isPresent()) {
            retailUpdate.get().setRetailName(retail.getRetailName());
            retailUpdate.get().setRetailType(retail.getRetailType());
            retailUpdate.get().setRetailAddress(retail.getRetailAddress());
            retailUpdate.get().setRetailOwner(retail.getRetailOwner());
            retailUpdate.get().setRetailPhone(retail.getRetailPhone());
            retailUpdate.get().setRetailLat(retail.getRetailLat());
            retailUpdate.get().setRetailLong(retail.getRetailLong());
            retailUpdate.get().setDistributorId(retail.getDistributorId());
            retailUpdate.get().setNationalId(retail.getNationalId());
            retailUpdate.get().setMarketId(retail.getMarketId());
            retailUpdate.get().setSubmittedBy(retail.getSubmittedBy());
            retailUpdate.get().setUpdatedAt(new Date());
            retailUpdate.get().setApprovedBy((long) request.getSession().getAttribute("userId"));
            retailUpdate.get().setStoreImage1(retail.getStoreImage1());
            rt = retailRepo.save(retailUpdate.get());
            return rt;
        } else {
            return rt;
        }
    }

    @Override
    public Retail getRetail(Long id) {
        Optional<Retail> retail = retailRepo.findById(id);
        if (retail.isPresent())
            return retail.get();
        throw new NotFoundException();
    }

    @Override
    public List<Retail> findAllByEmpId(String submittedBy) {

        return retailRepo.findAllByEmpId(submittedBy);
    }

    @Override
    public List<Retail> findAllPendingRetail() {
        // TODO Auto-generated method stub
        return retailRepo.findAllPendingRetail();
    }

    @Override
    public Retail ApproveRetail(long id, HttpServletRequest request) {
        Retail rt = new Retail();
        Optional<Retail> retailUpdate = retailRepo.findById(id);
        if (retailUpdate.isPresent()) {
            retailUpdate.get().setStatus("Approved");
            retailUpdate.get().setUpdatedAt(new Date());
            retailUpdate.get().setApprovedBy((long) request.getSession().getAttribute("userId"));
            rt = retailRepo.save(retailUpdate.get());
        }
        return rt;
    }

    @Override
    public List<Retail> findAllApprovedRetail() {
        // TODO Auto-generated method stub
        return retailRepo.findAllApprovedRetail();
    }

    @Override
    public Retail save(Retail entity, HttpServletRequest request) {
        // TODO Auto-generated method stub

        Retail retail = new Retail();
        if (request.getSession().getAttribute("orgId") != null) {

            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<Retail> list = new HashSet<Retail>();
            Set<Retail> list1 = new HashSet<Retail>();
            if (org.isPresent()) {
                list = org.get().getRetails();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                entity.setApprovedBy((long) request.getSession().getAttribute("userId"));
                entity.setSubmittedBy(Long.toString((long) request.getSession().getAttribute("userId")));
                entity.setApprovedDate(new Date());
                entity.setStatus("Approved");
                retail = retailRepo.save(entity);
                list.add(retail);
                Optional<Market> mkt = mktRepo.findById(entity.getMarketId());
                if (mkt.isPresent()) {
                    list1 = mkt.get().getRetails();
                    list1.add(retail);
                    mkt.get().setRetails(list1);
                    mktRepo.save(mkt.get());
                }
                org.get().setRetails(list);
                orgRepo.save(org.get());
            }
        } else {
            System.out.println("Org is null");
        }
        return null;
    }

    @Override
    public Retail save(Retail entity, long orgId) {
        // TODO Auto-generated method stub
        Retail retail = new Retail();
        Optional<Organization> org = orgRepo.findById(orgId);
        Set<Retail> list = new HashSet<Retail>();
        Set<Retail> list1 = new HashSet<Retail>();
        if (org.isPresent()) {
            list = org.get().getRetails();
            entity.setCreatedAt(new Date());
            entity.setActive(true);
            entity.setUpdatedAt(new Date());
            entity.setCreatedBy(0);
            entity.setApprovedBy(0);
            entity.setApprovedDate(new Date());
            entity.setStatus("Pending");
            retail = retailRepo.save(entity);
            list.add(retail);
            Optional<Market> mkt = mktRepo.findById(entity.getMarketId());
            if (mkt.isPresent()) {
                list1 = mkt.get().getRetails();
                list1.add(retail);
                mkt.get().setRetails(list1);
                mktRepo.save(mkt.get());
            }
            org.get().setRetails(list);
            orgRepo.save(org.get());
        }
        return retail;
    }
}
