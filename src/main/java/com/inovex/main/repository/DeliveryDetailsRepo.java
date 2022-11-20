package com.inovex.main.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.DeliveryDetails;

@Repository
public interface DeliveryDetailsRepo extends JpaRepository<DeliveryDetails, Long> {

    @Query(value = "SELECT product_id,product_name,sum(product_quantity),"
            + "sum(total_price),sum(discounted_price) FROM delivery_details  where (cast(created_at  as date)) >=?1"
            + " and (cast(created_at as date)) <=?2 group by product_id order by sum(product_quantity) desc", nativeQuery = true)
    public List<Object> findProductByDate(Date startDate, Date endDate);

    @Query(value = "SELECT product_name,sum(product_quantity),"
            + "sum(total_price),sum(discounted_price) FROM delivery_details"
            + " group by product_id order by sum(product_quantity) desc limit 15", nativeQuery = true)
    public List<Object> findTopSellingProduct();
}
