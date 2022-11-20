package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Campaign;

public interface CampaignService {

    Optional<Campaign> findById(long id);

    List<Campaign> findAll();

    List<Campaign> findAllCampaignType(Long campaignType);

    Campaign getCampaign(long id);

    void deleteById(long id,HttpServletRequest request);
    
    Campaign save(Campaign entity, HttpServletRequest request);

    Campaign update(Campaign campaign, Long id, HttpServletRequest request);
}
