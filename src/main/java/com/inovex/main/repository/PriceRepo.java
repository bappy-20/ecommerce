package com.inovex.main.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Price;

@Repository
public interface PriceRepo extends JpaRepository<Price, Long> {

    Optional<Price> findByProductId(Long productId);

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_price where organizations_price.organization_id = ?1 and organizations_price.price_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long priceId);
}
