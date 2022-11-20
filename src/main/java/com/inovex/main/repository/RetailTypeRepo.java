package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.RetailType;

@Repository
public interface RetailTypeRepo extends JpaRepository<RetailType, Long> {
	@Transactional
	@Modifying
	@Query(value = "delete from organizations_retail_type where organization_id =?1 and retail_type_id =?2", nativeQuery = true)
	public int deleteFromOrg(long orgId, long RetailtypeId);
}
