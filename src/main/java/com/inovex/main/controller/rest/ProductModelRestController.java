package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
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

import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductModel;
import com.inovex.main.json.response.ProductResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.ProductModelService;
import com.inovex.main.service.SecondaryInventoryService;

@RestController
@RequestMapping("/api")
public class ProductModelRestController {
    @Autowired
    ProductModelService productService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;
	@Autowired
	SecondaryInventoryService secondaryInventoryService;


    @GetMapping("/product-model")
    public List<ProductResponse> getAllProducts(HttpServletRequest request) {
        List<ProductResponse> productRes = new ArrayList<ProductResponse>();

        Set<ProductModel> productList = new HashSet<ProductModel>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                productList = org.get().getProductModel();
                for (ProductModel product2 : productList) {
                    ProductResponse res = new ProductResponse();
                    res.setId(product2.getId());
                    res.setProductName(product2.getProductName());
                    res.setProductLabel(product2.getProductLabel());
                    res.setShortDiscription(product2.getShortDiscription());
                    res.setProductImage(product2.getProductImage());

                    productRes.add(res);
                }
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return productRes;
    }

    @PostMapping("/product-model")
    public ResponseData createProduct(@RequestBody ProductModel product, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            ProductModel prc = productService.save(product, request);
            responseData.setData(prc);
            responseData.setStatusCode(201);
            responseData.setMessage("Product created successfully");

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

    @GetMapping("/product-model/{id}")
    public ResponseData getProduct(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            ProductModel prc = productService.getProduct(id);
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

    @DeleteMapping("/delete-product-model/{id}")
    public ResponseData deleteProduct(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            productService.deleteById(id, request);
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

    @PutMapping("/product-model/{id}")
    public ResponseData updateProduct(@RequestBody ProductModel product, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            ProductModel prc = productService.update(product, id, request);
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

    @RequestMapping(value = "/product-server-side", method = RequestMethod.POST)
    public String getDailySalary(@RequestParam("start") int start, @RequestParam("length") int length,
            @RequestParam("search[value]") String searchParam, HttpServletRequest request) {
        JSONObject responseObj = new JSONObject();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");

            ArrayList<Object> product = productService.getPagination(start, length, searchParam, request);
            JSONArray main = new JSONArray();
            JSONArray obj1 = new JSONArray();

            Iterator itr = product.iterator();
            while (itr.hasNext()) {
                obj1 = new JSONArray();
                Object[] obj = (Object[]) itr.next();
                obj1.put(String.valueOf(obj[0]));
                obj1.put(String.valueOf(obj[1]));
                obj1.put(String.valueOf(obj[2]));
                obj1.put(0);
                obj1.put(0);
                main.put(obj1);
            }

            Long totalLength = productService.getCountWithSearchParm(searchParam, orgId);
            responseObj.put("data", main);
            responseObj.put("recordsTotal", totalLength);
            responseObj.put("recordsFiltered", totalLength);
        }

        return responseObj.toString();
    }

    @GetMapping("/product-list-all")
    public List<ProductResponse> getAlProducts(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<ProductResponse> response = new ArrayList<ProductResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "product";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (ProductModel mn : org.get().getProductModel()) {
                        ProductResponse res = new ProductResponse();
                        res.setId(mn.getId());
                        res.setProductName(mn.getProductName());
                        res.setProductLabel(mn.getProductLabel());
                        res.setShortDiscription(mn.getShortDiscription());
                        res.setProductImage(mn.getProductImage());
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
