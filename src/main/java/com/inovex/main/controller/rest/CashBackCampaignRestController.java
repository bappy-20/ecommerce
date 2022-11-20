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
import com.inovex.main.entity.CashBackCampaign;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.json.response.CashBackCampaignResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CampaignService;
import com.inovex.main.service.CashBackCampaignService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.OrganizationService;

@RestController
@RequestMapping("/api")
public class CashBackCampaignRestController {

    @Autowired
    CashBackCampaignService campaignService;
    @Autowired
    CampaignService campService;
    @Autowired
    OrganizationService orgService;
    @Autowired
    MenuService menuService;
    @Autowired
    OrganizationRepository orgRepo;

    @GetMapping("/cash-back-campaign-list")
    public Set<CashBackCampaignResponse> getAllCampaignList(HttpServletRequest request) {
        Set<CashBackCampaign> campaignList = new HashSet<CashBackCampaign>();
        Set<CashBackCampaignResponse> cashBackcampaignList = new HashSet<CashBackCampaignResponse>();
        try {
            if (request.getSession().getAttribute("orgId") != null) {
                long orgId = (long) request.getSession().getAttribute("orgId");
                Optional<Organization> org = orgService.findById(orgId);
                if (org.isPresent()) {
                    campaignList = org.get().getCashBackCampaign();
                    for (CashBackCampaign cbc : campaignList) {
                        CashBackCampaignResponse cbcRes = new CashBackCampaignResponse();
                        cbcRes.setId(cbc.getId());
                        cbcRes.setDiscountType(cbc.getDiscountType());
                        cbcRes.setDiscountAmount(cbc.getDiscountAmount());
                        cbcRes.setRequiredInvoiceAmount(cbc.getRequiredInvoiceAmount());
                        Optional<Campaign> camp = campService.findById(cbc.getCampaignId());
                        if (camp.isPresent()) {
                            cbcRes.setCampaignName(camp.get().getCampaignName());
                        } else {
                            cbcRes.setCampaignName("Not found");
                        }
                        cashBackcampaignList.add(cbcRes);
                    }
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return cashBackcampaignList;
    }

    @PostMapping("/save-cash-back-campaign")
    private ResponseData createCampaign(@RequestBody CashBackCampaign campaign, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            CashBackCampaign cam = campaignService.save(campaign, request);
            responseData.setData(cam);
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

    @GetMapping("/get-cash-back-campaign/{id}")
    public ResponseData getCampaign(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<CashBackCampaign> camp = campaignService.findById(id);
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

    @DeleteMapping("/delete-cash-back-campaign/{id}")
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

    @PutMapping("/update-cash-back-campaign/{id}")
    public ResponseData updateCampaign(@RequestBody CashBackCampaign campaign, @PathVariable Long id,
            HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            CashBackCampaign camp = campaignService.update(campaign, id, request);
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

    @GetMapping("/get-available-cash-back-campaign")
    public Set<CashBackCampaignResponse> getAvailableCampaign(HttpServletRequest request) {
        Set<CashBackCampaignResponse> cashBackcampaignList = new HashSet<>();
        try {
            cashBackcampaignList = campaignService.getAvailableCashBack(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cashBackcampaignList;
    }

    @GetMapping("/cashback-camp-list")
    public List<CashBackCampaignResponse> getAllCasshBackCampList(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<CashBackCampaignResponse> response = new ArrayList<CashBackCampaignResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "cashBackCampaign";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    /*
                     * for (Menu mn : menu) { CashBackCampaignResponse res = new
                     * CashBackCampaignResponse(); res.setId(mn.getId());
                     * res.setCanEdit(rights.get(0)); res.setCanDelete(rights.get(1));
                     * response.add(res); }
                     */
                    for (CashBackCampaign cbc : org.get().getCashBackCampaign()) {
                        CashBackCampaignResponse cbcRes = new CashBackCampaignResponse();
                        cbcRes.setId(cbc.getId());
                        cbcRes.setDiscountType(cbc.getDiscountType());
                        cbcRes.setDiscountAmount(cbc.getDiscountAmount());
                        cbcRes.setRequiredInvoiceAmount(cbc.getRequiredInvoiceAmount());
                        Optional<Campaign> camp = campService.findById(cbc.getCampaignId());
                        if (camp.isPresent()) {
                            cbcRes.setCampaignName(camp.get().getCampaignName());
                        } else {
                            cbcRes.setCampaignName("Not found");
                        }
                        cbcRes.setCanEdit(rights.get(0));
                        cbcRes.setCanDelete(rights.get(1));
                        response.add(cbcRes);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

}