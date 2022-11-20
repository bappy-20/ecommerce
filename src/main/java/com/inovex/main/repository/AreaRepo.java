package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.AreaModel;

@Repository
public interface AreaRepo extends JpaRepository<AreaModel, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from regions_areas where regions_areas.areas_id= ?1", nativeQuery = true)
    public int deleteRef(long id);

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_area_models where organizations_area_models.organization_id = ?1 and area_models_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long areaId);

    @Transactional
    @Modifying
    @Query(value = "delete FROM areas_territories where area_model_id  =?1 ", nativeQuery = true)
    public void deleteFromAreaTerritory(long areaId);
}
