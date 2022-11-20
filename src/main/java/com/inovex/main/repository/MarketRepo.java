package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Market;

@Repository
public interface MarketRepo extends JpaRepository<Market, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from territory_model_markets where territory_model_markets.markets_id= ?1", nativeQuery = true)
    public int deleteRef(long id);

    @Transactional
    @Modifying
    @Query(value = "delete FROM organizations_mkt where organization_id = ?1 and mkt_id = ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long MarketId);

    @Transactional
    @Modifying
    @Query(value = "delete from market_retails where market_id = ?1 ", nativeQuery = true)
    public int deleteFromMarketReail(long marketId);

    @Transactional
    @Modifying
    @Query(value = "delete from user_mkts where mkts_id = ?1 ", nativeQuery = true)
    public int deleteFromUserMarket(long marketId);
}
