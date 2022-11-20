package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.OrderedProductEcommerce;

@Repository
public interface OrderEcommerceProductRepo extends JpaRepository<OrderedProductEcommerce, Long> {

}
