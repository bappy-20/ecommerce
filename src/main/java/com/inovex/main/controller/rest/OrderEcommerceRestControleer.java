package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.OrderEcommerceModel;
import com.inovex.main.entity.Organization;
import com.inovex.main.json.response.OrderEcommerceResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.OrderEcommerceService;

@RestController
@RequestMapping("/api")
public class OrderEcommerceRestControleer {
	
	@Autowired
	OrderEcommerceService ordEcommerceService;
	@Autowired
	DistributorService distService;
	@Autowired
	OrganizationRepository orgRepo;
	@Autowired
	MenuService menuService;
	
	@PostMapping("/save-order-ecommerce")
	public ResponseData createOrder(@RequestBody OrderEcommerceModel order, HttpServletRequest request) {
		ResponseData responseData = new ResponseData();

		try {
			OrderEcommerceModel dst = ordEcommerceService.save(order, request);
			responseData.setData(dst);
			responseData.setStatusCode(201);
			responseData.setMessage("Order created successfully");

			return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setData(null);
			responseData.setMessage(e.getMessage());
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());

			return responseData;
		}
	}
	
	@GetMapping("/order-ecommerce-role-list")
	public List<OrderEcommerceResponse> getAllOrderReqRole1(HttpServletRequest request) {
		Set<Menu> menu = new HashSet<Menu>();
		List<OrderEcommerceResponse> response = new ArrayList<OrderEcommerceResponse>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
		//	System.out.println(orgId);
			Optional<Organization> org = orgRepo.findById(orgId);
			//System.out.println(org.get().getOrgName());
			try {
				if (org.isPresent()) {
					menu = org.get().getMenu();
					String rolemanagement = "orderecommerce";
					List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

					for (OrderEcommerceModel orderEcom : org.get().getOrderEcommerceModel()) {
						OrderEcommerceResponse res = new OrderEcommerceResponse();
						res.setId(orderEcom.getId());
						Optional<Distributor> dis = distService.findById(orderEcom.getBuiyerId());
						if (dis.isPresent()) {
							res.setBuyerName(dis.get().getDistributorName());
						} else {
							res.setBuyerName("Not Found");
						}
						res.setCanEdit(rights.get(0));
						res.setCanDelete(rights.get(1));
						res.setOrderNumber(orderEcom.getOrderNumber());
						if (orderEcom.getStatus().equals("0")) {
							res.setTotalPrice(orderEcom.getTotalPrice());
							res.setNetDiscount(orderEcom.getNetDiscount());
							res.setGrandTotal(orderEcom.getGrandTotal());
							res.setStatus("Penging");
						}
						if (orderEcom.getStatus().equals("1")) {
							res.setStatus("Rejected");
							res.setTotalPrice(orderEcom.getTotalPrice());
							res.setNetDiscount(orderEcom.getNetDiscount());
							res.setGrandTotal(orderEcom.getGrandTotal());
						}
						if (orderEcom.getStatus().equals("2")) {
							res.setStatus("Approved");
							res.setTotalPrice(orderEcom.getTotalPrice());
							res.setNetDiscount(orderEcom.getNetDiscount());
							res.setGrandTotal(orderEcom.getGrandTotal());
						}
						
						response.add(res);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}
	
	  @PutMapping("/reject-order-ecom/{id}")
	    public ResponseData rejectOrderEcom(@PathVariable Long id) {

	        ResponseData responseData = new ResponseData();
	        try {
	        	OrderEcommerceModel lev = ordEcommerceService.rejectOrder(id);
	            responseData.setStatusCode(200);
	            responseData.setMessage("Rejected");
	            responseData.setData(lev);
	            return responseData;
	        } catch (Exception e) {
	            responseData.setData(null);
	            responseData.setStatusCode(500);
	            responseData.setMessage(e.getMessage());
	            return responseData;
	        }
	    }

	  @PutMapping("/approve-order-ecom/{id}")
	    public ResponseData appprovedOrderEcom(@PathVariable Long id,HttpServletRequest request) {

	        ResponseData responseData = new ResponseData();
	        try {
	        	OrderEcommerceModel lev = ordEcommerceService.approvedOrder(id,request);
	            responseData.setStatusCode(200);
	            responseData.setMessage("Rejected");
	            responseData.setData(lev);
	            return responseData;
	        } catch (Exception e) {
	            responseData.setData(null);
	            responseData.setStatusCode(500);
	            responseData.setMessage(e.getMessage());
	            return responseData;
	        }
	    }

	  @DeleteMapping("/delete-order-ecom/{id}")
	    public ResponseData deleteLeave(@PathVariable Long id,HttpServletRequest request) {

	        ResponseData responseData = new ResponseData();
	        try {
	        	ordEcommerceService.deleteById(id,request);
	            responseData.setStatusCode(204);
	            responseData.setMessage("delete successfully");
	            return responseData;
	        } catch (Exception e) {
	        	e.printStackTrace();
	            responseData.setData(null);
	            responseData.setStatusCode(500);
	            responseData.setMessage(e.getMessage());

	            return responseData;
	        }
	    }

	
}
