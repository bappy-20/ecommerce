package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.OrderEcommerceModel;

public interface OrderEcommerceService {
	
	 Optional<OrderEcommerceModel> findById(Long id);

	   // Optional<OrderEcommerceModel> findByProductId(Long productId);
	    
	    List<OrderEcommerceModel> findAll();

	    OrderEcommerceModel getOrderEcommerceModel(Long id);

	    OrderEcommerceModel save(OrderEcommerceModel entity, HttpServletRequest request);

	    long deleteById(Long id, HttpServletRequest request);

	    OrderEcommerceModel update(OrderEcommerceModel orderEcommerceModel, Long id, HttpServletRequest request);
	    
	    OrderEcommerceModel rejectOrder(Long id);
	    OrderEcommerceModel approvedOrder(Long id,HttpServletRequest request);
	    

}
