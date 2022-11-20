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

import com.inovex.main.entity.Campaign;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.CampaignRepository;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CampaignService;

@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    CampaignRepository campaignRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<Campaign> findById(long id) {
        // TODO Auto-generated method stub
        return campaignRepo.findById(id);
    }

    @Override
    public List<Campaign> findAll() {
        // TODO Auto-generated method stub
        return campaignRepo.findAll();
    }

    @Override
    public Campaign getCampaign(long id) {
        // TODO Auto-generated method stub
        Optional<Campaign> campaign = campaignRepo.findById(id);
        if (campaign.isPresent()) {
            return campaign.get();
        }
        throw new NotFoundException();
    }
    
    @Override
	public void deleteById(long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
    	if (request.getSession().getAttribute("orgId")!=null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				campaignRepo.deleteFromOrg(orgid, id);
				campaignRepo.deleteById(id);
			} else {
				System.out.println("Org not found");

			}
		}
		
	}


    @Override
    public Campaign save(Campaign entity, HttpServletRequest request) {

        Campaign camp = new Campaign();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<Campaign> campList = new HashSet<Campaign>();
            long activeStatus = 0;
            if (org.isPresent()) {
                campList = org.get().getCampaigns();
                entity.setActive(true);
                entity.setCreatedAt(new Date());
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                entity.setStatus(activeStatus);
                camp =campaignRepo.save(entity);
                campList.add(camp);
                org.get().setCampaigns(campList);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("org is null");
        }

        return camp;
    }

    @Override
    public Campaign update(Campaign campaign, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        long activeStatus = 1;
        Optional<Campaign> campaignUpdate = campaignRepo.findById(id);
        campaignUpdate.get().setCampaignName(campaign.getCampaignName());
        campaignUpdate.get().setCampaignType(campaign.getCampaignType());
        campaignUpdate.get().setStartTime(campaign.getStartTime());
        campaignUpdate.get().setExpiredDate(campaign.getExpiredDate());
        campaignUpdate.get().setStatus(activeStatus);
        campaignUpdate.get().setUpdatedAt(new Date());
        campaignUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        campaignRepo.save(campaignUpdate.get());
        return campaignUpdate.get();
    }

    @Override
    public List<Campaign> findAllCampaignType(Long campaignType) {
        // TODO Auto-generated method stub
        return campaignRepo.findAllByCampaignType(campaignType);
    }
}
