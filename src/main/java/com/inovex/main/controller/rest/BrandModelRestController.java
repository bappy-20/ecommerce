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
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.json.response.BrandResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.BrandModelService;
import com.inovex.main.service.MenuService;

@RestController
@RequestMapping("/api")
public class BrandModelRestController {
    @Autowired
    BrandModelService brandModelService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/brand-list1")
    public Set<BrandModel> getAllCategorys(HttpServletRequest request) {

        Set<BrandModel> brandList = new HashSet<BrandModel>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                brandList = org.get().getBrandModels();
            } catch (Exception e) {

            }
        }
        return brandList;
    }

    @PostMapping("/save-brand")
    public ResponseData createBrand(@RequestBody BrandModel brand, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            BrandModel brd = brandModelService.save(brand, request);
            responseData.setData(brd);
            responseData.setStatusCode(201);
            responseData.setMessage("brand created successfully");

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

    @GetMapping("/get-brand/{id}")
    public ResponseData getbrand(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<BrandModel> brd = brandModelService.findById(id);
            responseData.setData(brd.get());
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

    @DeleteMapping("/delete-brand/{id}")
    public ResponseData deleteBrand(@PathVariable Long id, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            brandModelService.deleteById(id, request);
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

    @PutMapping("/update-brand/{id}")
    public ResponseData updateEmployee(@RequestBody BrandModel brand, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            BrandModel brd = brandModelService.update(brand, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(brd);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/brand-list-all")
    public List<BrandResponse> getAlbrand(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<BrandResponse> response = new ArrayList<BrandResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "brand";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (BrandModel mn : org.get().getBrandModels()) {
                        BrandResponse res = new BrandResponse();
                        res.setId(mn.getId());
                        res.setBrandName(mn.getBrandName());
                        res.setBrandOrigin(mn.getBrandOrigin());
                        res.setLogo(mn.getLogo());
                        res.setCompanyId(mn.getCompanyId());
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