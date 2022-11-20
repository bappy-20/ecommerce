package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.Menu;
import com.inovex.main.entity.OrderDetails;
import com.inovex.main.entity.OrderHistory;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductModel;
import com.inovex.main.entity.Retail;
import com.inovex.main.entity.ReturnOrder;
import com.inovex.main.entity.ReturnOrderProduct;
import com.inovex.main.json.response.IncomingProductResponse;
import com.inovex.main.json.response.OrderDetailsResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.ReturnOrderResponse;
import com.inovex.main.repository.OrderHistoryRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.OrderHisoryService;
import com.inovex.main.service.ProductMappingService;
import com.inovex.main.service.ProductModelService;
import com.inovex.main.service.RetailService;
import com.inovex.main.service.ReturnOrderService;
import com.sun.el.parser.ParseException;

@RestController
@RequestMapping("/api")
public class ReturnOrderRestController {

    @Autowired
    ReturnOrderService returnOrderService;
    @Autowired
    RetailService retailService;
    @Autowired
    ProductModelService productService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;
    @Autowired
    OrderHisoryService orderHistoryService;
    @Autowired
    OrderHistoryRepo orderHisoryRepo;
    @Autowired
    ProductMappingService productMappingService;

    @RequestMapping(value = "/submit-order-return", method = RequestMethod.POST)
    public String submitOrderReturn(@RequestParam("retailId") long retailId,
            @RequestParam("totalPrice") long totalPrice, @RequestParam("orderNumber") String orderNumber,
            @RequestParam("returnDate") String returnDate, @RequestParam("returnNote") String returnNote,
            @RequestParam("product") String producList, HttpServletRequest request) throws ParseException, java.text.ParseException {

        returnOrderService.save1(retailId, totalPrice, orderNumber, returnDate, returnNote, producList, request);
        return "success";
    }
    
    @RequestMapping(value = "/submit-order-return-again", method = RequestMethod.POST)
  public String submitOrderReturn(@RequestParam("retailId") long retailId,
          @RequestParam("totalPrice") long totalPrice, @RequestParam("orderNumber") String orderNumber,
          @RequestParam("returnDate") String returnDate, @RequestParam("returnNote") String returnNote,
          @RequestParam("product") JSONArray producList, HttpServletRequest request) throws ParseException, java.text.ParseException {

      returnOrderService.save2(retailId, totalPrice, orderNumber, returnDate, returnNote, producList, request);
      return "success";
  }
    


