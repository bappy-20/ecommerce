package com.inovex.main.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.ReturnPurchaseProduct;

@Repository
public interface ReturnPurchaseProductRepo extends JpaRepository<ReturnPurchaseProduct, Long> {
    Optional<ReturnPurchaseProduct> findByPurchaseNumber(String purchaseNumber);

    @Transactional
    @Modifying
    @Query(value = "delete FROM organizations_return_purchase_products where organization_id = ?1 and return_purchase_products_id =?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long returnPurchaseId);
}
