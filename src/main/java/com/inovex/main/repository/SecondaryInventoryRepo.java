package com.inovex.main.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.SecondaryInventory;

@Repository
public interface SecondaryInventoryRepo extends JpaRepository<SecondaryInventory, Long> {

    public Optional<SecondaryInventory> findByProductId(long productId);

    @Query("SELECT l FROM SecondaryInventory l where l.productId = :productId AND l.distributorId = :distId ")
    public Optional<SecondaryInventory> findByProductIdAndDistributor(@Param("productId") long productId,
            @Param("distId") long distId);

    @Query("SELECT l FROM SecondaryInventory l where l.productId = :productId ")
    public List<SecondaryInventory> recvAndonhand(@Param("productId") String productId);

    @Query("SELECT l.productId FROM SecondaryInventory l where l.productId = :productId ")
    public String findProductId(@Param("productId") String productId);

    @Transactional
    @Modifying
    @Query("UPDATE SecondaryInventory app SET app.receivedInventory = :receivedInventory ,app.onHand = :onHand WHERE app.productId = :productId")
    int updatercvandOnhand(@Param("productId") String productId, @Param("receivedInventory") String receivedInventory,
            @Param("onHand") String onHand);

    @Transactional
    @Modifying
    @Query("UPDATE SecondaryInventory app SET app.receivedInventory = :receivedInventory ,app.onHand = :onHand,app.shippedInventory = :shippedInventory WHERE app.productId = :productId")
    int updatercvandOnhandAndShipped(@Param("productId") String productId,
            @Param("receivedInventory") String receivedInventory, @Param("onHand") String onHand,
            @Param("shippedInventory") String shippedInventory);

   @Query("SELECT l.onHand FROM SecondaryInventory l where l.productId = :productId")
    public Long getProductQuantity(@Param("productId") long productId);
    
    @Transactional
    @Modifying
    @Query("UPDATE SecondaryInventory app SET app.onHand = :onHand,app.shippedInventory = :shippedInventory  WHERE app.productId = :productId")
    int updateShippedandOnhandAfterOrderProceed(@Param("productId") long productId, @Param("onHand") long onHand,
            @Param("shippedInventory") long shippedInventory);
    
    @Query(value = "SELECT * FROM secondary_inventory where distributor_id = ?1",nativeQuery = true)
    public List<SecondaryInventory> findAllSecondaryInventoryBySingleDealer(long delaerId);

    /*
     * @Query(value =
     * "SELECT p FROM SecondaryInventory p where p.productId = ?1 and p.productCategory = ?2 and p.createdAt Between ?3 and ?4 "
     * ) List<SecondaryInventory> secondaryInventoryByCustomParam(
     * 
     * @Param("productId") long productId, @Param("productCategory") long
     * productCategory,
     * 
     * @Param("createdAt") Date startDate, @Param("createdAt") Date endDate);
     * 
     * @Query(value =
     * "SELECT p FROM SecondaryInventory p where p.productId = ?1 and p.productCategory = ?2 and p.createdAt Between ?3 and ?4 "
     * ) List<SecondaryInventory>
     * secondaryInventoryByProductIdAndProductCategory(@Param("productId") long
     * productId,
     * 
     * @Param("productCategory") long productCategory, @Param("createdAt") Date
     * startDate,
     * 
     * @Param("createdAt") Date endDate);
     * 
     * @Query(value =
     * "SELECT p FROM SecondaryInventory p where p.productCategory = ?1 and p.createdAt Between ?2 and ?3 "
     * ) List<SecondaryInventory>
     * secondaryInventoryByCateogoryAndDate(@Param("productCategory") long
     * productCategory,
     * 
     * @Param("createdAt") Date startDate, @Param("createdAt") Date endDate);
     * 
     * @Query(value =
     * "SELECT p FROM SecondaryInventory p where p.productId = ?1 and p.createdAt Between ?2 and ?3 "
     * ) List<SecondaryInventory>
     * secondaryInventoryByProductIdAndDate(@Param("productId") long productId,
     * 
     * @Param("createdAt") Date startDate, @Param("createdAt") Date endDate);
     * 
     * @Query(value =
     * "SELECT p FROM SecondaryInventory p where p.productId = ?1 and p.createdAt Between ?2 and ?3 "
     * ) List<SecondaryInventory>
     * secondaryInventoryByDistributorIdAndProductIdAndDate( @Param("productId")
     * long productId,
     * 
     * @Param("createdAt") Date startDate, @Param("createdAt") Date endDate);
     * 
     * @Query(value =
     * "SELECT p FROM SecondaryInventory p where  p.productCategory = ?1 and p.createdAt Between ?2 and ?3 "
     * ) List<SecondaryInventory> secondaryInventoryByDistributorIdCateogoryAndDate(
     * 
     * @Param("productCategory") long productCategory,
     * 
     * @Param("createdAt") Date startDate, @Param("createdAt") Date endDate);
     * 
     * @Query(value =
     * "SELECT p FROM SecondaryInventory p where p.createdAt Between ?1 and ?2 ")
     * List<SecondaryInventory> secondaryInventoryByDistributorIdAndDate(
     * 
     * @Param("createdAt") Date startDate, @Param("createdAt") Date endDate);
     * 
     * @Query(value =
     * "SELECT p FROM SecondaryInventory p where p.createdAt Between ?1 and ?2 ")
     * List<SecondaryInventory> secondaryInventoryByDate(@Param("createdAt") Date
     * startDate, @Param("createdAt") Date endDate);
     */
}
