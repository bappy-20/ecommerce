package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.CampaignType;

public interface CampaignTypesService {
	
	Optional<CampaignType> findById(long id);
	
	List<CampaignType> findAll();
	
	CampaignType getCampaign(long id);
	
	void deleteById(long id,HttpServletRequest request);
	
	CampaignType save(CampaignType entity,HttpServletRequest request);
	
	CampaignType update(CampaignType campaignType, Long id,HttpServletRequest request);
}
