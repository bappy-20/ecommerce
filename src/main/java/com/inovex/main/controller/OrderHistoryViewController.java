package com.inovex.main.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.CollectionModel;
import com.inovex.main.entity.DeliveryDetails;
import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.OrderDetails;
import com.inovex.main.entity.OrderHistory;
import com.inovex.main.entity.ProcessedOrderHistory;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.entity.ProductModel;
import com.inovex.main.entity.Retail;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.DeliveryDetailsResponse;
import com.inovex.main.json.response.OrderDetailsResponse;
import com.inovex.main.service.CollectionService;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.OrderHisoryService;
import com.inovex.main.service.ProductMappingService;
import com.inovex.main.service.ProductModelService;
import com.inovex.main.service.RetailService;
import com.inovex.main.service.UserService;

@Controller
@RequestMapping("/admin")
public class OrderHistoryViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    CollectionService collectionService;
    @Autowired
    OrderHisoryService orderHisoryService;
    @Autowired
    ProductModelService productService;
    @Autowired
    ProductMappingService productmappingSevice;
    @Autowired
    EmployeeService empService;
    @Autowired
    DistributorService distributorService;
    @Autowired
    RetailService retailService;
    @Autowired
    UserService userService;

    @GetMapping("/orderHistory")
    public ModelAndView viewOrderHistory(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            int dailyTotal = orderHisoryService.getTotalOrderByCurdate().size();
            int monthlyTotal = orderHisoryService.findTotalMonthOrder().size();
            int pending = orderHisoryService.getPendingOrderByCurdate().size();
            int shipped = orderHisoryService.getShippedOrderByCurdate().size();
            int readytoDelivery = orderHisoryService.getReadyToDeliveryOrderByCurdate().size();
            int dailyDelivered = orderHisoryService.getDeliveredOrderByCurdate().size();
            int monthlyDelivered = orderHisoryService.findDeliveredCurrentMonth().size();
            int dailyCanceled = orderHisoryService.getCancelledOrderByCurdate().size();
            int monthlyCanceled = orderHisoryService.findCancelledCurrentMonth().size();
            model.addAttribute("dailyTotal", dailyTotal);
            model.addAttribute("monthlyTotal", monthlyTotal);
            model.addAttribute("pending", pending);
            model.addAttribute("shipped", shipped);
            model.addAttribute("readytoDelivery", readytoDelivery);
            model.addAttribute("dailyDelivered", dailyDelivered);
            model.addAttribute("monthlyDelivered", monthlyDelivered);
            model.addAttribute("dailyCanceled", dailyCanceled);
            model.addAttribute("monthlyCanceled", monthlyCanceled);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/orderHistory/orderHistory-home");
        }
        return mv;
    }

    @GetMapping("/deliverable-orderHistory")
    public ModelAndView viewDeliveredOrderHistory(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/orderHistory/delivered-orderHistory-home");
        }
        return mv;
    }

    @GetMapping("/order-details/{orderId}")
    public ModelAndView getOrderDetails(HttpServletRequest request, Model model, @PathVariable String orderId) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long retail_id = 0;
            String retail_name = null;
            String retail_address = null;
            String distributor_name = null;
            String distributor_address = null;
            String distributor_phone = null;
            String contact_phone = null;
            String paymentStatus = null;
            String paymentMethod = null;
            Date recieveDate = null;
            long total = 0;
            long discount = 0;
            long grand_total = 0;
            String delivery = null;
            String status = null;
            Set<OrderDetailsResponse> orderDetailsRes = new HashSet<>();
            Set<DeliveryDetailsResponse> deliveryDetailsRes = new HashSet<>();

            Optional<OrderHistory> orderdetails = orderHisoryService.findByOrderId(orderId);

            if (orderdetails.isPresent()) {
                retail_id = orderdetails.get().getRetailId();
                retail_name = orderdetails.get().getRetailName();
                retail_address = orderdetails.get().getRetailAddress();
                Optional<Distributor> dis = distributorService.findById(orderdetails.get().getDistributorId());
                if (dis.isPresent()) {
                    distributor_address = dis.get().getDistributorAddress();
                    distributor_phone = dis.get().getDistributorPhone();
                }
                distributor_name = orderdetails.get().getDistributorName();
                contact_phone = orderdetails.get().getContactPhone();
                total = orderdetails.get().getTotal();
                discount = orderdetails.get().getDiscount();
                grand_total = orderdetails.get().getGrandTotal();
                status = orderdetails.get().getOrderStatus();
                paymentStatus = orderdetails.get().getPaymentStatus();
                paymentMethod = orderdetails.get().getPaymentMethod();
                recieveDate = orderdetails.get().getDateReceive();
            }
            model.addAttribute("retail_id", retail_id);
            model.addAttribute("orderId", orderId);
            model.addAttribute("retail_name", retail_name);
            model.addAttribute("retail_address", retail_address);
            model.addAttribute("distributor_name", distributor_name);
            model.addAttribute("distributor_address", distributor_address);
            model.addAttribute("distributor_phone", distributor_phone);

            model.addAttribute("contact_phone", contact_phone);
            model.addAttribute("paymentStatus", paymentStatus);
            model.addAttribute("paymentMethod", paymentMethod);
            model.addAttribute("recieveDate", recieveDate);

            model.addAttribute("total", total);
            model.addAttribute("discount", discount);
            model.addAttribute("grand_total", grand_total);
            model.addAttribute("delivery", delivery);
            model.addAttribute("orderStatus", status);
            Set<OrderDetails> order_details1 = orderdetails.get().getOrderDetails();
            for (OrderDetails orderDetails2 : order_details1) {
                OrderDetailsResponse res = new OrderDetailsResponse();
                //System.out.println(orderDetails2.getProductId());
                long productModelId = orderDetails2.getProductId();
                Optional<ProductMapping> productMapping = productmappingSevice.findByProductId(productModelId);
                long productMappingId = productMapping.get().getId();
                //System.out.println(productMappingId);
                Optional<ProductModel> product = productService.findById(orderDetails2.getProductId());
                //Optional<ProductMapping> product = productService.findById(orderDetails2.productMappingId());
                //res.setProductId(orderDetails2.getProductId());
                res.setProductId(productMappingId);
//                res.setProductName(orderDetails2.getProductName());
                res.setProductName(product.get().getProductName());
                
                res.setProductQuantity(orderDetails2.getProductQuantity());
                res.setDiscountedPrice(orderDetails2.getTotalPrice());
                boolean dis=false;
                if (product.isPresent()) {
                    if (dis) {
                        /*
                         * if (product.get().getDiscountType().equals("BDT")) { long discountedPrice =
                         * orderDetails2.getTotalPrice() +
                         * Long.parseLong(product.get().getAvailableDiscount());
                         * res.setTotalPrice(discountedPrice);
                         * res.setDiscountType(product.get().getAvailableDiscount() + "( " +
                         * product.get().getDiscountType() + " )"); } else { long calculateDiscount =
                         * (orderDetails2.getTotalPrice()
                         * (Long.parseLong(product.get().getAvailableDiscount()))) / 100; long
                         * discountedPrice = orderDetails2.getTotalPrice() + calculateDiscount;
                         * res.setTotalPrice(discountedPrice);
                         * res.setDiscountType(product.get().getAvailableDiscount() + " (%) "); }
                         */

                        // res.setDiscount(Long.parseLong(product.get().getAvailableDiscount()));
                    } else {
                        res.setDiscountType("0");
                        res.setDiscount(0);
                        res.setDiscountedPrice(orderDetails2.getTotalPrice());
                        res.setTotalPrice(orderDetails2.getTotalPrice());
                    }

                } else {
                    System.out.println("Product not found!");
                }
                orderDetailsRes.add(res);
            }
            long total1 = 0;
            long discount1 = 0;
            long grandTotal1 = 0;
            Set<DeliveryDetails> delivery1 = new HashSet<>();
            Set<ProcessedOrderHistory> processedOrder = orderdetails.get().getProcessedOrderHistory();
            for (ProcessedOrderHistory processedOrderHistory : processedOrder) {
                total1 = processedOrderHistory.getTotal();
                discount1 = processedOrderHistory.getDiscount();
                grandTotal1 = processedOrderHistory.getGrandTotal();
                delivery1 = processedOrderHistory.getDeliveryDetails();
            }
            List<CollectionModel> cl = collectionService.findCollectionDetails(orderId);
            model.addAttribute("collections", cl);
            model.addAttribute("counts", orderDetailsRes);
            model.addAttribute("delivery1", delivery1);
            model.addAttribute("total1", total1);
            model.addAttribute("discount1", discount1);
            model.addAttribute("grandTotal1", grandTotal1);
            model.addAttribute("baseurl", baseurl);
            List<User> userList = userService.findByUserType("DE");
            model.addAttribute("user", userList);
            mv.setViewName("admin-pages/orderHistory/order-details");
        }
        return mv;
    }

    @GetMapping("/pending-order")
    public ModelAndView viewPendingOrderHistory(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/orderHistory/pending-order");
        }
        return mv;
    }

    @GetMapping("/total-order/{status}")
    public ModelAndView viewTotalOrderHistory(HttpServletRequest request, @PathVariable String status, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("status", status);
            mv.setViewName("admin-pages/orderHistory/total-order");
        }
        return mv;
    }

    @GetMapping("/ready-to-delivery-order")
    public ModelAndView viewReadyToDeliveryOrderHistory(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/orderHistory/ready-to-delivery-order");
        }
        return mv;
    }

    @GetMapping("/shipment-order")
    public ModelAndView viewShipedOrderHistory(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/orderHistory/shipment-order");
        }
        return mv;
    }

    @GetMapping("/delivered-order/{status}")
    public ModelAndView viewDelivered1OrderHistory(HttpServletRequest request, @PathVariable String status,
            Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("status", status);
            mv.setViewName("admin-pages/orderHistory/delivered-order");
        }
        return mv;
    }

    @GetMapping("/canceled-order/{status}")
    public ModelAndView viewCanceledOrderHistory(HttpServletRequest request, @PathVariable String status, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("status", status);
            mv.setViewName("admin-pages/orderHistory/canceled-order");
        }
        return mv;
    }

    @RequestMapping("/return-order-home")
    public ModelAndView getReturnProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/orderHistory/order-return-home");
        }
        return mv;

    }

    @RequestMapping("/add-return-order")
    public ModelAndView ReturnPurchaseProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            List<Retail> retailList = retailService.findAll();
            model.addAttribute("retail", retailList);
            List<ProductModel> productList = productService.findAll();
            model.addAttribute("product", productList);
            List<OrderHistory> orderList = orderHisoryService.findAll();
            model.addAttribute("order", orderList);
            
            mv.setViewName("admin-pages/orderHistory/order-return-form");
           // mv.setViewName("admin-pages/orderHistory/order-return-form2");
        }
        return mv;

    }

    @RequestMapping("/return-product/{id}")
    public ModelAndView getReturnProductDetails(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {

            model.addAttribute("id", id);
            List<ProductModel> productList = productService.findAll();
            model.addAttribute("product", productList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/orderHistory/return-product-list-from-order");
        }
        return mv;

    }
}
