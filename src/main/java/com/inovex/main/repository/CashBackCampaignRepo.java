package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.CashBackCampaign;

@Repository
public interface CashBackCampaignRepo extends JpaRepository<CashBackCampaign, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_cash_back_campaign where organizations_cash_back_campaign.organization_id = ?1 and organizations_cash_back_campaign.cash_back_campaign_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long cashBackCampId);
}
