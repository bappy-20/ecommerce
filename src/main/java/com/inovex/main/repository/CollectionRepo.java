package com.inovex.main.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.CollectionModel;

@Repository
public interface CollectionRepo extends JpaRepository<CollectionModel, Long> {
    @Query("SELECT l FROM CollectionModel l where l.orderId = :orderId")
    public List<CollectionModel> findCollectionDetails(@Param("orderId") String orderId);

    @Query("SELECT sum(l.recieveAmount) FROM CollectionModel l where l.orderId = :orderId")
    public Optional<String> getReceivedAmount(@Param("orderId") String orderId);

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_collection_models where organizations_collection_models.organization_id =?1 and collection_models_id =?2",  nativeQuery = true) 
    public int deleteFromOrg(long orgId, long collectionId);
    
    @Query(value = "SELECT e.order_id,e.recieve_amount,e.due_amount,e.total,e.collection_date FROM collections as e where collection_date >= ?1 and collection_date <= ?2 ",  nativeQuery = true)
    public List<Object> findAllFromDateRange(String date1,String date2);
     
    @Query(value = "SELECT sum(e.recieve_amount), sum(e.due_amount),sum(e.total) FROM collections as e where collection_date >= '2021-1-1' and collection_date <= '2023-1-1'",  nativeQuery = true)
    public List<Object> findTotalSumFromDateRange(String date1,String date2);  
      
}
