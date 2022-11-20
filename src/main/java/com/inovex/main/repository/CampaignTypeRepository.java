package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.CampaignType;

@Repository
public interface CampaignTypeRepository extends JpaRepository<CampaignType, Long> {
	
	@Transactional
    @Modifying
    @Query(value = " delete from organizations_campaign_type where organization_id =?1  and campaign_type_id =?2", nativeQuery = true)
    public int deleteFromOrg(long orgid, long id); 

}
