package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.RegionModel;

@Repository
public interface RegionModelRepository extends JpaRepository<RegionModel, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from distributor_regions where distributor_regions.regions_id= ?1", nativeQuery = true)
    public int deleteRef(long id);
    
    @Transactional
    @Modifying
    @Query(value = "delete from organizations_regions where organization_id =?1 and regions_id =?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long regionId);
}
