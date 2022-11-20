package com.inovex.main.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.CashBackCampaign;
import com.inovex.main.json.response.CashBackCampaignResponse;

public interface CashBackCampaignService {
    public Set<CashBackCampaignResponse> getAvailableCashBack(HttpServletRequest request);

    Optional<CashBackCampaign> findById(long id);

    List<CashBackCampaign> findAll();

    void deleteById(long id, HttpServletRequest request);

    CashBackCampaign save(CashBackCampaign entity, HttpServletRequest request);

    CashBackCampaign update(CashBackCampaign campaign, Long id, HttpServletRequest request);
}
