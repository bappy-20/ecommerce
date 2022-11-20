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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.BrandModel;
import com.inovex.main.entity.Category;
import com.inovex.main.entity.IncomingProduct;
import com.inovex.main.entity.MeasurementUnit;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.PriceUpdateHistory;
import com.inovex.main.entity.PrimaryInventory;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.entity.PurchaseProduct;
import com.inovex.main.entity.SecondaryInventory;
import com.inovex.main.entity.Supplier;
import com.inovex.main.json.response.IncomingProductResponse;
import com.inovex.main.json.response.PrimaryInventoryResponse;
import com.inovex.main.json.response.ProductInventoryResponse;
import com.inovex.main.json.response.PurchaseProductResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.PrimaryInventoryRepo;
import com.inovex.main.service.BrandModelService;
import com.inovex.main.service.CategoryService;
import com.inovex.main.service.IncomingProductService;
import com.inovex.main.service.MeasurementUnitService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.PriceUpdateService;
import com.inovex.main.service.ProductMappingService;
import com.inovex.main.service.PurchaseOrderService;
import com.inovex.main.service.SecondaryInventoryService;
import com.inovex.main.service.SupplierService;
import com.sun.el.parser.ParseException;

@RestController
@RequestMapping("/api")
public class ProductPurchaseRestController {

    @Autowired
    IncomingProductService incomingProductService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    ProductMappingService productService;
    @Autowired
    ProductMappingService productMappingService;
    @Autowired
    PriceUpdateService priceUpdateService;
    @Autowired
    BrandModelService brandService;
    @Autowired
    CategoryService catService;
    @Autowired
    SecondaryInventoryService secondaryInventoryService;
    @Autowired
    PurchaseOrderService purchaseOrderServic;
    @Autowired
    MeasurementUnitService measurementUnitService;
    @Autowired
    MenuService menuService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    PrimaryInventoryRepo primaryInventoryRepo;

    @GetMapping("/incoming-Product")
    public List<IncomingProductResponse> getAllIncomingProducts(HttpServletRequest request) {

        List<IncomingProduct> incomingProduct = new ArrayList<IncomingProduct>();
        List<IncomingProductResponse> incomingProductRes = new ArrayList<IncomingProductResponse>();

        try {

            incomingProduct = incomingProductService.findAll();
            for (IncomingProduct incomingProduct2 : incomingProduct) {
                IncomingProductResponse res = new IncomingProductResponse();
                Optional<ProductMapping> product = productService.findById(incomingProduct2.getProductId1());
                Optional<Supplier> supplier = supplierService.findById(incomingProduct2.getSupplierId());
                res.setReceivedQty(incomingProduct2.getReceivedQty());
                res.setPurchasePrice(incomingProduct2.getPurchasePrice());
                res.setId(incomingProduct2.getId());
                if (product.isPresent()) {
                    res.setProductName(product.get().getProductName());
                } else {
                    res.setProductName("not found!");
                }
                if (supplier.isPresent()) {
                    res.setSupplierName(supplier.get().getName());
                } else {
                    res.setSupplierName("not found!");
                }
                incomingProductRes.add(res);
            }
        }

        catch (Exception e) {

        }
        return incomingProductRes;
    }

