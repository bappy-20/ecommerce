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
import com.inovex.main.entity.DistributorRequisition;
import com.inovex.main.entity.DistributorRequisitionAccounceApprove;
import com.inovex.main.entity.DistributorRequisitionDelivered;
import com.inovex.main.entity.DistributorRequisitionFreeProduct;
import com.inovex.main.entity.DistributorRequisitionOperationApprove;
import com.inovex.main.entity.DistributorRequisitionProduct;
import com.inovex.main.entity.DistributorRequisitionReceive;
import com.inovex.main.entity.DistributorRequisitionSupplyChainApprove;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.PrimaryInventory;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.entity.Supplier;
import com.inovex.main.json.response.CashBackCampaignResponse;
import com.inovex.main.json.response.DistributorRequisitionProductResponse;
import com.inovex.main.service.CashBackCampaignService;
import com.inovex.main.service.DistributorRequisitionService;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.PrimaryInventoryService;
import com.inovex.main.service.ProductModelService;
import com.inovex.main.service.ProductWiseCampaignService;
import com.inovex.main.service.SupplierService;
import com.inovex.main.service.UserService;

@Controller
@RequestMapping("/admin")
public class DistributorRequisitionViewController {

    @Value("${base.url}")
    private String baseurl;
    @Autowired
    SupplierService supplierService;
    @Autowired
    ProductModelService productService;
    @Autowired
    DistributorService distService;
    @Autowired
    OrganizationService orgService;
    @Autowired
    DistributorRequisitionService distReqService;
    @Autowired
    ProductWiseCampaignService productWiseService;
    @Autowired
    CashBackCampaignService cashBackCampaignService;
    @Autowired
    PrimaryInventoryService primaryInventoryService;
    @Autowired
    UserService userService;

    @RequestMapping("/distributor-requisition-for-all")
    public ModelAndView getDealerAllRequisition(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/distributor-requisition-home-for-all-dist");
        }
        return mv;

    }

    @RequestMapping("/create-distributor-requisition-for-all")
    public ModelAndView createRequisitionForAll(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<Supplier> supList = new HashSet<>();
            Set<ProductMapping> productList = new HashSet<>();
            Set<Distributor> distList = new HashSet<>();
            if (org.isPresent()) {
                productList = org.get().getProductMapping();
                supList = org.get().getSuppliers();
                distList = org.get().getDistributors();
            }
            /// Optional<Distributor> dist = distService.findById(5l);
            // long userId = (long) request.getSession().getAttribute("userId");
            // long distId = distService.findDistributorIdByUserId(userId);
            // Optional<Distributor> dist = distService.findById(distId);
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("supplier", supList);
            model.addAttribute("product", productList);
            model.addAttribute("distList", distList);
            // model.addAttribute("dist", dist.get());
            mv.setViewName("admin-pages/distributor-requisition/distributor-requisition-form-for-all-dist");
        }
        return mv;

    }

    @RequestMapping("/distributor-requisition")
    public ModelAndView getDealerProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/distributor-requisition-home");
        }
        return mv;

    }

    @RequestMapping("/create-distributor-requisition")
    public ModelAndView purchaseProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<Supplier> supList = new HashSet<>();
            Set<ProductMapping> productList = new HashSet<>();
            if (org.isPresent()) {
                productList = org.get().getProductMapping();
                supList = org.get().getSuppliers();
            }
