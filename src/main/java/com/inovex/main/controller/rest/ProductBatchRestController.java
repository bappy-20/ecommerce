package com.inovex.main.controller.rest;

import java.text.SimpleDateFormat;
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

import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductBatch;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.entity.ProductModel;
import com.inovex.main.json.response.ProductBatchResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.ProductBatchService;
import com.inovex.main.service.ProductMappingService;
import com.inovex.main.service.ProductModelService;

@RestController
@RequestMapping("/api")
public class ProductBatchRestController {

    @Autowired
    ProductBatchService productBatchService;
    @Autowired
    ProductModelService proService;
    @Autowired
    MenuService menuService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    ProductModelService prdModelService;
    @Autowired
    ProductMappingService productMappingService;

    @GetMapping("/product-batch")
    public List<ProductBatchResponse> getAllPrices(HttpServletRequest request) {
        List<ProductBatch> productBatch = new ArrayList<ProductBatch>();
        List<ProductBatchResponse> productBatchRes = new ArrayList<ProductBatchResponse>();
        try {
            SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy");
            productBatch = productBatchService.findAll();
            if (productBatch.size() > 0) {
                for (ProductBatch p : productBatch) {
                    ProductBatchResponse res = new ProductBatchResponse();
                    res.setProductId(p.getProductId());
                    res.setId(p.getId());
                    res.setBatchNumber(p.getBatchNumber());
                    res.setManufatureDate(p.getManufatureDate());
                    res.setExpireDate(p.getExpireDate());
                    Optional<ProductModel> pro = proService.findById(p.getProductId());
                    if (pro.isPresent()) {
                        res.setProductName(pro.get().getProductName());
                    } else {
                        res.setProductName("Not Found");
                    }

                    productBatchRes.add(res);
                }
            }
        }

        catch (Exception e) {

        }
        return productBatchRes;
    }

    @PostMapping("/product-batch")
    public ResponseData createPrice(@RequestBody ProductBatch price, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            ProductBatch prc = productBatchService.save(price, request);
            responseData.setData(prc);
            responseData.setStatusCode(201);
            responseData.setMessage("Product Batch Created Successfully");

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

    @GetMapping("/product-batch/{id}")
    public ResponseData getPrice(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<ProductBatch> prc = productBatchService.findById(id);
            responseData.setData(prc.get());
            responseData.setStatusCode(200);
            responseData.setMessage("ok");
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);

            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    @DeleteMapping("/delete-product-batch/{id}")
    public ResponseData deletePrice(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            productBatchService.deleteById(id, request);
            responseData.setStatusCode(204);
            responseData.setMessage("delete successfully");
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    @PutMapping("/product-batch/{id}")
    public ResponseData updatePrice(@RequestBody ProductBatch price, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            ProductBatch prc = productBatchService.update(price, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(prc);
            return responseData;
        } catch (Exception e) {
        	e.printStackTrace();
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/product-batch-list")
    public List<ProductBatchResponse> getAllAreas(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<ProductBatchResponse> response = new ArrayList<ProductBatchResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "productBatch";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (ProductBatch mn : org.get().getProductBatch()) {
                        ProductBatchResponse res = new ProductBatchResponse();
                        res.setId(mn.getId());
                        Optional<ProductMapping> product = productMappingService.findById(mn.getProductId());
                        if (product.isPresent()) {
                            res.setProductName(product.get().getProductName());
                        }
                        res.setProductId(mn.getProductId());
                        res.setBatchNumber(mn.getBatchNumber());
                        res.setExpireDate(mn.getExpireDate());
                        res.setManufatureDate(mn.getManufatureDate());

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

//    @GetMapping("/product-batch-by-ProductId/{id}")
//    public List<ProductBatch> getProductBatchByProductId(@PathVariable Long productId){
//    	List<ProductBatch> prdBatch = new ArrayList<ProductBatch>();
//    	try {
//    		prdBatch = productBatchService.findAllPrdBatchByProductdId(productId);
//    		 return prdBatch;
//		} catch (Exception e) {
//			// TODO: handle exception
//			  e.printStackTrace();
//		}
//    	return prdBatch;
//    	
//    }

    @GetMapping("/product-batch-by-ProductId/{productId}")
    public ResponseData getPrdBatches(@PathVariable Long productId) {
        ResponseData responseData = new ResponseData();
        try {
            List<ProductBatch> prdBatch = productBatchService.findAllPrdBatchByProductdId(productId);
            responseData.setData(prdBatch);
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

}
