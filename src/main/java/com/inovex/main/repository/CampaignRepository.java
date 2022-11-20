package com.inovex.main.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findAllByCampaignType(Long campaignType);
    
    @Transactional
    @Modifying
    @Query(value = " delete from organizations_campaigns where organization_id =?1  and campaigns_id =?2", nativeQuery = true)
    public int deleteFromOrg(long orgid, long id);   		
}