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

import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.RetailType;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.RetailTypeResponse;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.RetailTypeService;

@RestController
@RequestMapping("/api")
public class RetailTypeRestController {

    @Autowired
    RetailTypeService retailTypeService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/retail-Type")
    public Set<RetailType> getAllRetailTypes(HttpServletRequest request) {

        Set<RetailType> retailTypeList = new HashSet<RetailType>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                retailTypeList = org.get().getRetailType();
            } catch (Exception e) {
            }
        }
        return retailTypeList;
    }

    @PostMapping("/retail-Type")
    public ResponseData createRetailType(@RequestBody RetailType retailType, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            RetailType rtl = retailTypeService.save(retailType, request);
            responseData.setData(rtl);
            responseData.setStatusCode(201);
            responseData.setMessage("RetailType created successfully");

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

    @GetMapping("/retail-Type/{id}")
    public ResponseData getRetailType(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            RetailType rtl = retailTypeService.getRetailType(id);
            responseData.setData(rtl);
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

    @DeleteMapping("/delete-retail-type/{id}")
    public ResponseData deleteRetailType(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            retailTypeService.deleteById(id, request);
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

    @PutMapping("/retail-Type/{id}")
    public ResponseData updateRetailType(@RequestBody RetailType retailType, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            RetailType rtl = retailTypeService.update(retailType, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(rtl);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/retail-type-role")
    public List<RetailTypeResponse> getAllAreas(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<RetailTypeResponse> response = new ArrayList<RetailTypeResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "retailType";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (RetailType mn : org.get().getRetailType()) {
                        RetailTypeResponse res = new RetailTypeResponse();
                        res.setId(mn.getId());
                        res.setRetailType(mn.getRetailType());
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
