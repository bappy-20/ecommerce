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
import com.inovex.main.entity.UserType;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.UserTypeResponse;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.UserTypeService;

@RestController
@RequestMapping("/api")
public class UserTypeRestController {

    @Autowired
    UserTypeService userTypeService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/user-Type")
    public List<UserType> getAllUserTypes(HttpServletRequest request) {

        List<UserType> userType = new ArrayList<UserType>();

        try {

            userType = userTypeService.findAll();
        }

        catch (Exception e) {

        }
        return userType;
    }

    @PostMapping("/user-Type")
    public ResponseData createUserType(@RequestBody UserType userType, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            UserType ctg = userTypeService.save(userType, request);
            responseData.setData(ctg);
            responseData.setStatusCode(201);
            responseData.setMessage("UserType created successfully");

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

    @GetMapping("/user-Type/{id}")
    public ResponseData getUserType(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            UserType ctg = userTypeService.getUserType(id);
            responseData.setData(ctg);
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

    @DeleteMapping("/user-Type/{id}")
    public ResponseData deleteUserType(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            userTypeService.deleteById(id, request);
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

    @PutMapping("/user-Type/{id}")
    public ResponseData updateUserType(@RequestBody UserType userType, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            UserType ctg = userTypeService.update(userType, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(ctg);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/user-type-list")
    public List<UserTypeResponse> getAlLUserType(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<UserTypeResponse> response = new ArrayList<UserTypeResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "empType";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (UserType mn : org.get().getUserType()) {
                        UserTypeResponse res = new UserTypeResponse();
                        res.setId(mn.getId());
                        res.setUserType(mn.getUserType());
                        res.setNote(mn.getNote());
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        response.add(res);
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

        return response;
    }

}
