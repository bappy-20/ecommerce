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

import com.inovex.main.entity.AreaModel;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.RegionModel;
import com.inovex.main.json.response.AreaResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.AreaService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.RegionModelService;

@RestController
@RequestMapping("/api")
public class AreaRestController {

    @Autowired
    AreaService areaService;
    @Autowired
    RegionModelService regionService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/area")
    public List<AreaResponse> getAllAreas(HttpServletRequest request) {
        Set<AreaModel> area = new HashSet<AreaModel>();
        List<AreaResponse> response = new ArrayList<AreaResponse>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        try {

            area = org.get().getAreaModels();
            for (AreaModel areaModel : area) {
                AreaResponse res = new AreaResponse();
                res.setId(areaModel.getId());
                res.setAreaName(areaModel.getAreaName());
                Optional<RegionModel> region = regionService.findById(areaModel.getRegionId1());
                if (region.isPresent()) {
                    res.setRegionName(region.get().getRegionName());
                } else {
                    res.setRegionName("no region found!");
                }

                response.add(res);
            }
        }

        catch (Exception e) {

        }
        return response;

    }

    @PostMapping("/area")
    public ResponseData createArea(@RequestBody AreaModel area, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        System.out.println(area.getRegionId1());
        try {
            AreaModel ar = areaService.save(area, request);
            responseData.setData(ar);
            responseData.setStatusCode(201);
            responseData.setMessage("Area created successfully");

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

    @GetMapping("/area/{id}")
    public ResponseData getArea(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            AreaModel dst = areaService.getArea(id);
            responseData.setData(dst);
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

    @DeleteMapping("/delete-area/{id}")
    public ResponseData deleteArea(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            areaService.deleteById(id, request);
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

    @PostMapping("/area-mapping/{id}")
    public ResponseData submitAreaMapping(@RequestBody List<Long> areaId, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {

            areaService.saveAreaMapping(areaId, id);
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

    @PutMapping("/area/{id}")
    public ResponseData updateArea(@RequestBody AreaModel area, @PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            System.out.println("area id " + area.getRegionId1());
            AreaModel ar = areaService.update(area, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(ar);
            return responseData;
        } catch (Exception e) {

            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/area-list-role")
    public List<AreaResponse> getAllAreaListRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<AreaResponse> response = new ArrayList<AreaResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "area";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

                    for (AreaModel areaModel : org.get().getAreaModels()) {
                        AreaResponse res = new AreaResponse();
                        res.setId(areaModel.getId());
                        res.setAreaName(areaModel.getAreaName());
                        Optional<RegionModel> region = regionService.findById(areaModel.getRegionId1());
                        if (region.isPresent()) {
                            res.setRegionName(region.get().getRegionName());
                        } else {
                            res.setRegionName("no region found!");
                        }
                        res.setUsers(areaModel.getUsers());
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

    @GetMapping("/all-area-list")
    public Set<AreaModel> getAllAreaList(HttpServletRequest request) {

        Set<AreaModel> arealist = new HashSet<AreaModel>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {

                    arealist = org.get().getAreaModels();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arealist;
    }

}
