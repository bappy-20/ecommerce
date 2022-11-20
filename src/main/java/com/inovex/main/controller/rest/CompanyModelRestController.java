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
import com.inovex.main.entity.CompanyModel;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.json.response.CompanyResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.BrandModelService;
import com.inovex.main.service.CompanyModelService;
import com.inovex.main.service.MenuService;

@RestController
@RequestMapping("/api")
public class CompanyModelRestController {
    @Autowired
    CompanyModelService companyModelService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;
    @Autowired
    BrandModelService brandModelService;

    @GetMapping("/company-list")
    public Set<CompanyModel> getAllCategorys(HttpServletRequest request) {

        Set<CompanyModel> companyModelList = new HashSet<CompanyModel>();
        // Long orgId = Long.parseLong("295");
        long orgId = (long) request.getSession().getAttribute("orgId");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                companyModelList = org.get().getCompanyModel();
            } catch (Exception e) {

            }
        }
        return companyModelList;
    }

    @PostMapping("/save-company")
    public ResponseData create(@RequestBody CompanyModel company, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            CompanyModel brd = companyModelService.save(company, request);// (brand, request);
            responseData.setData(brd);
            responseData.setStatusCode(201);
            responseData.setMessage("Company created successfully");
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

    @GetMapping("/get-company/{id}")
    public ResponseData get(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<CompanyModel> brd = companyModelService.findById(id);
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

    @DeleteMapping("/delete-company/{id}")
    public ResponseData delete(@PathVariable Long id, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            companyModelService.deleteById(id, request);
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

    @PutMapping("/update-company/{id}")
    public ResponseData update(@RequestBody CompanyModel company, @PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            CompanyModel brd = companyModelService.update(company, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(brd);
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/get-brand-list/{id}")
    public List<BrandModel> getBrandList(@PathVariable Long id) {
        List<BrandModel> responseData = new ArrayList<>();
        try {
            Optional<CompanyModel> brd = companyModelService.findById(id);
            if (brd.isPresent()) {
                responseData = brandModelService.findAllByCompanyId(id);
            }

            return responseData;
        } catch (Exception e) {

        }
        return responseData;
    }

    @GetMapping("/company-list1")
    public List<CompanyResponse> getAllAreas(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<CompanyResponse> response = new ArrayList<CompanyResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "company";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (CompanyModel mn : org.get().getCompanyModel()) {
                        CompanyResponse res = new CompanyResponse();
                        res.setId(mn.getId());
                        res.setCompanyName(mn.getCompanyName());
                        res.setCompanyOrigin(mn.getCompanyOrigin());
                        res.setLogo(mn.getLogo());
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