    @GetMapping("/get-return-order")
    public List<ReturnOrderResponse> getAllPurchase(HttpServletRequest request) {

        Set<ReturnOrder> returnProduct = new HashSet<ReturnOrder>();
        List<ReturnOrderResponse> returnProductRes = new ArrayList<ReturnOrderResponse>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                returnProduct = org.get().getReturnOrders();
                for (ReturnOrder order : returnProduct) {
                    ReturnOrderResponse res = new ReturnOrderResponse();
                    Optional<Retail> retail = retailService.findById(order.getRetailId());
                    res.setId(order.getId());
                    if (retail.isPresent()) {
                        res.setRetailName(retail.get().getRetailName());
                    } else {
                        res.setRetailName("not found!");
                    }
                    res.setOrderNumber(order.getOrderNumber());

                    res.setTotalPrice(order.getReturnPrice());

                    res.setReturnDate(order.getReturnDate());
                    res.setReturnNote(order.getReturnNote());
                    returnProductRes.add(res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return returnProductRes;
    }

    @GetMapping("/return-order-details/{id}")
    public List<IncomingProductResponse> getAllReturnPurchase(@PathVariable long id) {
        System.out.println("id is " + id);
        List<ReturnOrderProduct> returnProduct = new ArrayList<ReturnOrderProduct>();
        List<IncomingProductResponse> incomingProductRes = new ArrayList<IncomingProductResponse>();

        try {
            Optional<ReturnOrder> pp = returnOrderService.findById(id);
            if (pp.isPresent()) {
                returnProduct = pp.get().getReturnOrderProduct();
                for (ReturnOrderProduct incomingProduct2 : returnProduct) {
                    IncomingProductResponse res = new IncomingProductResponse();
                    Optional<ProductModel> product = productService.findById(incomingProduct2.getProductId());
                    res.setReceivedQty(incomingProduct2.getReceivedQty());
                    res.setPurchasePrice(incomingProduct2.getSalePrice());
                    res.setUnitPrice(incomingProduct2.getProductIdUnitprice());
                    res.setId(incomingProduct2.getId());
                    if (product.isPresent()) {
                        res.setProductName(product.get().getProductName());
                    } else {
                        res.setProductName("not found!");
                    }

                    incomingProductRes.add(res);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return incomingProductRes;
    }

    @GetMapping("/return-order-role")
    public List<ReturnOrderResponse> getAllReturnOrderRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<ReturnOrderResponse> response = new ArrayList<ReturnOrderResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "returnOrder";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

                    for (ReturnOrder order : org.get().getReturnOrders()) {
                        ReturnOrderResponse res = new ReturnOrderResponse();
                        Optional<Retail> retail = retailService.findById(order.getRetailId());
                        res.setId(order.getId());
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        if (retail.isPresent()) {
                            res.setRetailName(retail.get().getRetailName());
                        } else {
                            res.setRetailName("not found!");
                        }
                        res.setOrderNumber(order.getOrderNumber());

                        res.setTotalPrice(order.getReturnPrice());

                        res.setReturnDate(order.getReturnDate());
                        res.setReturnNote(order.getReturnNote());
                        response.add(res);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }
    
	@GetMapping("/get-all-order-by-retail-name/{retailId}")
	public List<OrderHistory> getAllOrderIdByRetailName(@PathVariable long retailId) {
		//OrderHistory ordHistory = new OrderHistory();
		List<OrderHistory> orderList = new ArrayList<OrderHistory>();
		try {
			orderList = orderHistoryService.findAllOrderHistoryByRetailId(retailId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;

	}

	@GetMapping("get-order-list-by-order-id/{orderId}")
	public OrderHistory getAllOrderListByOrderId(@PathVariable String orderId) {

		try {
			return orderHisoryRepo.findByOrderId(orderId).get();

		} catch (Exception e) {
		}

		return null;

	}
	@GetMapping("get-order-history-by-order-id/{orderId}")
	public ResponseData getOrderHistoryByOrderID(@PathVariable String orderId) {
		 //orderhistory = new OrderHistory();
		ResponseData responseData = new ResponseData();
		
		try {
			Optional<OrderHistory> orderhistory1 = orderHistoryService.findByOrderId(orderId);
			responseData.setData(orderhistory1.get());
			responseData.setStatusCode(200);
			responseData.setMessage("Succesfully Done");
			return responseData;
		} catch (Exception e) {
			// TODO: handle exception
			responseData.setData(null);
			responseData.setMessage(e.getMessage());
			responseData.setStatusCode(500);
			return responseData;
		}
		
	}
	
	@GetMapping("return-order-by-order-id/{orderId}")
	public ResponseData returnOrder(@PathVariable String orderId) {
		ResponseData responseData = new ResponseData();
		Set<OrderDetails> orderDetails = new HashSet<OrderDetails>();
		List<OrderDetailsResponse> orderDetailres = new ArrayList<OrderDetailsResponse>();
		
				try {
					Optional<OrderHistory> orderhistory1 = orderHistoryService.findByOrderId(orderId);
					if (orderhistory1.isPresent()) {
						orderDetails = orderhistory1.get().getOrderDetails();
						for (OrderDetails orderDetails2 : orderDetails) {
							OrderDetailsResponse res = new OrderDetailsResponse();
							Optional<ProductModel> product = productService.findById(orderDetails2.getProductId());
							if (product.isPresent()) {
								res.setProductName(product.get().getProductName());
 							    res.setProductId(product.get().getId());
							} else {
								res.setProductName("Not found");

							}
							//res.setProductId(pro);
							long totalprice = orderDetails2.getTotalPrice();
							long totalQuantity = orderDetails2.getProductQuantity();
							
							long unitPrice = 	totalprice/totalQuantity;	
							
							res.setProductUnitPrice(unitPrice);
							res.setProductQuantity(orderDetails2.getProductQuantity());
						
							
							orderDetailres.add(res);
							
						}
						responseData.setData(orderDetailres);
						
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				return responseData;
		
	}

}
