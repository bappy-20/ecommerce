package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import com.inovex.main.entity.OrderEcommerceModel;

public interface OrderModelService {
	
	Optional<OrderEcommerceModel> findById(Long id);

    List<OrderEcommerceModel> findAll();

    OrderEcommerceModel save(OrderEcommerceModel entity);

    OrderEcommerceModel update(OrderEcommerceModel entity);

    List<OrderEcommerceModel> saveAll(List<OrderEcommerceModel> entities);

    void deleteById(Long id);


}
