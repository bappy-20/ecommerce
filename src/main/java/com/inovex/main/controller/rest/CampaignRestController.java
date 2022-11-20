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

import com.inovex.main.entity.Campaign;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.json.response.CampaignResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CampaignService;
import com.inovex.main.service.CampaignTypesService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.ProductService;

@RestController
@RequestMapping("/api")
public class CampaignRestController {

    @Autowired
    CampaignService campaignService;
    ProductService prdService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;
    @Autowired
    CampaignTypesService campTypeService;

    @GetMapping("/campaign-list")
    public Set<Campaign> getAllCampaignList(HttpServletRequest request) {
        Set<Campaign> campaignList = new HashSet<Campaign>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            if (org.isPresent()) {
                campaignList = org.get().getCampaigns();
            }
        }
        return campaignList;
    }

    @PostMapping("/save-campaign")
    private ResponseData createCampaign(@RequestBody Campaign campaign, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            Campaign campp = campaignService.save(campaign, request);
            responseData.setData(campp);
            responseData.setStatusCode(200);
            responseData.setMessage("Campaign is created");
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

    @GetMapping("/get-campaign/{id}")
    public ResponseData getCampaign(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<Campaign> camp = campaignService.findById(id);
            responseData.setData(camp);
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

    @DeleteMapping("/delete-campaign/{id}")
    public ResponseData deleteCampaign(@PathVariable Long id, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            campaignService.deleteById(id, request);
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

    @PutMapping("/update-campaign/{id}")
    public ResponseData updateCampaign(@RequestBody Campaign campaign, @PathVariable Long id,
            HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            Campaign camp = campaignService.update(campaign, id, request);
            responseData.setData(camp);
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

    @GetMapping("/get-campaign-by-campaign-type/{campaignType}")
    public List<Campaign> getCampaignByType(@PathVariable Long campaignType) {
        List<Campaign> camp = new ArrayList<>();
        try {
            camp = campaignService.findAllCampaignType(campaignType);
            return camp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return camp;
    }

    @GetMapping("/campaing-role-list")
    public List<CampaignResponse> getAllCampaignRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<CampaignResponse> response = new ArrayList<CampaignResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "campaign";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (Campaign mn : org.get().getCampaigns()) {
                        CampaignResponse res = new CampaignResponse();
                        res.setId(mn.getId());
                        res.setExpiredDate(mn.getExpiredDate());
                        res.setStartTime(mn.getStartTime());
                        if (mn.getStatus() == 1) {
                            res.setStatus("Active");
                        } else {
                            res.setStatus("Inactive");
                        }
                        res.setCampaignName(mn.getCampaignName());
                        res.setCampaignType(mn.getCampaignType());
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