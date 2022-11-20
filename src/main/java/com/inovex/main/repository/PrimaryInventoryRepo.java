package com.inovex.main.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.PrimaryInventory;

@Repository
public interface PrimaryInventoryRepo extends JpaRepository<PrimaryInventory, Long> {

    public Optional<PrimaryInventory> findByProductId(Long productId);

    @Query("SELECT l FROM PrimaryInventory l where l.productId = :productId ")
    public List<PrimaryInventory> recvAndonhand(@Param("productId") String productId);

    @Query("SELECT l.productId FROM PrimaryInventory l where l.productId = :productId ")
    public String findProductId(@Param("productId") String productId);

    @Transactional
    @Modifying
    @Query("UPDATE PrimaryInventory app SET app.receivedInventory = :receivedInventory ,app.onHand = :onHand WHERE app.productId = :productId")
    int updatercvandOnhand(@Param("productId") String productId, @Param("receivedInventory") String receivedInventory,
            @Param("onHand") String onHand);

    @Transactional
    @Modifying
    @Query("UPDATE PrimaryInventory app SET app.receivedInventory = :receivedInventory ,app.onHand = :onHand,app.shippedInventory = :shippedInventory WHERE app.productId = :productId")
    int updatercvandOnhandAndShipped(@Param("productId") String productId,
            @Param("receivedInventory") String receivedInventory, @Param("onHand") String onHand,
            @Param("shippedInventory") String shippedInventory);

    @Query("SELECT l.onHand FROM PrimaryInventory l where l.productId = :productId ")
    public Long getProductQuantity(@Param("productId") long productId);

    @Transactional
    @Modifying
    @Query("UPDATE PrimaryInventory app SET app.onHand = :onHand,app.shippedInventory = :shippedInventory  WHERE app.productId = :productId")
    int updateShippedandOnhandAfterOrderProceed(@Param("productId") long productId, @Param("onHand") long onHand,
            @Param("shippedInventory") long shippedInventory);
}
