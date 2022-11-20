package com.inovex.main.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.PurchaseProduct;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseProduct, Long> {
    Optional<PurchaseProduct> findByPurchaseNumber(String purchaseNumber);

    @Query(value = "select sum(grand_total) from purchase_product", nativeQuery = true)
    Optional<String> getTotalPurchase();

    @Query(value = "select sum(grand_total) from purchase_product where  cast(created_at as date)=curdate()", nativeQuery = true)
    Optional<String> getTotalPurchaseToday();

    @Query(value = "select sum(grand_total) from purchase_product where month(cast(created_at as date))=month(curdate()) and year(cast(created_at as date))=year(curdate())", nativeQuery = true)
    Optional<String> getTotalPurchaseMonth();

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_purchase_products where organizations_purchase_products.organization_id = ?1 and organizations_purchase_products.purchase_products_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long purchaseId);
}
