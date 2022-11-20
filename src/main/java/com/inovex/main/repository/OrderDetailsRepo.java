package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.OrderDetails;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {

}
