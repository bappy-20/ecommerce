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
import com.inovex.main.entity.Supplier;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.SupplierResponse;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.SupplierService;

@RestController
@RequestMapping("/api")
public class SupplierRestController {

    @Autowired
    SupplierService supplierService;
    @Autowired
    MenuService menuService;
    @Autowired
    OrganizationRepository orgRepo;

    @GetMapping("/supplier")
    public List<Supplier> getAllSuppliers(HttpServletRequest request) {

        List<Supplier> supplier = new ArrayList<Supplier>();

        try {

            supplier = supplierService.findAll();
        }

        catch (Exception e) {

        }
        return supplier;
    }

    @PostMapping("/supplier")
    public ResponseData createSupplier(@RequestBody Supplier supplier, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            Supplier sup = supplierService.save(supplier, request);
            responseData.setData(sup);
            responseData.setStatusCode(201);
            responseData.setMessage("Supplier created successfully");

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

    @GetMapping("/supplier/{id}")
    public ResponseData getSupplier(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Supplier sup = supplierService.getSupplier(id);
            responseData.setData(sup);
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

    @DeleteMapping("/delete-supplier/{id}")
    public ResponseData deleteSupplier(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            supplierService.deleteById(id, request);
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

    @PutMapping("/supplier/{id}")
    public ResponseData updateSupplier(@RequestBody Supplier supplier, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            Supplier sup = supplierService.update(supplier, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(sup);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/supplier-list1")
    public List<SupplierResponse> getAllAreas(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<SupplierResponse> response = new ArrayList<SupplierResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "supplier";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (Supplier mn : org.get().getSuppliers()) {
                        SupplierResponse res = new SupplierResponse();
                        res.setId(mn.getId());
                        res.setName(mn.getName());
                        res.setPhone(mn.getPhone());
                        res.setNote(mn.getNote());
                        res.setAddress(mn.getAddress());
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
