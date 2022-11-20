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
import com.inovex.main.entity.Role;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.RoleResponse;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleRestController {
    @Autowired
    RoleService roleService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/user-role-list")
    public Set<Role> getAllRegionsInfo(HttpServletRequest request) {
        Set<Role> list = new HashSet<Role>();
        try {
            list = roleService.getAllRole(request);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    @PostMapping("/user-role/{orgId}")
    public ResponseData createRegion(@PathVariable long orgId, @RequestBody Role role) {
        ResponseData responseData = new ResponseData();

        try {
            Role rgn = roleService.save(role, orgId);
            responseData.setData(rgn);
            responseData.setStatusCode(201);
            responseData.setMessage("Role created successfully");

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

    @GetMapping("/user-role/{id}")
    public ResponseData viewCar(@PathVariable("id") long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<Role> s = roleService.findByRoleId(id);

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

    @PutMapping("/user-role/{id}")
    public ResponseData update(@RequestBody Role role, @PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            roleService.update(role, id);
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

    @DeleteMapping("/user-role/{id}")
    public ResponseData delete(@PathVariable Long id, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            roleService.deleteById(request, id);
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

    @GetMapping("/role-list-all")
    public List<RoleResponse> getAllRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<RoleResponse> response = new ArrayList<RoleResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    for (Role mn : org.get().getRole()) {
                        RoleResponse res = new RoleResponse();
                        res.setId(mn.getId());
                        res.setName(mn.getName());
                        res.setDescription(mn.getDescription());
                        res.setCanEdit(true);
                        res.setCanDelete(true);
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
