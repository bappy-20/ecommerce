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
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.BrandModel;
import com.inovex.main.entity.Category;
import com.inovex.main.entity.MeasurementUnit;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.entity.Supplier;
import com.inovex.main.json.response.ProductResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.BrandModelService;
import com.inovex.main.service.CategoryService;
import com.inovex.main.service.MeasurementUnitService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.ProductCategoryService;
import com.inovex.main.service.ProductMappingService;
import com.inovex.main.service.SecondaryInventoryService;
import com.inovex.main.service.SupplierService;

@RestController
@RequestMapping("/api")
public class ProductMappingRestController {
    @Autowired
    BrandModelService brandService;
    @Autowired
    ProductMappingService productService;
    @Autowired
    ProductCategoryService productCategory;
    @Autowired
    SupplierService supplierService;
    @Autowired
    MeasurementUnitService measurementUnitService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    SecondaryInventoryService secondaryInventoryService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/product-mapping")
    public List<ProductResponse> getAllProducts(HttpServletRequest request) {
        List<ProductResponse> productRes = new ArrayList<ProductResponse>();

        Set<ProductMapping> productList = new HashSet<ProductMapping>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                productList = org.get().getProductMapping();
                for (ProductMapping product2 : productList) {
                    ProductResponse res = new ProductResponse();
                    res.setId(product2.getId());
                    res.setProductName(product2.getProductName());
                    res.setSku(product2.getSku());

                    Optional<Category> cat = productCategory.findById(product2.getProductCategory());
                    if (cat.isPresent()) {
                        res.setProductCategoryName(cat.get().getName());
                    } else {
                        res.setProductCategoryName("not found!");
                    }

                    Optional<MeasurementUnit> mesure = measurementUnitService
                            .findById(Long.parseLong(product2.getMesuringType()));
                    if (mesure.isPresent()) {
                        res.setMesuringType(mesure.get().getName());
                    } else {
                        res.setMesuringType("not found!");
                    }
                    res.setStartingStock(product2.getStartingStock());
                    res.setSafetyStock(product2.getSafetyStock());
                    Optional<Supplier> supp = supplierService.findById(product2.getSupplierId());
                    if (supp.isPresent()) {
                        res.setSupplier(supp.get().getName());
                    } else {
                        res.setSupplier("not found!");
                    }
                    Optional<BrandModel> brand = brandService.findById(product2.getBrandId());
                    if (brand.isPresent()) {
                        res.setBrandName(brand.get().getBrandName());
                    } else {
                        res.setBrandName("not found!");
                    }

                    productRes.add(res);
                }
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return productRes;
    }

    @PostMapping("/product-mapping")
    public ResponseData createProduct(@RequestBody ProductMapping product, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            ProductMapping prc = productService.save(product, request);
            responseData.setData(prc);
            responseData.setStatusCode(201);
            responseData.setMessage("Product Mapped successfully");

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

    @GetMapping("/product-mapping/{id}")
    public ResponseData getProduct(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            ProductMapping prc = productService.getProduct(id);
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

    @DeleteMapping("/delete-product-mapping/{id}")
    public ResponseData deleteProduct(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            productService.deleteById(id, request);
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

    @PutMapping("/product-mapping/{id}")
    public ResponseData updateProduct(@RequestBody ProductMapping product, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            ProductMapping prc = productService.update(product, id, request);
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

    @GetMapping("/product-mapping-list-role")
    public List<ProductResponse> getAlMappingProducts(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<ProductResponse> response = new ArrayList<ProductResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "mappingProduct";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

                    for (ProductMapping product2 : org.get().getProductMapping()) {
                        ProductResponse res = new ProductResponse();
                        res.setId(product2.getId());
                        res.setProductId(product2.getId());
                        //res.setProduct
                        res.setProductName(product2.getProductName());
                        res.setSku(product2.getSku());
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        Optional<Category> cat = productCategory.findById(product2.getProductCategory());
                        if (cat.isPresent()) {
                            res.setProductCategoryName(cat.get().getName());
                        } else {
                            res.setProductCategoryName("not found!");
                        }

                        Optional<MeasurementUnit> mesure = measurementUnitService
                                .findById(Long.parseLong(product2.getMesuringType()));
                        if (mesure.isPresent()) {
                            res.setMesuringType(mesure.get().getName());
                        } else {
                            res.setMesuringType("not found!");
                        }
                        res.setStartingStock(product2.getStartingStock());
                        res.setSafetyStock(product2.getSafetyStock());
                        Optional<Supplier> supp = supplierService.findById(product2.getSupplierId());
                        if (supp.isPresent()) {
                            res.setSupplier(supp.get().getName());
                        } else {
                            res.setSupplier("not found!");
                        }
                        long brandId = product2.getBrandId();
                        
                        Optional<BrandModel> brand = brandService.findById(product2.getBrandId());
                        if (brand.isPresent()) {
                            res.setBrandName(brand.get().getBrandName());
                        } else {
                            res.setBrandName("not found!");
                        }

                        response.add(res);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }
    
    @GetMapping("/product-mapping-for-ProductId/{id}")
    public ResponseData getProductId(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Long prc = productService.getProductId(id);
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

}
