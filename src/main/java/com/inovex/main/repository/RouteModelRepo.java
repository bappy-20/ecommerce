package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.RouteModel;

@Repository
public interface RouteModelRepo extends JpaRepository<RouteModel, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from distributor_routes where distributor_routes.distributor_id = ?1 and distributor_routes.routes_id= ?2", nativeQuery = true)
    public int deleteFromDist(long distId, long routeId);
    @Transactional
    @Modifying
    @Query(value = "delete from routes_markets where routes_markets.route_model_id = ?1", nativeQuery = true)
    public int deleteMkt(long routeId);
    @Transactional
    @Modifying
    @Query(value = "delete from organizations_routes where organization_id =?1 and routes_id =?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long routeId);
}
