package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.OrderEcommerceModel;
import com.inovex.main.entity.OrderedProductEcommerce;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.PrimaryInventory;
import com.inovex.main.repository.OrderEcommerceRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.PrimaryInventoryRepo;
import com.inovex.main.service.OrderEcommerceService;


@Service
@Transactional
public class OrderEcommerceServiceImpl implements OrderEcommerceService {
	
	
	@Autowired
	OrderEcommerceRepo orderEcommerceRepo;
	@Autowired
	OrganizationRepository orgRepo;
	
	@Autowired
	PrimaryInventoryRepo primaryInventoryRepo;
	
	
	
	@Override
	public Optional<OrderEcommerceModel> findById(Long id) {
		// TODO Auto-generated method stub
		return orderEcommerceRepo.findById(id);
	}
//	@Override
//	public Optional<OrderEcommerceModel> findByProductId(Long productId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	@Override
	public List<OrderEcommerceModel> findAll() {
		// TODO Auto-generated method stub
		return orderEcommerceRepo.findAll();
	}
	@Override
	public OrderEcommerceModel getOrderEcommerceModel(Long id) {
		// TODO Auto-generated method stub
		///return null;
		  Optional<OrderEcommerceModel> orderEcomme = orderEcommerceRepo.findById(id);
	        if (orderEcomme.isPresent())
	            return orderEcomme.get();
	        throw new NotFoundException();
	}
	@Override
	public OrderEcommerceModel save(OrderEcommerceModel entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		//return null;
		
		OrderEcommerceModel orderEcommerce = new OrderEcommerceModel();
	        if (request.getSession().getAttribute("orgId") != null) {
	            long id = (long) request.getSession().getAttribute("orgId");
	            Optional<Organization> org = orgRepo.findById(id);
	            Set<OrderEcommerceModel> list = new HashSet<OrderEcommerceModel>();
	            if (org.isPresent()) {
	            	
	            	  list = org.get().getOrderEcommerceModel();
	                  entity.setCreatedAt(new Date());
	                  entity.setActive(true);
	                  entity.setUpdatedAt(new Date());
	                  entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
	                  orderEcommerce = orderEcommerceRepo.save(entity);
	                  list.add(orderEcommerce);
	                  org.get().setOrderEcommerceModel(list);
	                  orgRepo.save(org.get());
	                 // updateMapppingWithOrder(entity);
	            }

	        } else {
	            System.out.println("Org is Null");
	        }
	        return orderEcommerce;

	}
	
	@Override
	public long deleteById(Long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		if (request.getSession().getAttribute("orgId")!= null) {
    		long orgid = (long) request.getSession().getAttribute("orgId");
    		Optional<Organization> org = orgRepo.findById(orgid);
    			if (org.isPresent()) { 
    				Optional<OrderEcommerceModel> order = orderEcommerceRepo.findById(id);
    				if (order.isPresent()) {
    					String status = order.get().getStatus();
    					if (status.equals("0")) {
    						orderEcommerceRepo.deleteFromOrg(orgid, id);
    						orderEcommerceRepo.deleteById(id);
    						System.out.println("Deleted");
    						return 1;
    						
    	    				
						} else {
							//System.out.println("Not Deleted");
							System.out.println("Order is not pending, so you can not delete these");
							return 0;
						}
						
					} else {						
						System.out.println("Order is not found");

					}
				} 
		} else {
			 System.out.println("org not found");

		}
		return id;
		
	}
	@Override
	public OrderEcommerceModel update(OrderEcommerceModel orderEcommerceModel, Long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderEcommerceModel rejectOrder(Long id) {
		// TODO Auto-generated method stub
		//return null;
		OrderEcommerceModel leave = new OrderEcommerceModel();
	        Optional<OrderEcommerceModel> leaveStatusApproved = orderEcommerceRepo.findById(id);
	        if (leaveStatusApproved.isPresent()) {
	            leaveStatusApproved.get().setStatus("1");
	            leaveStatusApproved.get().setUpdatedAt(new Date());
	            leave = orderEcommerceRepo.save(leaveStatusApproved.get());
	        }
	        return leave;
	}
	@Override
	public OrderEcommerceModel approvedOrder(Long id,HttpServletRequest request) {
		// TODO Auto-generated method stub
		OrderEcommerceModel approve = new OrderEcommerceModel();
		 Optional<OrderEcommerceModel> leaveStatusApproved = orderEcommerceRepo.findById(id);
	        if (leaveStatusApproved.isPresent()) {
	            leaveStatusApproved.get().setStatus("2");
	            leaveStatusApproved.get().setUpdatedAt(new Date());
	            approve = orderEcommerceRepo.save(leaveStatusApproved.get());
	            updatePrimaryInventory(id, request);
	        }
	        return approve;
	}
	
	public void updatePrimaryInventory(Long id,HttpServletRequest request) {
		 long netTotal = 0;
	     long netDiscount = 0;
	     long netGrandTotal = 0;
	     
	     long productQuantity = 0;
	     
	     long onhand;
	     long onhandnow;
	       
		
		if (request.getSession().getAttribute("orgId")!= null) {
    		long orgid = (long) request.getSession().getAttribute("orgId");
    		Optional<Organization> org = orgRepo.findById(orgid);
    			if (org.isPresent()) { 
    				Optional<OrderEcommerceModel> order = orderEcommerceRepo.findById(id);
    				Set<OrderedProductEcommerce> orderProductList = new HashSet<OrderedProductEcommerce>();
    				if (order.isPresent()) {
    					orderProductList = order.get().getOrderedProducts();
    					
    					for (OrderedProductEcommerce orderedProductEcommerce : orderProductList) {
    						
    						long productId = orderedProductEcommerce.getProductId();
    						
    						productQuantity = productQuantity+orderedProductEcommerce.getReceivedQty();
    						
    						Optional<PrimaryInventory> primaryInventory = primaryInventoryRepo.findByProductId(productId);
    						
    						if (primaryInventory.isPresent()) {
    							onhand = primaryInventory.get().getOnHand();
    							onhandnow = onhand -productQuantity;
    							primaryInventory.get().setOnHand(onhandnow);
							} else {
								System.out.println("Primary Inventory Not found");

							}    						
    						
							
						}
    					
    					 
    				}
    			}
		}
    			
    	
		
	}
	


}
