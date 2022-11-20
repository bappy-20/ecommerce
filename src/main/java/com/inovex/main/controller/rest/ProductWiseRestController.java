package com.inovex.main.controller.rest;

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

import com.inovex.main.entity.Campaign;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.entity.ProductWiseCampaign;
import com.inovex.main.json.response.ProductWiseCampaignResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CampaignService;
import com.inovex.main.service.ProductMappingService;
import com.inovex.main.service.ProductWiseCampaignService;

@RestController
@RequestMapping("/api")
public class ProductWiseRestController {

    @Autowired
    CampaignService campaignService;
    @Autowired

    ProductMappingService productService;
    @Autowired
    ProductWiseCampaignService prdWiseCampService;
    @Autowired
    OrganizationRepository orgRepo;

    @GetMapping("/product-wise-campaign-list")
    public Set<ProductWiseCampaign> getAllProductWiseCamapaignList(HttpServletRequest request) {
        Set<ProductWiseCampaign> prdwisecampList = new HashSet<ProductWiseCampaign>();
        try {
            if (request.getSession().getAttribute("orgId") != null) {
                long orgId = (long) request.getSession().getAttribute("orgId");
                Optional<Organization> org = orgRepo.findById(orgId);
                if (org.isPresent()) {
                    prdwisecampList = org.get().getProductWiseCampaigns();
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return prdwisecampList;
    }

    /*
     * @PostMapping("/save-product-wise-campaign") private ResponseData
     * createCampaign(@RequestBody ProductWiseCampaign prdWiseCamp,
     * HttpServletRequest request) { ResponseData responseData = new ResponseData();
     * try { ProductWiseCampaign prdcamp = prdWiseCampService.save(prdWiseCamp,
     * request);
     * 
     * 
     * if (request.getSession().getAttribute("orgId") != null) { long orgId = (long)
     * request.getSession().getAttribute("orgId"); Optional<Organization> org =
     * orgRepo.findById(orgId); if (org.isPresent()) { prdwisecampList =
     * org.get().getProductWiseCampaigns(); } } return prdwisecampList; }
     */

    @GetMapping("/product-wise-campaign-list1")
    public Set<ProductWiseCampaignResponse> getAllProductWiseCamapaignList1(HttpServletRequest request) {

        Set<ProductWiseCampaignResponse> productRes = new HashSet<ProductWiseCampaignResponse>();

        Set<ProductWiseCampaign> prdwisecampList = new HashSet<ProductWiseCampaign>();

        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            if (org.isPresent()) {
                try {
                    prdwisecampList = org.get().getProductWiseCampaigns();
                    for (ProductWiseCampaign productWiseCampaign2 : prdwisecampList) {
                        ProductWiseCampaignResponse res = new ProductWiseCampaignResponse();
                        res.setId(productWiseCampaign2.getId());
                        res.setCampaignId(productWiseCampaign2.getCampaignId());
                        res.setDiscountAmount(productWiseCampaign2.getDiscountAmount());
                        res.setDiscountOn(productWiseCampaign2.getDiscountOn());
                        res.setDiscountType(productWiseCampaign2.getDiscountType());
                        res.setFreeItemQuantity(productWiseCampaign2.getFreeItemQuantity());
                        res.setOfferType(productWiseCampaign2.getOfferType());
                        res.setProductId(productWiseCampaign2.getProductId());
                        res.setRequiredQuantity(productWiseCampaign2.getRequiredQuantity());
                        Optional<ProductMapping> prd = productService.findById(productWiseCampaign2.getProductId());
                        if (prd.isPresent()) {
                            res.setProductName(prd.get().getProductName());
                        } else {
                            res.setProductName("Product Not Found!");
                        }
                        Optional<ProductMapping> prdfree = productService
                                .findById(productWiseCampaign2.getFreeProductId());
                        if (prdfree.isPresent()) {
                            res.setFreeProduct(prdfree.get().getProductName());
                        } else {
                            res.setFreeProduct("Free Product Not Found!");
                        }

                        Optional<Campaign> caaamp = campaignService.findById(productWiseCampaign2.getCampaignId());
                        if (caaamp.isPresent()) {
                            res.setCampaignName(caaamp.get().getCampaignName());
                        } else {
                            res.setCampaignName("Campaign Not Found!");
                        }
                        productRes.add(res);

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

        }
        return productRes;
    }

    @PostMapping("/save-product-wise-campaign")
    private ResponseData createProductWiseCampaign(@RequestBody ProductWiseCampaign prdWiseCamp,
            HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            ProductWiseCampaign prdcamp = prdWiseCampService.save(prdWiseCamp, request);

            responseData.setData(prdcamp);
            responseData.setStatusCode(200);
            responseData.setMessage("Campaign is created");
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

    @GetMapping("/get-product-wise-campaign/{id}")
    public ResponseData getProductWiseCampaign(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<ProductWiseCampaign> prdcamp = prdWiseCampService.findById(id);
            responseData.setData(prdcamp);
            responseData.setStatusCode(200);
            responseData.setMessage("Ok");
            return responseData;

        } catch (Exception e) {
            // TODO: handle exception
            responseData.setData(null);
            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            return responseData;
        }
    }

    @DeleteMapping("/delete-product-wise-campaign/{id}")

    public ResponseData deleteProductWiseCampaign(@PathVariable Long id, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            prdWiseCampService.deleteById(id, request);
            responseData.setStatusCode(204);
            responseData.setMessage("Delete Succesfully");
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @PutMapping("/update-product-wise-campaign/{id}")

    public ResponseData updateProductWiseCampaign(@RequestBody ProductWiseCampaign productWiseCampaign,
            @PathVariable Long id, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            ProductWiseCampaign camp = prdWiseCampService.update(productWiseCampaign, id, request);
            responseData.setData(camp);
            responseData.setStatusCode(200);
            responseData.setMessage("Update Fuel Succesfully");
            return responseData;
        } catch (Exception e) {
            // TODO: handle exception
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }

    }

    @GetMapping("/get-campaign-by-product/{productId}")
    public ResponseData getProductDiscount(@PathVariable Long productId) {
        ResponseData responseData = new ResponseData();
        try {

            List<ProductWiseCampaign> prdcamp = prdWiseCampService.findAllByProductId(productId);
            if (prdcamp.size() > 0) {
                responseData.setData(prdcamp);
                responseData.setStatusCode(200);
                responseData.setMessage("Data found");
            } else {
                responseData.setData(null);
                responseData.setStatusCode(204);
                responseData.setMessage("Data not found");
            }

            return responseData;

        } catch (Exception e) {
            // TODO: handle exception
            responseData.setData(null);
            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            return responseData;
        }
    }

    @GetMapping("/get-active-product-wise-campaign")
    public Set<ProductWiseCampaignResponse> getAvailableCampaign(HttpServletRequest request) {
        Set<ProductWiseCampaignResponse> cashBackcampaignList = new HashSet<>();
        try {
            cashBackcampaignList = prdWiseCampService.getAvailableCashBack(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cashBackcampaignList;

    }

    @PostMapping("/save-product-wise-campaign-list")
    private ResponseData createProductWiseCampaignList(@RequestBody List<ProductWiseCampaign> prdWiseCampList,
            HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            List<ProductWiseCampaign> prdcamp = prdWiseCampService.saveListOfCampaign(prdWiseCampList, request);
            responseData.setData(prdcamp);
            responseData.setStatusCode(200);
            responseData.setMessage("PrdWiseCampList is created");
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
}