    @PostMapping("/incoming-Product")
    public ResponseData createIncomingProduct(@RequestBody IncomingProduct incomingProduct,
            HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            IncomingProduct prc = incomingProductService.save(incomingProduct, request);
            responseData.setData(prc);
            responseData.setStatusCode(201);
            responseData.setMessage("IncomingProduct created successfully");

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

    @GetMapping("/incoming-Product/{id}")
    public ResponseData getIncomingProduct(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            IncomingProduct prc = incomingProductService.getIncomingProduct(id);
            responseData.setData(prc);
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

    @DeleteMapping("/incoming-Product/{id}")
    public ResponseData deleteIncomingProduct(@PathVariable Long id) {

        ResponseData responseData = new ResponseData();
        try {
            incomingProductService.deleteById(id);
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

    @PutMapping("/incoming-Product/{id}")
    public ResponseData updateIncomingProduct(@RequestBody IncomingProduct incomingProduct, @PathVariable long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            IncomingProduct prc = incomingProductService.update(incomingProduct, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(prc);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/current-inventory")
    public List<ProductInventoryResponse> getAllProductStock(HttpServletRequest request) {

        Set<ProductMapping> incomingProduct = new HashSet<ProductMapping>();
        List<ProductInventoryResponse> incomingProductRes = new ArrayList<ProductInventoryResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            try {
                Optional<Organization> org = orgRepo.findById(id);
                if (org.isPresent()) {
                    incomingProduct = org.get().getProductMapping();
                    for (ProductMapping product : incomingProduct) {
                        long onHand = 0;
                        ProductInventoryResponse res = new ProductInventoryResponse();
                        res.setProductName(product.getProductName());
                        res.setSku(product.getSku());
                        res.setStartingStock(product.getStartingStock());
                        res.setSafetyStock(product.getSafetyStock());
                        // res.setProductMrpPrice(product.getProductMrpPrice());

                        if (!product.getMesuringType().equals(null) && !product.getMesuringType().equals("")) {
                            Optional<MeasurementUnit> un = measurementUnitService
                                    .findById(Long.parseLong(product.getMesuringType()));
                            if (un.isPresent()) {
                                res.setMesuringType(un.get().getName());
                            } else {
                                res.setMesuringType("Not found!");
                            }
                        } else {
                            res.setMesuringType("Not found!");
                        }

                       // Optional<SecondaryInventory> si = secondaryInventoryService.findByProductId(product.getId());
                        Optional<PrimaryInventory> pi = primaryInventoryRepo.findByProductId(product.getId());
                        if (pi.isPresent()) {
                            onHand = pi.get().getOnHand();
                            res.setOnHand(onHand);
                            //sout
                            System.out.println(pi.get().getReceivedInventory());
                            res.setTotalRecieve(pi.get().getReceivedInventory());
                            res.setTotalShipped(pi.get().getShippedInventory());

                        } else {
                            res.setOnHand(0);
                            res.setTotalRecieve(0);
                            res.setTotalShipped(0);
                        }

                        Optional<Category> cat = catService.findById(product.getProductCategory());
                        if (cat.isPresent()) {
                            res.setProductCategoryName(cat.get().getName());
                        } else {
                            res.setProductCategoryName("Not found");
                        }

                        Optional<BrandModel> brand = brandService.findById(product.getBrandId());
                        if (brand.isPresent()) {
                            res.setBrandName(brand.get().getBrandName());
                        } else {
                            res.setBrandName("Not found");
                        }

                        Optional<Supplier> supplier = supplierService.findById(product.getSupplierId());
                        
                          Optional<PriceUpdateHistory> findProductExist = priceUpdateService.findByProductId(product.getId());
                          if (findProductExist.isPresent()) {
                         

                            Optional<Long> avgPrice = priceUpdateService.countTotalRecieve(product.getId());
                            if (avgPrice.isPresent()) {
                                res.setProductPrice(avgPrice.get());
                                res.setTotalSaleAmount(avgPrice.get() * onHand);
                                
                            } else {
                                res.setProductPrice(0);
                            }
                            
                            res.setProductMrpPrice(findProductExist.get().getMrp());

                            if (supplier.isPresent()) {
                                res.setSupplier(supplier.get().getName());
                            } else {
                                res.setSupplier("not found!");
                            }

                        if (supplier.isPresent()) {
                            res.setSupplier(supplier.get().getName());
                        } else {
                            res.setSupplier("not found!");
                        }

                         }
                        incomingProductRes.add(res);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return incomingProductRes;
    }

    @RequestMapping(value = "/submit-purchase-details", method = RequestMethod.POST)
    public String updateDeliveryDetails(@RequestParam("supplierId") long supplierId,
            @RequestParam("totalPrice") long totalPrice, @RequestParam("purchaseNumber") String purchaseNumber,
            @RequestParam("discount") long discount, @RequestParam("purchaseDate") String purchaseDate,
            @RequestParam("purchaseNote") String purchaseNote, @RequestParam("product") String producList,
            HttpServletRequest request) throws ParseException, java.text.ParseException {
        long grandTotal = totalPrice - discount;
        purchaseOrderServic.save(request, supplierId, totalPrice, purchaseNumber, discount, grandTotal, purchaseDate,
                purchaseNote, producList);
        return "success";
    }

    @GetMapping("/get-purchase-product")
    public List<PurchaseProductResponse> getAllPurchase(HttpServletRequest request) {

        List<PurchaseProduct> incomingProduct = new ArrayList<PurchaseProduct>();
        List<PurchaseProductResponse> incomingProductRes = new ArrayList<PurchaseProductResponse>();
        try {
            incomingProduct = purchaseOrderServic.findAll();
            for (PurchaseProduct incomingProduct2 : incomingProduct) {
                PurchaseProductResponse res = new PurchaseProductResponse();
                Optional<Supplier> supplier = supplierService.findById(incomingProduct2.getSupplierId());
                res.setId(incomingProduct2.getId());
                if (supplier.isPresent()) {
                    res.setSupplier(supplier.get().getName());
                } else {
                    res.setSupplier("not found!");
                }
                res.setPurchaseNumber(incomingProduct2.getPurchaseNumber());
                res.setTotalPrice(incomingProduct2.getTotalPrice());
                res.setGrandTotal(incomingProduct2.getGrandTotal());
                res.setDiscount(incomingProduct2.getDiscount());
                res.setPurchaseDate(incomingProduct2.getPurchaseDate());
                res.setPurchaseNote(incomingProduct2.getPurchaseNote());
                res.setTotalRecieveprice(incomingProduct2.getTotalRecievePrice());
                incomingProductRes.add(res);
            }
        }

        catch (Exception e) {
        }
        return incomingProductRes;
    }

    @GetMapping("/incoming-product-details/{id}")
    public List<IncomingProductResponse> getAllPurchase(@PathVariable long id) {
        System.out.println("id is " + id);
        List<IncomingProduct> incomingProduct = new ArrayList<IncomingProduct>();
        List<IncomingProductResponse> incomingProductRes = new ArrayList<IncomingProductResponse>();

        try {
            Optional<PurchaseProduct> pp = purchaseOrderServic.findById(id);
            if (pp.isPresent()) {
                incomingProduct = pp.get().getIncomingProduct();
                for (IncomingProduct incomingProduct2 : incomingProduct) {
                    IncomingProductResponse res = new IncomingProductResponse();
                    Optional<ProductMapping> product = productService.findById(incomingProduct2.getProductId1());
                    res.setReceivedQty(incomingProduct2.getReceivedQty());
                    res.setPurchasePrice(incomingProduct2.getPurchasePrice());
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

    @DeleteMapping("/delete-purchase-product/{id}")
    public ResponseData deletePurchaseProduct(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            purchaseOrderServic.deleteById(id, request);
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

    @GetMapping("/incoming-products-role")
    public List<PurchaseProductResponse> getAllIncomingPrdRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<PurchaseProductResponse> response = new ArrayList<PurchaseProductResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "productPurchase";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

                    for (PurchaseProduct incomingProduct2 : org.get().getPurchaseProducts()) {
                        PurchaseProductResponse res = new PurchaseProductResponse();
                        Optional<Supplier> supplier = supplierService.findById(incomingProduct2.getSupplierId());
                        res.setId(incomingProduct2.getId());
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        if (supplier.isPresent()) {
                            res.setSupplier(supplier.get().getName());
                        } else {
                            res.setSupplier("not found!");
                        }
                        res.setPurchaseNumber(incomingProduct2.getPurchaseNumber());
                        res.setTotalPrice(incomingProduct2.getTotalPrice());
                        res.setGrandTotal(incomingProduct2.getGrandTotal());
                        res.setDiscount(incomingProduct2.getDiscount());
                        res.setPurchaseDate(incomingProduct2.getPurchaseDate());
                        res.setPurchaseNote(incomingProduct2.getPurchaseNote());
                        res.setTotalRecieveprice(incomingProduct2.getTotalRecievePrice());
                        response.add(res);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @GetMapping("/current-primary-inventory")

    public List<PrimaryInventoryResponse> getAllPrimaryInventory() {
        List<PrimaryInventoryResponse> primaryInventoryRes = new ArrayList<PrimaryInventoryResponse>();
        List<PrimaryInventory> primaryInventoryList = new ArrayList<PrimaryInventory>();

        try {
            primaryInventoryList = primaryInventoryRepo.findAll();
            for (PrimaryInventory primaryInventory : primaryInventoryList) {
                PrimaryInventoryResponse res = new PrimaryInventoryResponse();
                res.setId(primaryInventory.getId());
                res.setProductId(primaryInventory.getProductId());
                res.setOnHand(primaryInventory.getOnHand());
                res.setStartingStock(primaryInventory.getStartingInventory());
                res.setReceivedInventory(primaryInventory.getReceivedInventory());
                res.setSafetyStock(primaryInventory.getSafetyStock());
                res.setShippedInventory(primaryInventory.getShippedInventory());
                long productId = primaryInventory.getProductId();
                System.out.println(productId);
                Optional<ProductMapping> productMapping = productMappingService.findById(productId);
                // Optional<ProductMapping> productMapping =
                // productMappingService.findByProductId(productId);
                if (productMapping.isPresent()) {
                    res.setProductName(productMapping.get().getProductName());
                } else {
                    res.setProductName("Not Found");
                }
                primaryInventoryRes.add(res);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return primaryInventoryRes;
    }

}
