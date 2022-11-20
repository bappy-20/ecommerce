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
import com.inovex.main.entity.RetailComplain;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.RetailComplainResponse;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.RetailComplainService;

@RestController
@RequestMapping("/api")
public class RetailComplainRestController {

    @Autowired
    RetailComplainService retailComplainService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/retail-Complain")
    public Set<RetailComplain> getAllRetailComplains(HttpServletRequest request) {
        Set<RetailComplain> retailComplainList = new HashSet<RetailComplain>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                retailComplainList = org.get().getRetailComplains();
            } catch (Exception e) {
            }
        }
        return retailComplainList;
    }

    @PostMapping("/save-retailComplain")
    public ResponseData createRetailComplain(@RequestBody RetailComplain retailComplain, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            RetailComplain ext = retailComplainService.save(retailComplain, request);
            responseData.setData(ext);
            responseData.setStatusCode(201);
            responseData.setMessage("retailComplain created successfully");

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

    @GetMapping("/get-retailComplain/{id}")
    public ResponseData getretailComplain(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<RetailComplain> ext = retailComplainService.findById(id);
            responseData.setData(ext.get());
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

    @DeleteMapping("/delete-retailComplain/{id}")
    public ResponseData deleteRetailComplain(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            retailComplainService.deleteById(id, request);
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

    @PutMapping("/update-retailComplain/{id}")
    public ResponseData updateRetailComplain(@RequestBody RetailComplain retailComplain, @PathVariable Long id) {

        ResponseData responseData = new ResponseData();
        try {
            RetailComplain brd = retailComplainService.update(retailComplain, id);
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

    @GetMapping("/retail-complain-list-role")
    public List<RetailComplainResponse> getAllRetailComplainRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<RetailComplainResponse> response = new ArrayList<RetailComplainResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "retailComplain";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (RetailComplain mn : org.get().getRetailComplains()) {
                        RetailComplainResponse res = new RetailComplainResponse();
                        res.setId(mn.getId());
                        res.setTitle(mn.getTitle());
                        res.setRetailId(mn.getRetailId());
                        res.setNote(mn.getNote());
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