//            long userId = (long) request.getSession().getAttribute("userId");
//            long distId = distService.findDistributorIdByUserId(userId);
//            Optional<Distributor> dist = distService.findById(distId);
            Optional<Distributor> dist = distService.findById(91l);
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("supplier", supList);
            model.addAttribute("product", productList);
            model.addAttribute("dist", dist.get());
            mv.setViewName("admin-pages/distributor-requisition/distributor-requisition-form");
        }
        return mv;

    }

    @RequestMapping("/distributor-requisition-details/{id}")
    public ModelAndView requisitionDetails(HttpServletRequest request, Model model, @PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Set<ProductMapping> productList1 = new HashSet<>();
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                productList1 = org.get().getProductMapping();
                model.addAttribute("org", org.get());
            }
            Optional<DistributorRequisition> distRequisition = distReqService.findById(id);
            if (distRequisition.isPresent()) {
                Set<DistributorRequisitionProduct> productList = distRequisition.get().getDistributorRequiredProduct();
                Set<DistributorRequisitionFreeProduct> freeItem = distRequisition.get().getFreeProductlist();
                Optional<Distributor> dist = distService.findById(distRequisition.get().getDealerId());
                if (distRequisition.get().getStatus().equals("0")) {
                    model.addAttribute("netTotal", distRequisition.get().getTotalPrice());
                    model.addAttribute("netDiscount", distRequisition.get().getNetDiscount());
                    model.addAttribute("netGrandTotal", distRequisition.get().getGrandTotal());
                    model.addAttribute("counts", distRequisition.get().getDistributorRequiredProduct());
                }
                if (distRequisition.get().getStatus().equals("1")) {
                    model.addAttribute("netTotal", distRequisition.get().getTotalPrice());
                    model.addAttribute("netDiscount", distRequisition.get().getNetDiscount());
                    model.addAttribute("netGrandTotal", distRequisition.get().getGrandTotal());
                    model.addAttribute("counts", distRequisition.get().getDistributorRequiredProduct());
                }
                if (distRequisition.get().getStatus().equals("2")) {
                    model.addAttribute("netTotal", distRequisition.get().getTotalPriceOfSupplyChain());
                    model.addAttribute("netDiscount", distRequisition.get().getNetDiscountOfSupplyChain());
                    model.addAttribute("netGrandTotal", distRequisition.get().getGrandTotalOfSupplyChain());
                    model.addAttribute("counts", distRequisition.get().getRequisitionSupplyChainApprove());
                }
                if (distRequisition.get().getStatus().equals("3")) {
                    model.addAttribute("netTotal", distRequisition.get().getTotalPriceOfAccounce());
                    model.addAttribute("netDiscount", distRequisition.get().getNetDiscountOfAccounce());
                    model.addAttribute("netGrandTotal", distRequisition.get().getGrandTotalOfAccounce());

                    model.addAttribute("counts", distRequisition.get().getNetDiscountOfAccounce());
                }
                if (distRequisition.get().getStatus().equals("4")) {
                    model.addAttribute("netTotal", distRequisition.get().getTotalPriceOfOperation());
                    model.addAttribute("netDiscount", distRequisition.get().getNetDiscountOfOperation());
                    model.addAttribute("netGrandTotal", distRequisition.get().getGrandTotalOfOperation());

                    model.addAttribute("counts", distRequisition.get().getRequisitionOperationApprove());
                }
                if (distRequisition.get().getStatus().equals("5")) {
                    model.addAttribute("netTotal", distRequisition.get().getTotalPriceOfDelivery());
                    model.addAttribute("netDiscount", distRequisition.get().getNetDiscountOfDelivery());
                    model.addAttribute("netGrandTotal", distRequisition.get().getGrandTotalOfDelivery());

                    model.addAttribute("counts", distRequisition.get().getRequisitionDelivered());
                }
                if (distRequisition.get().getStatus().equals("6")) {
                    model.addAttribute("netTotal", distRequisition.get().getTotalPriceOfDelivery());
                    model.addAttribute("netDiscount", distRequisition.get().getNetDiscountOfDelivery());
                    model.addAttribute("netGrandTotal", distRequisition.get().getGrandTotalOfDelivery());
                    model.addAttribute("counts", distRequisition.get().getDistributorRequiredReceive());
                }

                model.addAttribute("freeItem", freeItem);
                if (dist.isPresent()) {
                    model.addAttribute("dist", dist.get());
                } else {
                    model.addAttribute("dist", new Distributor());
                }

                model.addAttribute("product", productList1);
                model.addAttribute("distRequisition", distRequisition.get());
            }
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/distributor-requisition-details");
        }

        return mv;

    }

    @RequestMapping("/confirmed-requisition")
    public ModelAndView getConfirmedDealerProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/confirmed-distributor-requisition-home");
        }
        return mv;

    }

    @RequestMapping("/confirmed-requisition-details/{id}")
    public ModelAndView confirmedRequisitionDetails(HttpServletRequest request, Model model, @PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                model.addAttribute("org", org.get());
            }
            Optional<DistributorRequisition> distRequisition = distReqService.findById(id);
            String status;
            status = distRequisition.get().getStatus();
            System.out.println(status);
            if (distRequisition.isPresent()) {
                Set<DistributorRequisitionProductResponse> productResList = new HashSet<>();
                Set<DistributorRequisitionFreeProduct> freeItem = distRequisition.get().getFreeProductlist();
                Set<DistributorRequisitionProduct> productList = distRequisition.get().getDistributorRequiredProduct();

                for (DistributorRequisitionProduct distributorRequisitionProduct : productList) {
                    DistributorRequisitionProductResponse res = new DistributorRequisitionProductResponse();
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
                Optional<Distributor> dist = distService.findById(distRequisition.get().getDealerId());
                model.addAttribute("counts", productResList);
                model.addAttribute("dist", dist.get());
                model.addAttribute("freeItem", freeItem);
                model.addAttribute("distRequisition", distRequisition.get());
            }
            model.addAttribute("status", status);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/confirmed-distributor-requisition-details");
        }

        return mv;

    }

    // for accounce
    @RequestMapping("/processed-requisition")
    public ModelAndView getProcessedDealerProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/processed-distributor-requisition-home");
        }
        return mv;

    }

    @RequestMapping("/processed-requisition-details/{id}")
    public ModelAndView processedRequisitionDetails(HttpServletRequest request, Model model, @PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long netTotal = 0;
            long netDiscount = 0;
            long netGrandTotal = 0;

            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                model.addAttribute("org", org.get());
            }
            Optional<DistributorRequisition> distRequisition = distReqService.findById(id);
            String status;
            status = distRequisition.get().getStatus();
            if (distRequisition.isPresent()) {
                Set<DistributorRequisitionProductResponse> productResList = new HashSet<>();
                Set<DistributorRequisitionFreeProduct> freeItem = distRequisition.get().getFreeProductlist();

                Set<DistributorRequisitionSupplyChainApprove> productList = distRequisition.get()
                        .getRequisitionSupplyChainApprove();
                for (DistributorRequisitionSupplyChainApprove distributorRequisitionProduct : productList) {
                    netTotal += Long.parseLong(distributorRequisitionProduct.getTotalPrice());
                    netDiscount += Long.parseLong(distributorRequisitionProduct.getDiscount());
                    netGrandTotal += Long.parseLong(distributorRequisitionProduct.getGrandTotal());

                    DistributorRequisitionProductResponse res = new DistributorRequisitionProductResponse();
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
                        res.setStockQuantity(0);
                        res.setStockStatus("Stock Not found");
                    }
                    productResList.add(res);

                }
                Set<CashBackCampaignResponse> cashBackcampaignList = cashBackCampaignService
                        .getAvailableCashBack(request);
                for (CashBackCampaignResponse cbc : cashBackcampaignList) {
                    if (netTotal >= Long.parseLong(cbc.getRequiredInvoiceAmount())) {
                        if (cbc.getDiscountType().equals("BDT")) {
                            netDiscount += Long.parseLong(cbc.getDiscountAmount());
                            netGrandTotal -= Long.parseLong(cbc.getDiscountAmount());
                        } else {
                            long discount = (Long.parseLong(cbc.getDiscountAmount()) * netTotal) / 100;
                            netDiscount += discount;
                            netGrandTotal -= discount;
                        }

                    }
                }
                Optional<Distributor> dist = distService.findById(distRequisition.get().getDealerId());
                model.addAttribute("counts", productResList);
                model.addAttribute("dist", dist.get());
                model.addAttribute("freeItem", freeItem);
                model.addAttribute("distRequisition", distRequisition.get());
                model.addAttribute("netTotal", netTotal);
                model.addAttribute("netDiscount", netDiscount);
                model.addAttribute("netGrandTotal", netGrandTotal);
            }
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("status", status);
            mv.setViewName("admin-pages/distributor-requisition/processed-distributor-requisition-details");
        }

        return mv;

    }

    @RequestMapping("/approved-requisition")
    public ModelAndView getapprovedDealerProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/approved-distributor-requisition-home");
        }
        return mv;

    }

    @RequestMapping("/approved-requisition-details/{id}")
    public ModelAndView approvedRequisitionDetails(HttpServletRequest request, Model model, @PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long netTotal = 0;
            long netDiscount = 0;
            long netGrandTotal = 0;
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                model.addAttribute("org", org.get());
            }
            Optional<DistributorRequisition> distRequisition = distReqService.findById(id);
            String status;
            status = distRequisition.get().getStatus();
            if (distRequisition.isPresent()) {
                Set<DistributorRequisitionProductResponse> productResList = new HashSet<>();
                Set<DistributorRequisitionFreeProduct> freeItem = distRequisition.get().getFreeProductlist();

                Set<DistributorRequisitionAccounceApprove> productList = distRequisition.get()
                        .getRequisitionAccounceApprove();
                for (DistributorRequisitionAccounceApprove distributorRequisitionProduct : productList) {
                    netTotal += Long.parseLong(distributorRequisitionProduct.getTotalPrice());
                    netDiscount += Long.parseLong(distributorRequisitionProduct.getDiscount());
                    netGrandTotal += Long.parseLong(distributorRequisitionProduct.getGrandTotal());
                    DistributorRequisitionProductResponse res = new DistributorRequisitionProductResponse();
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
                        res.setStockQuantity(0);
                        res.setStockStatus("Stock Not found");
                    }
                    productResList.add(res);

                }
                Set<CashBackCampaignResponse> cashBackcampaignList = cashBackCampaignService
                        .getAvailableCashBack(request);
                for (CashBackCampaignResponse cbc : cashBackcampaignList) {
                    if (netTotal >= Long.parseLong(cbc.getRequiredInvoiceAmount())) {
                        if (cbc.getDiscountType().equals("BDT")) {
                            netDiscount += Long.parseLong(cbc.getDiscountAmount());
                            netGrandTotal -= Long.parseLong(cbc.getDiscountAmount());
                        } else {
                            long discount = (Long.parseLong(cbc.getDiscountAmount()) * netTotal) / 100;
                            netDiscount += discount;
                            netGrandTotal -= discount;
                        }

                    }
                }
                Optional<Distributor> dist = distService.findById(distRequisition.get().getDealerId());
                model.addAttribute("counts", productResList);
                model.addAttribute("dist", dist.get());
                model.addAttribute("freeItem", freeItem);
                model.addAttribute("distRequisition", distRequisition.get());
                model.addAttribute("netTotal", netTotal);
                model.addAttribute("netDiscount", netDiscount);
                model.addAttribute("netGrandTotal", netGrandTotal);
            }
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("status", status);
            mv.setViewName("admin-pages/distributor-requisition/approved-distributor-requisition-details");
        }

        return mv;

    }

    // for war-house
    @RequestMapping("/verified-requisition")
    public ModelAndView getverifiedDealerProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/verified-distributor-requisition-home");
        }
        return mv;

    }

    @RequestMapping("/verified-requisition-details/{id}")
    public ModelAndView verifiedRequisitionDetails(HttpServletRequest request, Model model, @PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long netTotal = 0;
            long netDiscount = 0;
            long netGrandTotal = 0;
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                model.addAttribute("org", org.get());
            }
            Optional<DistributorRequisition> distRequisition = distReqService.findById(id);
            if (distRequisition.isPresent()) {
                Set<DistributorRequisitionProductResponse> productResList = new HashSet<>();
                Set<DistributorRequisitionFreeProduct> freeItem = distRequisition.get().getFreeProductlist();

                Set<DistributorRequisitionOperationApprove> productList = distRequisition.get()
                        .getRequisitionOperationApprove();
                for (DistributorRequisitionOperationApprove distributorRequisitionProduct : productList) {
                    netTotal += Long.parseLong(distributorRequisitionProduct.getTotalPrice());
                    netDiscount += Long.parseLong(distributorRequisitionProduct.getDiscount());
                    netGrandTotal += Long.parseLong(distributorRequisitionProduct.getGrandTotal());
                    DistributorRequisitionProductResponse res = new DistributorRequisitionProductResponse();
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
                        res.setStockQuantity(0);
                        res.setStockStatus("Stock Not found");
                    }
                    productResList.add(res);

                }
                Set<CashBackCampaignResponse> cashBackcampaignList = cashBackCampaignService
                        .getAvailableCashBack(request);
                for (CashBackCampaignResponse cbc : cashBackcampaignList) {
                    if (netTotal >= Long.parseLong(cbc.getRequiredInvoiceAmount())) {
                        if (cbc.getDiscountType().equals("BDT")) {
                            netDiscount += Long.parseLong(cbc.getDiscountAmount());
                            netGrandTotal -= Long.parseLong(cbc.getDiscountAmount());
                        } else {
                            long discount = (Long.parseLong(cbc.getDiscountAmount()) * netTotal) / 100;
                            netDiscount += discount;
                            netGrandTotal -= discount;
                        }

                    }
                }
                Optional<Distributor> dist = distService.findById(distRequisition.get().getDealerId());
                model.addAttribute("counts", productResList);
                model.addAttribute("dist", dist.get());
                model.addAttribute("freeItem", freeItem);
                model.addAttribute("distRequisition", distRequisition.get());
                model.addAttribute("netTotal", netTotal);
                model.addAttribute("netDiscount", netDiscount);
                model.addAttribute("netGrandTotal", netGrandTotal);
            }
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/verified-distributor-requisition-details");
        }

        return mv;

    }

    // for partial delivery
    @RequestMapping("/partial-delivered-requisition")
    public ModelAndView getPartialDealerProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/partial-delivered-requisition-home");
        }
        return mv;

    }

    @RequestMapping("/partial-requisition-details/{id}")
    public ModelAndView partialDeliveredRequisitionDetails(HttpServletRequest request, Model model,
            @PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long netTotal = 0;
            long netDiscount = 0;
            long netGrandTotal = 0;
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                model.addAttribute("org", org.get());
            }
            Optional<DistributorRequisition> distRequisition = distReqService.findById(id);
            if (distRequisition.isPresent()) {
                Set<DistributorRequisitionProductResponse> productResList = new HashSet<>();
                Set<DistributorRequisitionFreeProduct> freeItem = distRequisition.get().getFreeProductlist();

                Set<DistributorRequisitionDelivered> productList = distRequisition.get().getRequisitionDelivered();
                for (DistributorRequisitionDelivered distributorRequisitionProduct : productList) {
                    netTotal += Long.parseLong(distributorRequisitionProduct.getTotalPrice());
                    netDiscount += Long.parseLong(distributorRequisitionProduct.getDiscount());
                    netGrandTotal += Long.parseLong(distributorRequisitionProduct.getGrandTotal());
                    DistributorRequisitionProductResponse res = new DistributorRequisitionProductResponse();
                    res.setProductId(distributorRequisitionProduct.getProductId());
                    res.setReceivedQty(distributorRequisitionProduct.getReceivedQty());
                    res.setProductName(distributorRequisitionProduct.getProductName());
                    res.setProductUnitPrice(distributorRequisitionProduct.getProductUnitPrice());
                    res.setTotalPrice(distributorRequisitionProduct.getTotalPrice());
                    res.setDiscount(distributorRequisitionProduct.getDiscount());
                    res.setGrandTotal(distributorRequisitionProduct.getGrandTotal());
                    res.setDeliveredQuantity(distributorRequisitionProduct.getDeliveredQuantity());
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
                        res.setStockQuantity(0);
                        res.setStockStatus("Stock Not found");
                    }
                    productResList.add(res);

                }
                Set<CashBackCampaignResponse> cashBackcampaignList = cashBackCampaignService
                        .getAvailableCashBack(request);
                for (CashBackCampaignResponse cbc : cashBackcampaignList) {
                    if (netTotal >= Long.parseLong(cbc.getRequiredInvoiceAmount())) {
                        if (cbc.getDiscountType().equals("BDT")) {
                            netDiscount += Long.parseLong(cbc.getDiscountAmount());
                            netGrandTotal -= Long.parseLong(cbc.getDiscountAmount());
                        } else {
                            long discount = (Long.parseLong(cbc.getDiscountAmount()) * netTotal) / 100;
                            netDiscount += discount;
                            netGrandTotal -= discount;
                        }

                    }
                }
                Optional<Distributor> dist = distService.findById(distRequisition.get().getDealerId());
                model.addAttribute("counts", productResList);
                model.addAttribute("dist", dist.get());
                model.addAttribute("freeItem", freeItem);
                model.addAttribute("distRequisition", distRequisition.get());
                model.addAttribute("netTotal", netTotal);
                model.addAttribute("netDiscount", netDiscount);
                model.addAttribute("netGrandTotal", netGrandTotal);
            }
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/partial-delivered-requisition-details");
        }

        return mv;

    }

    // for delivery
    @RequestMapping("/delivered-requisition")
    public ModelAndView getdeliveredDealerProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/delivered-distributor-requisition-home");
        }
        return mv;

    }

    @RequestMapping("/delivered-requisition-details/{id}")
    public ModelAndView deliveredRequisitionDetails(HttpServletRequest request, Model model, @PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long netTotal = 0;
            long netDiscount = 0;
            long netGrandTotal = 0;
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                model.addAttribute("org", org.get());
            }
            Optional<DistributorRequisition> distRequisition = distReqService.findById(id);
            if (distRequisition.isPresent()) {
                Set<DistributorRequisitionProductResponse> productResList = new HashSet<>();
                Set<DistributorRequisitionFreeProduct> freeItem = distRequisition.get().getFreeProductlist();

                Set<DistributorRequisitionDelivered> productList = distRequisition.get().getRequisitionDelivered();
                for (DistributorRequisitionDelivered distributorRequisitionProduct : productList) {
                    netTotal += Long.parseLong(distributorRequisitionProduct.getTotalPrice());
                    netDiscount += Long.parseLong(distributorRequisitionProduct.getDiscount());
                    netGrandTotal += Long.parseLong(distributorRequisitionProduct.getGrandTotal());
                    DistributorRequisitionProductResponse res = new DistributorRequisitionProductResponse();
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
                        res.setStockQuantity(0);
                        res.setStockStatus("Stock Not found");
                    }
                    productResList.add(res);

                }
                Set<CashBackCampaignResponse> cashBackcampaignList = cashBackCampaignService
                        .getAvailableCashBack(request);
                for (CashBackCampaignResponse cbc : cashBackcampaignList) {
                    if (netTotal >= Long.parseLong(cbc.getRequiredInvoiceAmount())) {
                        if (cbc.getDiscountType().equals("BDT")) {
                            netDiscount += Long.parseLong(cbc.getDiscountAmount());
                            netGrandTotal -= Long.parseLong(cbc.getDiscountAmount());
                        } else {
                            long discount = (Long.parseLong(cbc.getDiscountAmount()) * netTotal) / 100;
                            netDiscount += discount;
                            netGrandTotal -= discount;
                        }

                    }
                }
                Optional<Distributor> dist = distService.findById(distRequisition.get().getDealerId());
                model.addAttribute("counts", productResList);
                model.addAttribute("dist", dist.get());
                model.addAttribute("freeItem", freeItem);
                model.addAttribute("distRequisition", distRequisition.get());

            }
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/delivered-distributor-requisition-details");
        }
        return mv;
    }

    @RequestMapping("/recieved-requisition")
    public ModelAndView getrecievedDealerProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/recieved-distributor-requisition-home");
        }
        return mv;

    }

    @RequestMapping("/recieved-requisition-details/{id}")
    public ModelAndView recievedRequisitionDetails(HttpServletRequest request, Model model, @PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                model.addAttribute("org", org.get());
            }
            Optional<DistributorRequisition> distRequisition = distReqService.findById(id);
            if (distRequisition.isPresent()) {
                Set<DistributorRequisitionFreeProduct> freeItem = distRequisition.get().getFreeProductlist();
                Set<DistributorRequisitionReceive> productList = distRequisition.get().getDistributorRequiredReceive();

                Optional<Distributor> dist = distService.findById(distRequisition.get().getDealerId());
                model.addAttribute("counts", productList);
                model.addAttribute("dist", dist.get());
                model.addAttribute("freeItem", freeItem);
                model.addAttribute("distRequisition", distRequisition.get());
            }
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor-requisition/recieved-distributor-requisition-details");
        }

        return mv;

    }
}
