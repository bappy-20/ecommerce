package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.TerritoryModel;

@Repository
public interface TerritoryRepository extends JpaRepository<TerritoryModel, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from areas_territories where areas_territories.territories_id= ?1", nativeQuery = true)
    public int deleteRef(long id);
    @Transactional
    @Modifying
    @Query(value = "delete from organizations_territory_models where organization_id =?1 and territory_models_id =?2", nativeQuery = true)
    public int deleteFromOrg(long orgId,long terrritoryId);
    @Transactional
    @Modifying
    @Query(value = "delete from territory_model_markets where territory_model_id =?1", nativeQuery = true)
    public int deleteTerrritoryMarket(long terrritoryId);
}
