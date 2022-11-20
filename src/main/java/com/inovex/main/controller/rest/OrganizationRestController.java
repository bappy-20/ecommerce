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
import com.inovex.main.json.response.OrganizationResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.OrganizationService;

@RestController
@RequestMapping("/api")
public class OrganizationRestController {
    @Autowired
    OrganizationService organizationService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/organization-list")
    public List<OrganizationResponse> getAllOrganizations() {
        try {
            List<OrganizationResponse> data = new ArrayList<>();
            List<Organization> data1 = organizationService.getAllOrganizations();
            for (Organization org : data1) {
                OrganizationResponse org1 = new OrganizationResponse();
                org1.setId(org.getId());
                org1.setOrgName(org.getOrgName());
                org1.setOwnerName(org.getOwnerName());
                org1.setContactName(org.getContactName());
                org1.setAddress(org.getAddress());
                org1.setEmail(org.getEmail());
                org1.setMobile(org.getMobile());
                org1.setWebsite(org.getWebsite());
                org1.setSubscriptionNumber(org.getSubscriptionNumber());
                org1.setSubscriptionPeriod(org.getSubscriptionPeriod());
                org1.setLogo(org.getLogo());
                org1.setAbout(org.getAbout());
                org1.setCanDelete(false);
                org1.setCanEdit(false);
                data.add(org1);
            }

            return data;
        } catch (Exception exception) {

            return null;
        }
    }

    @GetMapping("/organizations/{id}")
    public ResponseData getOrganizations(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Organization data = organizationService.getOrganization(id);
            responseData.setData(data);
            responseData.setMessage("ok");
            responseData.setStatusCode(200);
            return responseData;
        } catch (Exception exception) {
            responseData.setData(null);
            responseData.setMessage(exception.getMessage());
            responseData.setStatusCode(500);
            responseData.setStatus("error");
            return responseData;
        }
    }

    @PostMapping("/organizations")
    public ResponseData createOrganization(@RequestBody Organization organization) {
        ResponseData responseData = new ResponseData();
        try {
            Organization data = organizationService.createOrganization(organization);
            responseData.setData(data);
            responseData.setMessage("ok");
            responseData.setStatusCode(201);
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setData(null);

            responseData.setStatusCode(500);
            responseData.setMessage("Please Fill up every field");
            return responseData;
        }

    }

    @PutMapping("/organizations/{id}")
    public ResponseData updateOrganization(@RequestBody Organization organization, @PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        System.out.println("called !" + id);
        try {
            Organization data = organizationService.updateOrganization(organization, id);
            responseData.setData(data);
            responseData.setMessage("updated successfully");
            responseData.setStatusCode(200);
            return responseData;
        } catch (NullPointerException e) {

            responseData.setData(null);

            responseData.setStatusCode(500);
            responseData.setMessage("Please Fill up every field");
            return responseData;
        } catch (DataIntegrityViolationException e) {

            responseData.setData(null);

            responseData.setStatusCode(500);
            responseData.setMessage("Duplicate Organization name.please add a new name ");
            return responseData;
        }
    }

    @DeleteMapping("/organizations/{id}")
    public ResponseData deleteOrganizaton(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Long data = organizationService.deleteOrganization(id);
            if (data == null)
                throw new Exception("data Deletion failed");
            responseData.setMessage("ok");
            responseData.setStatusCode(200);
            return responseData;
        } catch (Exception e) {
            boolean isExists = e.getCause().toString().contains("ConstraintViolationException");
            if (isExists) {
                responseData
                        .setMessage("You have some dependency with car and checkpoint.remove first then try again.");
            } else {
                responseData.setMessage(e.getMessage());
            }
            responseData.setData(null);
            responseData.setStatusCode(500);
            return responseData;
        }
    }

    @GetMapping("/organizations/profile")
    public Organization getOrgProfile(HttpServletRequest request) {
        Organization org = new Organization();
        try {
            if (request.getSession().getAttribute("orgId") != null) {
                long id = (long) request.getSession().getAttribute("orgId");
                org = organizationService.getOrganization(id);

                if (org != null) {
                    System.out.println("org is not ");
                }
            }

        } catch (Exception e) {

        }
        return org;

    }

    @GetMapping("/organizations")
    public List<OrganizationResponse> getAllOrganizations(HttpServletRequest request) {

        Set<Menu> menu = new HashSet<Menu>();
        List<OrganizationResponse> response = new ArrayList<OrganizationResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "Org";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

                    OrganizationResponse res = new OrganizationResponse();
                    res.setId(org.get().getId());
                    res.setOrgName(org.get().getOrgName());
                    res.setOwnerName(org.get().getOwnerName());
                    res.setContactName(org.get().getContactName());
                    res.setMobile(org.get().getMobile());
                    res.setEmail(org.get().getEmail());
                    res.setAddress(org.get().getAddress());
                    res.setWebsite(org.get().getWebsite());
                    res.setAbout(org.get().getAbout());
                    res.setLogo(org.get().getLogo());
                    res.setCanEdit(rights.get(0));
                    res.setCanDelete(rights.get(1));
                    response.add(res);

                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

        return response;
    }
}
