package com.inovex.main.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.OrderEcommerceModel;
import com.inovex.main.entity.OrderedProductEcommerce;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.PrimaryInventory;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.json.response.OrderedEcommerceProductResponse;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.OrderEcommerceService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.PrimaryInventoryService;

@Controller
@RequestMapping("/admin")
public class OrderEcommerceViewController {
	
	 @Value("${base.url}")
	 private String baseurl;
	 @Autowired
	 OrganizationService orgService;	 
	 @Autowired
	 DistributorService distService;   
	 @Autowired
	 OrderEcommerceService orderEcomService;
	 @Autowired
	 PrimaryInventoryService primaryInventoryService;
	 
	 @RequestMapping("/create-order-ecommerce")
	    public ModelAndView getOrderEcom(HttpServletRequest request, Model model) {
	        ModelAndView mv = new ModelAndView();
	        if (request.getSession().getAttribute("orgId") == null) {
	            mv.setViewName("redirect:/login");
	        } else {
	        	
	        	 long orgId = (long) request.getSession().getAttribute("orgId");
	             Optional<Organization> org = orgService.findById(orgId);
	             Set<ProductMapping> productList = new HashSet<>();
	             if (org.isPresent()) {
	                 productList = org.get().getProductMapping();
	             }
	             
//	            long userId = (long) request.getSession().getAttribute("userId");
//	            long distId = distService.findDistributorIdByUserId(userId);
//	            Optional<Distributor> dist = distService.findById(distId);
	            Optional<Distributor> dist = distService.findById(91l);
	            model.addAttribute("product", productList);
	            model.addAttribute("dist", dist.get());	        	
	            model.addAttribute("baseurl", baseurl);
	            mv.setViewName("admin-pages/orderEcommerce/create-order");
	        }
	        return mv;

	    }
	 
	 @RequestMapping("/order-ecommerce-home")
	    public ModelAndView getOrderEcommerce(HttpServletRequest request, Model model) {
	        ModelAndView mv = new ModelAndView();
	        if (request.getSession().getAttribute("orgId") == null) {
	            mv.setViewName("redirect:/login");
	        } else {
	            model.addAttribute("baseurl", baseurl);
	            mv.setViewName("admin-pages/orderEcommerce/order-home");
	        }
	        return mv;

	    }
	 
	 
	 @RequestMapping("/confirmed-order-details/{id}")
	    public ModelAndView confirmedOrderDetails(HttpServletRequest request, Model model, @PathVariable Long id) {
	        ModelAndView mv = new ModelAndView();
	        if (request.getSession().getAttribute("orgId") == null) {
	            mv.setViewName("redirect:/login");
	        } else {
	            long orgId = (long) request.getSession().getAttribute("orgId");
	            Optional<Organization> org = orgService.findById(orgId);
	            if (org.isPresent()) {
	                model.addAttribute("org", org.get());
	            }
	            Optional<OrderEcommerceModel> orderEcommerce = orderEcomService.findById(id);
	            String status;
	            status = orderEcommerce.get().getStatus();
	            System.out.println(status);
	            if (orderEcommerce.isPresent()) {
	                Set<OrderedEcommerceProductResponse> productResList = new HashSet<>();
	               // Set<DistributorRequisitionFreeProduct> freeItem = distRequisition.get().getFreeProductlist();
	                Set<OrderedProductEcommerce> productList = orderEcommerce.get().getOrderedProducts();

	                for (OrderedProductEcommerce distributorRequisitionProduct : productList) {
	                	OrderedEcommerceProductResponse res = new OrderedEcommerceProductResponse();
	                    res.setProductId(distributorRequisitionProduct.getProductId());
	                    res.setReceivedQty(distributorRequisitionProduct.getReceivedQty());
	                    res.setProductName(distributorRequisitionProduct.getProductName());
	                    res.setProductUnitPrice(distributorRequisitionProduct.getProductUnitPrice());
	                    res.setTotalPrice(distributorRequisitionProduct.getTotalPrice());
	                    res.setDiscount(distributorRequisitionProduct.getDiscount());
	                    res.setGrandTotal(distributorRequisitionProduct.getGrandTotal());
	                    Optional<PrimaryInventory> pi = primaryInventoryService
	                            .findByProductId(distributorRequisitionProduct.getProductId());
	                    if (pi.isPresent()) {
	                        long stock = pi.get().getOnHand();
	                        if (stock > distributorRequisitionProduct.getReceivedQty()) {
	                            res.setStockQuantity(stock);
	                            res.setStockStatus("In-Stock");
	                        } else {
	                            long out = stock - distributorRequisitionProduct.getReceivedQty();
	                            res.setStockQuantity(out);
	                            res.setStockStatus("Out-Of-Stock");
	                        }
	                    } else {
	                        res.setStockQuantity(0l);
	                        res.setStockStatus("Stock Not found");
	                    }
	                    productResList.add(res);

	                }
	                Optional<Distributor> dist = distService.findById(orderEcommerce.get().getBuiyerId());
	                model.addAttribute("counts", productResList);
	                model.addAttribute("dist", dist.get());
	              //  model.addAttribute("freeItem", freeItem);
	                model.addAttribute("distRequisition", orderEcommerce.get());
	            }
	            model.addAttribute("id", id);
	            model.addAttribute("status", status);
	            model.addAttribute("baseurl", baseurl);
	            mv.setViewName("admin-pages/orderEcommerce/confirmed-order-details");
	        }

	        return mv;

	    }

	    @RequestMapping("/add-order-ecomerce")
	    public ModelAndView addOrderEcomercce(HttpServletRequest request, Model model) {
	        ModelAndView mv = new ModelAndView();
	        if (request.getSession().getAttribute("orgId") == null) {
	            mv.setViewName("redirect:/login");
	        } else {
	            long orgId = (long) request.getSession().getAttribute("orgId");
	            Optional<Organization> org = orgService.findById(orgId);
	            if (org.isPresent()) {
	                Set<ProductMapping> productList = org.get().getProductMapping();
	                model.addAttribute("product", productList);
	                model.addAttribute("baseurl", baseurl);
	                mv.setViewName("admin-pages/orderEcommerce/order-add-form");
	            }

	        }
	        return mv;

	    }



}
