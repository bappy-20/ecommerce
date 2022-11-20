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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.RouteModel;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.RouteResponse;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.RouteModelService;

@RestController
@RequestMapping("/api")
public class RouteRestController {
    @Autowired
    RouteModelService routeService;
    @Autowired
    OrganizationService orgService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/route-list")
    public Set<RouteModel> getAllRoutesInfo(HttpServletRequest request) {
        Set<RouteModel> Routelist = new HashSet<RouteModel>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                Routelist = org.get().getRoutes();
            } catch (Exception e) {
            }
        } else {
            System.out.println("Org is null");
        }
        return Routelist;
    }

    @PostMapping("/route")
    public ResponseData createRoute(@RequestBody RouteModel route, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            RouteModel rt = routeService.save(route, request);
            responseData.setData(rt);
            responseData.setStatusCode(201);
            responseData.setMessage("Supplier created successfully");

            return responseData;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseData.setData(null);
            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    @GetMapping("/route/{id}")
    public ResponseData viewCar(@PathVariable("id") String id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<RouteModel> s = routeService.findById(Long.valueOf(id));

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

    /*
     * @PutMapping("/region/{id}") public ResponseData update(@RequestBody
     * RegionModel region, @PathVariable Long id, HttpServletRequest request) {
     * 
     * ResponseData responseData = new ResponseData(); try {
     * regionService.update(region, id, request); responseData.setStatusCode(200);
     * responseData.setMessage("update successfully"); return responseData; } catch
     * (NullPointerException e) {
     * 
     * responseData.setData(null);
     * 
     * responseData.setStatusCode(500);
     * responseData.setMessage("Please Fill up every field"); return responseData; }
     * catch (DataIntegrityViolationException e) {
     * 
     * responseData.setData(null);
     * 
     * responseData.setStatusCode(500);
     * responseData.setMessage("Duplicate Car number.please add a new car "); return
     * responseData; }
     * 
     * }
     */
    @DeleteMapping("/delete-route/{id}")
    public ResponseData delete(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            routeService.deleteById(id, request);
            responseData.setStatusCode(204);
            responseData.setMessage("delete successfully");
            return responseData;

        } catch (Exception e) {
            e.printStackTrace();
            responseData.setMessage(e.getMessage());
            responseData.setData(null);
            responseData.setStatusCode(500);
            return responseData;
        }
    }

    @GetMapping("/route-list-role")
    public List<RouteResponse> getAllRouteRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<RouteResponse> response = new ArrayList<RouteResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "route";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (RouteModel mn : org.get().getRoutes()) {
                        RouteResponse res = new RouteResponse();
                        res.setId(mn.getId());
                        res.setAreaName(mn.getAreaName());
                        res.setMarketName(mn.getMarketName());
                        res.setRegionName(mn.getRegionName());
                        res.setTerritoryName(mn.getTerritoryName());
                        res.setRouteName(mn.getRouteName());
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
