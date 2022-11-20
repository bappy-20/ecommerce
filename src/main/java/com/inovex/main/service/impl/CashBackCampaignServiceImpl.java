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

import com.inovex.main.entity.Campaign;
import com.inovex.main.entity.CashBackCampaign;
import com.inovex.main.entity.Organization;
import com.inovex.main.json.response.CashBackCampaignResponse;
import com.inovex.main.repository.CampaignRepository;
import com.inovex.main.repository.CashBackCampaignRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CashBackCampaignService;

@Service
@Transactional
public class CashBackCampaignServiceImpl implements CashBackCampaignService {
    @Autowired
    CashBackCampaignRepo cashBackCampaignRepo;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    CampaignRepository campRepo;

    @Override
    public Optional<CashBackCampaign> findById(long id) {
        // TODO Auto-generated method stub
        return cashBackCampaignRepo.findById(id);
    }

    @Override
    public List<CashBackCampaign> findAll() {
        // TODO Auto-generated method stub
        return cashBackCampaignRepo.findAll();
    }

    @Override
    public void deleteById(long id, HttpServletRequest request) {
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            cashBackCampaignRepo.deleteFromOrg(orgId, id);
            cashBackCampaignRepo.deleteById(id);
        }
    }

    @Override
    public CashBackCampaign save(CashBackCampaign entity, HttpServletRequest request) {
        CashBackCampaign camp = new CashBackCampaign();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<CashBackCampaign> campList = new HashSet<CashBackCampaign>();
            if (org.isPresent()) {
                campList = org.get().getCashBackCampaign();
                entity.setActive(true);
                entity.setCreatedAt(new Date());
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));

                entity = cashBackCampaignRepo.save(entity);
                campList.add(entity);
                org.get().setCashBackCampaign(campList);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("org is null");
        }

        return camp;
    }

    @Override
    public CashBackCampaign update(CashBackCampaign campaign, Long id, HttpServletRequest request) {

        Optional<CashBackCampaign> campaignUpdate = cashBackCampaignRepo.findById(id);
        if (campaignUpdate.isPresent()) {
            campaignUpdate.get().setRequiredInvoiceAmount(campaign.getRequiredInvoiceAmount());
            campaignUpdate.get().setUpdatedAt(new Date());
            campaignUpdate.get().setDiscountAmount(campaign.getDiscountAmount());
            campaignUpdate.get().setDiscountType(campaign.getDiscountType());
            campaignUpdate.get().setUpdatedBy((long) request.getSession().getAttribute("userId"));
            cashBackCampaignRepo.save(campaignUpdate.get());
            return campaignUpdate.get();
        } else {
            return null;
        }

    }

    @Override
    public Set<CashBackCampaignResponse> getAvailableCashBack(HttpServletRequest request) {
        Set<CashBackCampaign> campaignList = new HashSet<CashBackCampaign>();
        Set<CashBackCampaignResponse> cashBackcampaignList = new HashSet<CashBackCampaignResponse>();

        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            if (org.isPresent()) {
                campaignList = org.get().getCashBackCampaign();
                for (CashBackCampaign cbc : campaignList) {
                    CashBackCampaignResponse cbcRes = new CashBackCampaignResponse();
                    Optional<Campaign> camp = campRepo.findById(cbc.getCampaignId());
                    if (camp.isPresent()) {
                        if (camp.get().getStatus() == 1) {
                            cbcRes.setCampaignName(camp.get().getCampaignName());
                            cbcRes.setCampaignId(cbc.getId());
                            cbcRes.setId(cbc.getId());
                            cbcRes.setDiscountType(cbc.getDiscountType());
                            cbcRes.setDiscountAmount(cbc.getDiscountAmount());
                            cbcRes.setRequiredInvoiceAmount(cbc.getRequiredInvoiceAmount());
                            cbcRes.setCampaignStartDate(camp.get().getStartTime());
                            cbcRes.setCampaignEndDate(camp.get().getExpiredDate());
                            cashBackcampaignList.add(cbcRes);

                        }

                    }

                }
            }
        }
        return cashBackcampaignList;
    }
}
