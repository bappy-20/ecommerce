package com.inovex.main.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.PriceUpdateHistory;

@Repository
public interface PriceUpdateRepo extends JpaRepository<PriceUpdateHistory, Long> {

    @Query(value = "select avg(product_price) from price_update_history where product_id=?1", nativeQuery = true)
    public Optional<Long> countTotalRecieve(long productId);

    Optional<PriceUpdateHistory> findByProductId(long productId);

    @Query(value = "SELECT e FROM PriceUpdateHistory e WHERE e.productId= :productId AND DATE(e.createdAt) >= :startDate AND DATE(e.createdAt) <= :endDate")
    List<PriceUpdateHistory> findAllByProduct(@Param("productId") long productId, @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
}
