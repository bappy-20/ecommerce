package com.inovex.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.IncomingProduct;

@Repository
public interface IncominggProductRepo extends JpaRepository<IncomingProduct, Long> {
    @Query(value = "select sum(received_qty) from incoming_product where product_id1=?1", nativeQuery = true)
    public Long countTotalRecieve(long productId);

    Optional<IncomingProduct> findByProductId1(long productId1);

}
