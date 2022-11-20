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

import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.json.response.DistributorResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.MenuService;

@RestController
@RequestMapping("/api")
public class DistributorRestController {

    @Autowired
    DistributorService distributorService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/distributor")
    public Set<Distributor> getAllDistributors(HttpServletRequest request) {
        Set<Distributor> distributorList = new HashSet<Distributor>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                distributorList = org.get().getDistributors();
            } catch (Exception e) {

            }
        }
        return distributorList;
    }

    @PostMapping("/distributor")
    public ResponseData createDistributor(@RequestBody Distributor distributor, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            Distributor dst = distributorService.save(distributor, request);
            responseData.setData(dst);
            responseData.setStatusCode(201);
            responseData.setMessage("Distributor created successfully");

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

    @GetMapping("/distributor/{id}")
    public ResponseData getDistributor(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Distributor dst = distributorService.getDistributor(id);
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

    @DeleteMapping("/delete-distributor/{id}")
    public ResponseData deleteDistributor(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            distributorService.deleteById(id, request);
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

    @PutMapping("/distributor/{id}")
    public ResponseData updateDistributor(@RequestBody Distributor distributor, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            Distributor dst = distributorService.update(distributor, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(dst);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/distributor-list")
    public List<DistributorResponse> getAllDistributor(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<DistributorResponse> responses = new ArrayList<DistributorResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();

                    String rolemanagement = "Dist";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (Distributor mn : org.get().getDistributors()) {
                        DistributorResponse res = new DistributorResponse();
                        res.setId(mn.getId());
                        res.setDistributorName(mn.getDistributorName());
                        res.setDistributorAddress(mn.getDistributorAddress());
                        res.setNid(mn.getNid());
                        res.setDistributorOwner(mn.getDistributorOwner());
                        res.setDistributorPhone(mn.getDistributorPhone());
                        res.setDistributorType(mn.getDistributorType());
                        res.setDistributorCredit(mn.getDistributorCredit());
                        res.setTradeImage(mn.getTradeImage());
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        responses.add(res);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }

        }
        return responses;

    }

}
