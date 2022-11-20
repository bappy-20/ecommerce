package com.inovex.main.controller.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.DeliveryDetails;
import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.OrderDetails;
import com.inovex.main.entity.OrderHistory;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.entity.SecondaryInventory;
import com.inovex.main.json.response.SecondaryInventoryResponse;
import com.inovex.main.service.DeliveryDetailsService;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.OrderHisoryService;
import com.inovex.main.service.ProductMappingService;
import com.inovex.main.service.SecondaryInventoryService;

@RestController
@RequestMapping("/api")
public class ReportRestController {
    @Autowired
    OrderHisoryService orderHisoryService;
    @Autowired
    DeliveryDetailsService deliveryDetailsService;
    @Autowired 
    SecondaryInventoryService secondaryInventoryService;
    @Autowired
    ProductMappingService productMappingService;
    @Autowired
    DistributorService distributorService;
    

    @GetMapping("/get-all-pending-order-by-date/{startDate}/{endDate}")
    public Set<OrderHistory> getAllPendingOrderByDate(@PathVariable String startDate, @PathVariable String endDate,
            HttpServletRequest request) throws ParseException {
        Set<OrderHistory> orderList = new HashSet<>();
        try {
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = df2.parse(startDate);
            Date enDate = df2.parse(endDate);
            orderList = orderHisoryService.findAllByDateAndStatus(stDate, enDate, "Pending", request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;

    }

    @GetMapping("/get-all-delivered-order-by-date/{startDate}/{endDate}")
    public Set<OrderHistory> getAllTotalDeliveredOrderByDate(@PathVariable String startDate,
            @PathVariable String endDate, HttpServletRequest request) throws ParseException {
        Set<OrderHistory> orderList = new HashSet<>();
        try {
            JSONObject jObj = new JSONObject();
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = df2.parse(startDate);
            Date enDate = df2.parse(endDate);
            orderList = orderHisoryService.findAllByDateAndStatus(stDate, enDate, "Delivered", request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;

    }

    @GetMapping("/get-retail-wise-order-by-date/{startDate}/{endDate}/{retailId}/{ordetType}")
    public List<OrderHistory> getRetailWiseOrderByDate(@PathVariable String startDate, @PathVariable String endDate,
            @PathVariable long retailId, @PathVariable String ordetType) throws ParseException {
        List<OrderHistory> orderList = new ArrayList<>();
        try {
            JSONObject jObj = new JSONObject();
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = df2.parse(startDate);
            Date enDate = df2.parse(endDate);
            orderList = orderHisoryService.findRetailWiseByDateAndStatus(stDate, enDate, retailId, ordetType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;

    }

    @GetMapping("/get-order-details-by-date/{orderId}")
    public Set<OrderDetails> getAllOrderDetailsByDate(@PathVariable String orderId) {
        Set<OrderDetails> ode = new HashSet<>();
        try {

            Optional<OrderHistory> orderList = orderHisoryService.findByOrderIdAndStatus(orderId);
            if (orderList.isPresent()) {
                ode = orderList.get().getOrderDetails();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ode;

    }

    @GetMapping("/get-product-order-by-date/{startDate}/{endDate}")
    public List<DeliveryDetails> getProductorderByDate(@PathVariable String startDate, @PathVariable String endDate)
            throws ParseException {
        List<Object> orderList = new ArrayList<>();
        List<DeliveryDetails> dlList = new ArrayList<DeliveryDetails>();
        try {
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = df2.parse(startDate);
            Date enDate = df2.parse(endDate);
            orderList = deliveryDetailsService.findProductByDate(stDate, enDate);
            Iterator itr = orderList.iterator();
            while (itr.hasNext()) {
                DeliveryDetails dl = new DeliveryDetails();
                Object[] obj = (Object[]) itr.next();
                dl.setProductId(Long.parseLong(String.valueOf(obj[0])));
                dl.setProductName(String.valueOf(obj[1]));
                dl.setProductQuantity(Long.parseLong(String.valueOf(obj[2])));
                dl.setTotalPrice(Long.parseLong(String.valueOf(obj[3])));
                long discount = Long.parseLong(String.valueOf(obj[3])) - Long.parseLong(String.valueOf(obj[4]));
                dl.setDiscount(discount);
                dl.setDiscountedPrice(Long.parseLong(String.valueOf(obj[4])));
                dlList.add(dl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dlList;

    }
    
    @GetMapping("/secondary-inventory-current-stock-single-dealer")
    public List<SecondaryInventoryResponse> secondaryInventoryStock(HttpServletRequest request){
    	
    	List<SecondaryInventoryResponse> secondaryInventoryRes = new ArrayList<SecondaryInventoryResponse>();
    	List<SecondaryInventory> secondaryInventoryList = new ArrayList<SecondaryInventory>();
    	try {
    		if (request.getSession().getAttribute("dealerid") != null) {
    			long dealerId = (long) request.getSession().getAttribute("dealeriiiid");
        		secondaryInventoryList = secondaryInventoryService.getSecondaryInventoryCurrentStoctBySingleDealer(dealerId);
        		
        		for (SecondaryInventory secondaryInventory : secondaryInventoryList) {
					SecondaryInventoryResponse si = new SecondaryInventoryResponse();
					si.setId(secondaryInventory.getId());
					si.setDistributorId(secondaryInventory.getDistributorId());
					si.setOnHand(secondaryInventory.getOnHand());
					si.setReceivedInventory(secondaryInventory.getReceivedInventory());
					si.setMinimumQty(secondaryInventory.getMinimumQty());
					si.setProductId(secondaryInventory.getProductId());
					si.setSafetyStock(secondaryInventory.getSafetyStock());
					si.setStartingInventory(secondaryInventory.getStartingInventory());
					si.setShippedInventory(secondaryInventory.getShippedInventory());
					long productid = secondaryInventory.getProductId();
					Optional<ProductMapping> productMapping =productMappingService.findById(productid);
					if (productMapping.isPresent()) {
						si.setProductName(productMapping.get().getProductName());
					} else {
						si.setProductName("Product Not Found");;

					}	
					long distributorId = secondaryInventory.getDistributorId();
					Optional<Distributor> distributor = distributorService.findById(distributorId);
					if (distributor.isPresent()) {
						si.setDistributorName(distributor.get().getDistributorName());
					} else {
						si.setDistributorName("Distributor not Found");

					}
					secondaryInventoryRes.add(si);
				}
        				
    		}
    		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return secondaryInventoryRes;   	
    	
    }
    
    @GetMapping("/secondary-inventory-stock-of-selected-dealer/{dealerId}")
    public List<SecondaryInventoryResponse> secondaryInventoryStockofSelecdedDealer(HttpServletRequest request,@PathVariable Long dealerId){
    	
    	List<SecondaryInventoryResponse> secondaryInventoryRes = new ArrayList<SecondaryInventoryResponse>();
    	List<SecondaryInventory> secondaryInventoryList = new ArrayList<SecondaryInventory>();
    	try {
    		if (request.getSession().getAttribute("dealerid") != null) {
        		secondaryInventoryList = secondaryInventoryService.getSecondaryInventoryCurrentStoctBySingleDealer(dealerId);
        		
        		for (SecondaryInventory secondaryInventory : secondaryInventoryList) {
					SecondaryInventoryResponse si = new SecondaryInventoryResponse();
					si.setId(secondaryInventory.getId());
					si.setDistributorId(secondaryInventory.getDistributorId());
					si.setOnHand(secondaryInventory.getOnHand());
					si.setReceivedInventory(secondaryInventory.getReceivedInventory());
					si.setMinimumQty(secondaryInventory.getMinimumQty());
					si.setProductId(secondaryInventory.getProductId());
					si.setSafetyStock(secondaryInventory.getSafetyStock());
					si.setStartingInventory(secondaryInventory.getStartingInventory());
					si.setShippedInventory(secondaryInventory.getShippedInventory());
					long productid = secondaryInventory.getProductId();
					Optional<ProductMapping> productMapping =productMappingService.findById(productid);
					if (productMapping.isPresent()) {
						si.setProductName(productMapping.get().getProductName());
					} else {
						si.setProductName("Product Not Found");;

					}	
					long distributorId = secondaryInventory.getDistributorId();
					Optional<Distributor> distributor = distributorService.findById(distributorId);
					if (distributor.isPresent()) {
						si.setDistributorName(distributor.get().getDistributorName());
					} else {
						si.setDistributorName("Distributor not Found");

					}
					secondaryInventoryRes.add(si);
				}
        				
    		}
    		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return secondaryInventoryRes;   	
    	
    }


}
