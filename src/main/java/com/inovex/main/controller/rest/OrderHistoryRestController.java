package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.DeliveryDetails;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.OrderHistory;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProcessedOrderHistory;
import com.inovex.main.json.response.OrderHistoryResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrderHistoryRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.DeliveryDetailsService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.SecondaryInventoryService;
import com.sun.el.parser.ParseException;

@RestController
@RequestMapping("/api")
public class OrderHistoryRestController {
    @Autowired
    DeliveryDetailsService deliveryDetailsService;
    @Autowired
    OrderHistoryRepo orderHisoryService;
    @Autowired
    SecondaryInventoryService secondaryInventoryService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @RequestMapping(value = "/order-list", method = RequestMethod.GET)
    public Set<OrderHistory> getAllOrder() {
        Set<OrderHistory> orderHistoryList = new HashSet<OrderHistory>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            orderHistoryList = org.get().getOrderHistory();
        }
        return orderHistoryList;
    }

    @DeleteMapping("/delete-order-history/{id}")
    public ResponseData deleteArea(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            OrderHistory or = new OrderHistory();
            Optional<OrderHistory> order = orderHisoryService.findById(id);
            if (order.isPresent()) {
                order.get().setOrderStatus("Cancelled");
                order.get().setUpdatedAt(new Date());
                or = orderHisoryService.save(order.get());
            }
            if (or != null) {
                responseData.setStatus("success");
            } else {
                responseData.setStatus("unsuccess");
            }
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

    @RequestMapping(value = "/order-list-by-order-id/{orderId}", method = RequestMethod.GET)
    public OrderHistory getOrderyByorderId(@PathVariable String orderId) {

        return orderHisoryService.findByOrderId(orderId).get();
    }

    @RequestMapping(value = "/update-delivery-details", method = RequestMethod.GET)
    public String updateDeliveryDetails(@RequestParam("orderId") String orderId,
            @RequestParam("deliverydetails") String deliverydetails) throws ParseException {
        Set<DeliveryDetails> deliList = new HashSet<>();
        OrderHistory or = new OrderHistory();
        System.out.println(" deliverydetails " + deliverydetails);
        if (deliverydetails.equals("")) {
        } else {
            ProcessedOrderHistory processedOrder = new ProcessedOrderHistory();
            String[] namesList = deliverydetails.split("###");
            long totalPrice = 0;
            long totalDiscount = 0;
            long totalDiscountedPrice = 0;
            for (int i = 0; i < namesList.length; i++) {

                DeliveryDetails del = new DeliveryDetails();
                String[] namesList1 = namesList[i].split(",");
                String productId = namesList1[0];

                del.setProductId(Long.parseLong(namesList1[0]));
                del.setProductName(namesList1[1]);
                del.setProductQuantity(Long.parseLong(namesList1[2]));
                del.setTotalPrice(Long.parseLong(namesList1[3]));
                totalPrice += Long.parseLong(namesList1[3]);
                del.setDiscountType(namesList1[4]);
                del.setDiscountedPrice(Long.parseLong(namesList1[5]));
                totalDiscountedPrice += Long.parseLong(namesList1[5]);
                del.setActive(true);
                del.setCreatedAt(new Date());
                del.setUpdatedAt(new Date());
                del.setCreatedBy(1);
                deliList.add(del);

                long productId1 = Long.parseLong(productId);
                long stockquantity = secondaryInventoryService.getProductQuantity(productId1);
                System.out.println(" productId1 " + productId1 + " stockquantity " + stockquantity);

                String deliveryQuantity = namesList1[2];
                int deliveryQuantity1 = Integer.parseInt(deliveryQuantity);
                long shippedQuantity = Long.parseLong(deliveryQuantity);

                if (stockquantity > deliveryQuantity1) {

                    long onhand1 = stockquantity - deliveryQuantity1;

                    secondaryInventoryService.updateShippedandOnhandAfterOrderProceed(productId1, onhand1,
                            shippedQuantity);

                } else {
                    long onhand1 = stockquantity - deliveryQuantity1;
                    System.out.println("onhand1 : " + onhand1);
                    secondaryInventoryService.updateShippedandOnhandAfterOrderProceed(productId1, onhand1,
                            shippedQuantity);
                }

            }
            processedOrder.setTotal(totalPrice);
            totalDiscount = totalPrice - totalDiscountedPrice;
            processedOrder.setDiscount(totalDiscount);
            processedOrder.setGrandTotal(totalDiscountedPrice);
            processedOrder.setDeliveryDetails(deliList);
            Set<ProcessedOrderHistory> proceorderList = new HashSet<>();
            proceorderList.add(processedOrder);
            Optional<OrderHistory> order = orderHisoryService.findByOrderId(orderId);
            if (order.isPresent()) {

                order.get().setProcessedOrderHistory(proceorderList);
                order.get().setOrderStatus("Ready-To-Delivery");
                or = orderHisoryService.save(order.get());
            }

        }
        if (or != null) {
            return "success";
        } else {
            return "unsuccess";
        }

    }

    @GetMapping("/get-pending-order")
    public List<OrderHistory> viewPendingOrderHistory() {

        return orderHisoryService.getPendingOrderByCurdate();

    }

    @GetMapping("/get-total-order/{status}")
    public List<OrderHistory> viewTotalOrderHistory(@PathVariable String status) {

        if (status.equals("daily")) {
            return orderHisoryService.getTotalOrderByCurdate();
        } else {
            return orderHisoryService.findTotalMonthOrder();
        }

    }

    @GetMapping("/get-ready-to-delivery-order")
    public List<OrderHistory> viewReadyToDeliveryOrderHistory() {

        return orderHisoryService.getReadyToDeliveryOrderByCurdate();

    }

    @GetMapping("/get-shipment-order")
    public List<OrderHistory> viewShipedOrderHistory() {

        return orderHisoryService.getShippedOrderByCurdate();

    }

    @GetMapping("/get-delivered-order/{status}")
    public List<OrderHistory> viewDelivered1OrderHistory(@PathVariable String status) {

        if (status.equals("daily")) {
            return orderHisoryService.getDeliveredOrderByCurdate();
        } else {
            return orderHisoryService.findDeliveredCurrentMonth();
        }

    }

    @GetMapping("/get-canceled-order/{status}")
    public List<OrderHistory> viewCanceledOrderHistory(@PathVariable String status) {

        if (status.equals("daily")) {
            return orderHisoryService.getCancelledOrderByCurdate();
        } else {
            return orderHisoryService.findCancelledCurrentMonth();
        }

    }

    @GetMapping("/update-order-status/{empId}/{status}/{orderId}")
    public String viewTotalOrderHistory(@PathVariable String empId, @PathVariable String status,
            @PathVariable String orderId) {
        OrderHistory or = new OrderHistory();
        Optional<OrderHistory> order = orderHisoryService.findByOrderId(orderId);
        if (order.isPresent()) {
            order.get().setOrderStatus(status);
            order.get().setDeliveryMan(empId);
            or = orderHisoryService.save(order.get());
        }
        if (or != null)

        {
            return "success";
        } else {
            return "unsuccess";
        }

    }

    @GetMapping("/get-all-pending-order-by-date/{status}")
    public List<OrderHistory> getAllPendingOrderByDate(@PathVariable String status) {

        if (status.equals("daily")) {
            return orderHisoryService.getCancelledOrderByCurdate();
        } else {
            return orderHisoryService.findCancelledCurrentMonth();
        }

    }

    @GetMapping("/get-top-selling-product")
    public List<Object> getTopSellingProduct() {

        return deliveryDetailsService.findTopSellingProduct();
    }

    @GetMapping("/get-sale-by-date")
    public List<Object> getSaleByDate() {

        return orderHisoryService.getSaleByDate();
    }

    @GetMapping("/order-history-role")
    public List<OrderHistoryResponse> getAllOrderHistoryRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<OrderHistoryResponse> response = new ArrayList<OrderHistoryResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "orderDetails";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (OrderHistory mn : org.get().getOrderHistory()) {
                        OrderHistoryResponse res = new OrderHistoryResponse();
                        res.setId(mn.getId());
                        res.setOrderId(mn.getOrderId());
                        res.setRetailId(mn.getRetailId());
                        res.setRetailName(mn.getRetailName());
                        res.setRetailAddress(mn.getRetailAddress());
                        res.setTotal(mn.getTotal());
                        res.setDiscount(mn.getDiscount());
                        res.setGrandTotal(mn.getGrandTotal());
                        res.setDueAmount(mn.getDueAmount());
                        res.setCollectedAmount(mn.getCollectedAmount());
                        res.setOrderStatus(mn.getOrderStatus());
                        res.setDateReceive(mn.getDateReceive());
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        response.add(res);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

}
