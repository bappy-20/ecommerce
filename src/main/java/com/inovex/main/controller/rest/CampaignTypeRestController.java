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

import com.inovex.main.entity.CampaignType;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.json.response.CampaignTypeResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CampaignTypesService;
import com.inovex.main.service.MenuService;

@RestController
@RequestMapping("/api")
public class CampaignTypeRestController {

    @Autowired
    CampaignTypesService campaignTypeService;
    @Autowired
    MenuService menuService;
    @Autowired
    OrganizationRepository orgRepo;

    @GetMapping("/campaign-type-list")
    public List<CampaignType> getAllCampaignTypeList(HttpServletRequest request) {
        List<CampaignType> campaignTypeList = new ArrayList<CampaignType>();
        try {
            campaignTypeList = campaignTypeService.findAll();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return campaignTypeList;
    }

    @PostMapping("/save-campaign-type")
    private ResponseData createCampaignType(@RequestBody CampaignType campaignType, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            CampaignType camType = campaignTypeService.save(campaignType, request);
            responseData.setData(camType);
            responseData.setStatusCode(200);
            responseData.setMessage("Campaign Type is created");
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

    @GetMapping("/get-campaign-type/{id}")
    public ResponseData getCampaignType(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<CampaignType> camType = campaignTypeService.findById(id);
            responseData.setData(camType);
            responseData.setStatusCode(200);
            responseData.setMessage("Ok");
            return responseData;

        } catch (Exception e) {
            // TODO: handle exception
            responseData.setData(null);
            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            return responseData;
        }
    }

    @DeleteMapping("/delete-campaign-type/{id}")
    public ResponseData deleteCampaignType(@PathVariable Long id,HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            campaignTypeService.deleteById(id, request);
            responseData.setStatusCode(204);
            responseData.setMessage("Delete Succesfully");
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @PutMapping("/update-campaign-type/{id}")
    public ResponseData updateCampaignType(@RequestBody CampaignType campaignType, @PathVariable Long id,
            HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            CampaignType camType = campaignTypeService.update(campaignType, id, request);
            responseData.setData(camType);
            responseData.setStatusCode(200);
            responseData.setMessage("Update Fuel Succesfully");
            return responseData;
        } catch (Exception e) {
            // TODO: handle exception
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    /**
     * @author bappi
     * 
     */

    @GetMapping("/campaign-type-role-list")
    public List<CampaignTypeResponse> getAllCampTypesRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<CampaignTypeResponse> response = new ArrayList<CampaignTypeResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "campaignType";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (CampaignType mn : org.get().getCampaignType()) {
                        CampaignTypeResponse res = new CampaignTypeResponse();
                        res.setId(mn.getId());
                        res.setCampaignType(mn.getCampaignType());
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