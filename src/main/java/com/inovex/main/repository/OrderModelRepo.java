package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.OrderEcommerceModel;

@Repository
public interface OrderModelRepo extends JpaRepository<OrderEcommerceModel, Long> {

}
