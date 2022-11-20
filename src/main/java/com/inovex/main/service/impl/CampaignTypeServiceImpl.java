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

import com.inovex.main.entity.CampaignType;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.CampaignTypeRepository;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CampaignTypesService;

@Service
@Transactional
public class CampaignTypeServiceImpl implements CampaignTypesService {
    @Autowired
    CampaignTypeRepository campTypeRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<CampaignType> findById(long id) {
        // TODO Auto-generated method stub
        return campTypeRepo.findById(id);
    }

    @Override
    public List<CampaignType> findAll() {
        // TODO Auto-generated method stub
        return campTypeRepo.findAll();
    }

    @Override
    public CampaignType getCampaign(long id) {
        // TODO Auto-generated method stub
        Optional<CampaignType> campaignType = campTypeRepo.findById(id);
        if (campaignType.isPresent()) {
            return campaignType.get();
        }
        throw new NotFoundException();
    }

    @Override
    public void deleteById(long id,HttpServletRequest request) {
        // TODO Auto-generated method stub
       // campTypeRepo.deleteById(id);
    	if (request.getSession().getAttribute("orgId")!=null) {
    		
    		long orgid = (long) request.getSession().getAttribute("orgId");
    		Optional<Organization> org = orgRepo.findById(orgid);
    		if(org.isPresent()) {
    			campTypeRepo.deleteFromOrg(orgid, id);
    			campTypeRepo.deleteById(id);
    		}
    		else {
    			System.out.println("org not found");
    		}
    	}

    }

    @Override
    public CampaignType save(CampaignType entity, HttpServletRequest request) {
        CampaignType cm = new CampaignType();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<CampaignType> campList = new HashSet<CampaignType>();
            if (org.isPresent()) {
                entity.setUpdatedAt(new Date());
                entity.setCreatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                entity.setActive(true);
                cm = campTypeRepo.save(entity);
                campList = org.get().getCampaignType();
                campList.add(cm);
                org.get().setCampaignType(campList);
                orgRepo.save(org.get());
            }
        }
        return cm;
    }

    @Override
    public CampaignType update(CampaignType campaignType, Long id, HttpServletRequest request) {

        Optional<CampaignType> campaignTypeUpdate = campTypeRepo.findById(id);
        campaignTypeUpdate.get().setCampaignType(campaignType.getCampaignType());
        campaignTypeUpdate.get().setNote(campaignType.getNote());
        campaignTypeUpdate.get().setUpdatedAt(new Date());
        campaignTypeUpdate.get().setUpdatedBy((long) request.getSession().getAttribute("userId"));
        campTypeRepo.save(campaignTypeUpdate.get());
        return campaignTypeUpdate.get();
    }

}
