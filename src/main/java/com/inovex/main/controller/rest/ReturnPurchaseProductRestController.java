package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.IncomingProduct;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.entity.PurchaseProduct;
import com.inovex.main.entity.Retail;
import com.inovex.main.entity.ReturnProduct;
import com.inovex.main.entity.ReturnPurchaseProduct;
import com.inovex.main.entity.Supplier;
import com.inovex.main.json.response.IncomingProductResponse;
import com.inovex.main.json.response.PurchaseProductResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.ProductMappingService;
import com.inovex.main.service.PurchaseOrderService;
import com.inovex.main.service.ReturnPurchaseProductService;
import com.inovex.main.service.SupplierService;
import com.sun.el.parser.ParseException;

@RestController
@RequestMapping("/api")
public class ReturnPurchaseProductRestController {

    @Autowired
    ReturnPurchaseProductService returnPurchaseProductService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    ProductMappingService productService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    PurchaseOrderService purchaseOrderService;

    @RequestMapping(value = "/submit-purchase-return", method = RequestMethod.POST)
    public String submitPurchaseReturn(@RequestParam("supplierId") long supplierId,
            @RequestParam("totalPrice") long totalPrice, @RequestParam("purchaseNumber") String purchaseNumber,
            @RequestParam("purchaseDate") String purchaseDate, @RequestParam("purchaseNote") String purchaseNote,
            @RequestParam("deductionPrice") String deductionPrice, @RequestParam("product") JSONArray producList,
            HttpServletRequest request) throws ParseException, java.text.ParseException {

        returnPurchaseProductService.save(supplierId, totalPrice, purchaseNumber, purchaseDate, purchaseNote,
                producList, request);

        return "success";
    }

    @GetMapping("/get-return-purchase-product")
    public List<PurchaseProductResponse> getAllPurchase(HttpServletRequest request) {

        Set<ReturnPurchaseProduct> returnPurchesProductList = new HashSet<ReturnPurchaseProduct>();
        List<PurchaseProductResponse> incomingProductRes = new ArrayList<PurchaseProductResponse>();
        Set<Retail> retail = new HashSet<Retail>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            if (org.isPresent()) {
                try {
                    returnPurchesProductList = org.get().getReturnPurchaseProducts();
                    for (ReturnPurchaseProduct incomingProduct2 : returnPurchesProductList) {
                        PurchaseProductResponse res = new PurchaseProductResponse();
                        Optional<Supplier> supplier = supplierService.findById(incomingProduct2.getSupplierId());
                        res.setId(incomingProduct2.getId());
                        if (supplier.isPresent()) {
                            res.setSupplier(supplier.get().getName());
                        } else {
                            res.setSupplier("not found!");
                        }
                        res.setPurchaseNumber(incomingProduct2.getPurchaseNumber());
                        res.setTotalPrice(incomingProduct2.getReturnPrice());

                        res.setPurchaseDate(incomingProduct2.getReturnDate());
                        res.setPurchaseNote(incomingProduct2.getPurchaseNote());
                        incomingProductRes.add(res);
                    }
                }

                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        return incomingProductRes;
    }

    @GetMapping("/return-product-details/{id}")
    public List<IncomingProductResponse> getAllReturnPurchase(@PathVariable long id) {
        System.out.println("id is " + id);
        List<ReturnProduct> incomingProduct = new ArrayList<ReturnProduct>();
        List<IncomingProductResponse> incomingProductRes = new ArrayList<IncomingProductResponse>();
        try {
            Optional<ReturnPurchaseProduct> pp = returnPurchaseProductService.findById(id);
            if (pp.isPresent()) {
                incomingProduct = pp.get().getReturnProduct();
                for (ReturnProduct incomingProduct2 : incomingProduct) {
                    IncomingProductResponse res = new IncomingProductResponse();
                    Optional<ProductMapping> product = productService
                            .findById(Long.parseLong(incomingProduct2.getProductId1()));
                    res.setReceivedQty(incomingProduct2.getReturnQuantity());
                    //res.setR
                    res.setPurchasePrice(Long.parseLong(incomingProduct2.getPurchasePrice()));
                    res.setUnitPrice(Long.parseLong(incomingProduct2.getProductIdUnitprice()));
                    res.setId(incomingProduct2.getId());                    
                    res.setProductName(incomingProduct2.getProductName());
//                    if (product.isPresent()) {
//                        res.setProductName(product.get().getProductName());
//                    } else {
//                        res.setProductName("not found!");
//                    }

                    incomingProductRes.add(res);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return incomingProductRes;
    }

    @GetMapping("/return-product-list/{purchaseNumber}")
    public ResponseData getAllReturnProductList(@PathVariable String purchaseNumber) {
        ResponseData responseData = new ResponseData();
        List<IncomingProduct> incomingProduct = new ArrayList<IncomingProduct>();
        List<IncomingProductResponse> incomingProductRes = new ArrayList<IncomingProductResponse>();
        try {
            Optional<PurchaseProduct> pp = purchaseOrderService.findByPurchaseNumber(purchaseNumber);
            if (pp.isPresent()) {
                incomingProduct = pp.get().getIncomingProduct();
                for (IncomingProduct incomingProduct2 : incomingProduct) {
                    IncomingProductResponse res = new IncomingProductResponse();
                    Optional<ProductMapping> product = productService.findById(incomingProduct2.getProductId1());
                    res.setReceivedQty(incomingProduct2.getReceivedQty());
                    res.setPurchasePrice(incomingProduct2.getPurchasePrice());
                    res.setUnitPrice(incomingProduct2.getProductIdUnitprice());
                    res.setId(incomingProduct2.getId());
                    res.setProductId(incomingProduct2.getProductId1());
                    if (product.isPresent()) {
                        res.setProductName(product.get().getProductName());
                    } else {
                        res.setProductName("not found!");
                    }

                    incomingProductRes.add(res);
                }
                responseData.setData(incomingProductRes);
                responseData.setStatusCode(pp.get().getSupplierId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseData;
    }
    
    @DeleteMapping("/delete-return-purchase-product/{id}")
    public ResponseData deleteReturnPurchaseProduct(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            returnPurchaseProductService.deleteById(id, request);
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
