package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.inovex.main.entity.RegionModel;
import com.inovex.main.json.response.RegionResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.RegionModelService;

@RestController
@RequestMapping("/api")
public class RegionRestController {
    @Autowired
    RegionModelService regionService;
    @Autowired
    DistributorService distService;
    @Autowired
    OrganizationService orgService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/region-list")
    public Set<RegionModel> getAllRegionsInfo(HttpServletRequest request) {
        Set<RegionModel> regionList = new HashSet<RegionModel>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            regionList = org.get().getRegions();
        }
        return regionList;
    }

    @PostMapping("/get-region")
    public ResponseData createRegion(@RequestBody RegionModel region, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            RegionModel rgn = regionService.save(region, request);
            responseData.setData(rgn);
            responseData.setStatusCode(201);
            responseData.setMessage("Region created successfully");

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

    @GetMapping("/region/{id}")
    public ResponseData viewCar(@PathVariable("id") String id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<RegionModel> s = regionService.findById(Long.valueOf(id));

            responseData.setData(s.get());
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

    @PutMapping("/region/{id}")
    public ResponseData update(@RequestBody RegionModel region, @PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            regionService.update(region, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            return responseData;
        } catch (NullPointerException e) {

            responseData.setData(null);

            responseData.setStatusCode(500);
            responseData.setMessage("Please Fill up every field");
            return responseData;
        } catch (DataIntegrityViolationException e) {

            responseData.setData(null);

            responseData.setStatusCode(500);
            responseData.setMessage("Duplicate Car number.please add a new car ");
            return responseData;
        }

    }

    @DeleteMapping("/delete-region/{id}")
    public ResponseData delete(@PathVariable Long id, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            regionService.deleteById(id, request);
            responseData.setStatusCode(204);
            responseData.setMessage("delete successfully");
            return responseData;

        } catch (Exception e) {
            responseData.setMessage(e.getMessage());
            responseData.setData(null);
            responseData.setStatusCode(500);
            return responseData;
        }
    }

    @GetMapping("/region-list-role")
    public List<RegionResponse> getAllRegionsList(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<RegionResponse> response = new ArrayList<RegionResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "region";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (RegionModel mn : org.get().getRegions()) {
                        RegionResponse res = new RegionResponse();
                        res.setId(mn.getId());
                        res.setRegionName1(mn.getRegionName());
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

    @GetMapping("/all-region-list")
    public Set<RegionModel> getAllList(HttpServletRequest request) {

        Set<RegionModel> arealist = new HashSet<RegionModel>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {

                    arealist = org.get().getRegions();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arealist;
    }

    @PostMapping("/region-mapping/{id}")
    public ResponseData submitUserMapping(@RequestBody List<Long> regionId, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {

            regionService.saveUserMapping(regionId, id);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(null);
            return responseData;
        } catch (Exception e) {

            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }
}
