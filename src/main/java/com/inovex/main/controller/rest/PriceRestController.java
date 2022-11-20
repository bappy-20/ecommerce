package com.inovex.main.controller.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.Price;
import com.inovex.main.entity.PriceUpdateHistory;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.json.response.PriceResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.PriceService;
import com.inovex.main.service.PriceUpdateService;
import com.inovex.main.service.ProductMappingService;

@RestController
@RequestMapping("/api")
public class PriceRestController {
    @Autowired
    OrganizationService orgService;
    @Autowired
    PriceService priceService;
    @Autowired
    ProductMappingService proService;
    @Autowired
    PriceUpdateService priceUpdateService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/price")
    public List<PriceResponse> getAllPrices(HttpServletRequest request) {

        Set<Price> price = new HashSet<Price>();
        List<PriceResponse> priceRes = new ArrayList<PriceResponse>();

        try {
            if (request.getSession().getAttribute("orgId") != null) {
                long id = (long) request.getSession().getAttribute("orgId");
                Optional<Organization> org = orgService.findById(id);
                if (org.isPresent()) {
                    price = org.get().getPrice();
                }
            }
            if (price.size() > 0) {
                for (Price p : price) {
                    PriceResponse res = new PriceResponse();
                    res.setProductId(p.getProductId());
                    res.setId(p.getId());
                    res.setProductPrice(p.getProductPrice());
                    Optional<ProductMapping> pro = proService.findById(p.getProductId());
                    if (pro.isPresent()) {
                        res.setProductName(pro.get().getProductName());
                    } else {
                        res.setProductName("Not Found");
                    }
                    res.setRetailPrice(p.getRetailPrice());
                    res.setMrp(p.getMrp());
                    res.setDealerPrice(p.getDealerPrice());
                    priceRes.add(res);
                }
            }
        }

        catch (Exception e) {

        }
        return priceRes;
    }

    @PostMapping("/price")
    public ResponseData createPrice(@RequestBody Price price, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            Price prc = priceService.save(price, request);
            responseData.setData(prc);
            responseData.setStatusCode(201);
            responseData.setMessage("Price created successfully");

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

//    @GetMapping("/price/{id}")
//    public ResponseData getPrice(@PathVariable Long id) {
//        ResponseData responseData = new ResponseData();
//        try {
//            Price prc = priceService.getPrice(id);
//            responseData.setData(prc);
//            responseData.setStatusCode(200);
//            responseData.setMessage("ok");
//            return responseData;
//        } catch (Exception e) {
//            responseData.setData(null);
//
//            responseData.setMessage(e.getMessage());
//            responseData.setStatusCode(500);
//            responseData.setMessage(e.getMessage());
//
//            return responseData;
//        }
//    }
    
    
    @GetMapping("/price/{id}")
    public ResponseData getPrice(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            PriceResponse prc = priceService.getPrice1(id);
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

    @DeleteMapping("/delete-price/{id}")
    public ResponseData deletePrice(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            priceService.deleteById(id, request);
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

    @PutMapping("/price/{id}")
    public ResponseData updatePrice(@RequestBody Price price, @PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            Price prc = priceService.update(price, id, request);
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

    @GetMapping("/price-update-history/{productId}/{date1}/{date2}")
    public List<PriceResponse> getPriceUpdatehistory(@PathVariable long productId, @PathVariable String date1,
            @PathVariable String date2) {
        List<PriceUpdateHistory> priceList = new ArrayList<>();
        List<PriceResponse> priceRes = new ArrayList<PriceResponse>();
        try {
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = df2.parse(date1);
            Date enDate = df2.parse(date2);
            priceList = priceUpdateService.findAllByProduct(productId, stDate, enDate);
            for (PriceUpdateHistory p : priceList) {
                PriceResponse res = new PriceResponse();
                res.setProductId(p.getProductId());
                res.setId(p.getId());
                res.setProductPrice(p.getProductPrice());
                Optional<ProductMapping> pro = proService.findById(p.getProductId());
                if (pro.isPresent()) {
                    res.setProductName(pro.get().getProductName());
                } else {
                    res.setProductName("Not Found");
                }
                // res.setRetailPrice(p.getRetailPrice());
                res.setMrp(p.getMrp());
                res.setDealerPrice(p.getDealerPrice());
                priceRes.add(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return priceRes;
    }

    @GetMapping("/get-dealer-price/{productId}")
    public ResponseData getDealerPrice(@PathVariable Long productId) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<Price> prc = priceService.findByProductId(productId);
            if (prc.isPresent()) {
                responseData.setData(prc.get());
            } else {
                responseData.setData(0);
            }
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

    @GetMapping("get-product-price/{productId}")
    public ResponseData GetProductPrice(@PathVariable long productId) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<Price> prc = priceService.findByProductId(productId);
            if (prc.isPresent()) {
                responseData.setData(prc.get());
            } else {
                responseData.setData("not Inserted");
            }
            responseData.setStatusCode(200);
            responseData.setMessage("ok");
            return responseData;

        } catch (Exception e) {
            // TODO: handle exception
            responseData.setData(null);

            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    @GetMapping("/product-price-role")
    public List<PriceResponse> getProductRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<PriceResponse> response = new ArrayList<PriceResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "productPrice";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

                    for (Price p : org.get().getPrice()) {
                        PriceResponse res = new PriceResponse();
                        res.setProductId(p.getProductId());
                        res.setId(p.getId());
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        res.setProductPrice(p.getProductPrice());
                        Optional<ProductMapping> pro = proService.findById(p.getProductId());
                        if (pro.isPresent()) {
                            res.setProductName(pro.get().getProductName());
                        } else {
                            res.setProductName("Not Found");
                        }
                        res.setRetailPrice(p.getRetailPrice());
                        res.setMrp(p.getMrp());
                        res.setDealerPrice(p.getDealerPrice());
                        response.add(res);
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        return response;
    }
    
    @GetMapping("get-product-price2/{productId}")
    public ResponseData GetProductPrice2(@PathVariable long productId) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<Price> prc = priceService.findByProductId(productId);
            if (prc.isPresent()) {
                responseData.setData(prc.get());
            } else {
                responseData.setData("not Inserted");
            }
            responseData.setStatusCode(200);
            responseData.setMessage("ok");
            return responseData;

        } catch (Exception e) {
            // TODO: handle exception
            responseData.setData(null);

            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    
    
}
