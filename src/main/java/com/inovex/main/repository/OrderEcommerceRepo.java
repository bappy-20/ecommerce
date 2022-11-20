package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.OrderEcommerceModel;

@Repository
public interface OrderEcommerceRepo extends JpaRepository<OrderEcommerceModel, Long> {
	
	
	@Transactional
    @Modifying
    @Query(value = "delete from organizations_order_ecommerce_model where organization_id = ?1 and order_ecommerce_model_id = ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long orderEcomId);


}
