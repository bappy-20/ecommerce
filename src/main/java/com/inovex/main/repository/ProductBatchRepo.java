package com.inovex.main.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.ProductBatch;

@Repository
public interface ProductBatchRepo extends JpaRepository<ProductBatch, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from organizations_product_batch where organizations_product_batch.organization_id = ?1 and organizations_product_batch.product_batch_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long areaId);

    @Query(value = "SELECT * FROM product_batch where product_id = ?1", nativeQuery = true)
    List<ProductBatch> findAllBatchByProductId(Long productId);
}
