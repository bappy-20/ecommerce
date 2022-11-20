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
import com.inovex.main.entity.MenuResponse;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.SubMenu;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;

@RestController
@RequestMapping("/api")
public class RoleManageRestController {

    @Autowired
    MenuService menuService;
    @Autowired
    OrganizationRepository orgRepo;

    @GetMapping("/role-list")
    public List<MenuResponse> getAllAreas(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<MenuResponse> response = new ArrayList<MenuResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "rolemanagement";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (Menu mn : menu) {
                        MenuResponse res = new MenuResponse();
                        res.setId(mn.getId());
                        res.setRoleName(mn.getRoleName());
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

    @GetMapping("/submenu/{role}")
    public Set<SubMenu> getUserRoles(HttpServletRequest request, @PathVariable String role) {
        Set<SubMenu> subMenu = new HashSet<SubMenu>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Set<Menu> menu = new HashSet<Menu>();
            Optional<Organization> org = orgRepo.findById(orgId);
            if (org.isPresent()) {
                menu = org.get().getMenu();
                Optional<Menu> matchingObject = menu.stream().filter(p -> p.getRoleName().equals(role)).findFirst();
              if(matchingObject.isPresent()) {
            	  subMenu = matchingObject.get().getSubMenu();
              }
                
            }
        }

        return subMenu;

    }

    @PostMapping("/menu")
    public ResponseData createArea(@RequestBody Menu menu, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            Menu ar = menuService.save(menu, request);
            responseData.setData(ar);
            responseData.setStatusCode(201);
            responseData.setMessage("menu created successfully");
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

    @GetMapping("/role/{id}")
    public ResponseData getArea(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<Menu> mn = menuService.findById(id);
            responseData.setData(mn.get());
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

    @DeleteMapping("/delete-menu/{id}")
    public ResponseData deleteArea(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            menuService.deleteById(id, request);
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

    @PutMapping("/menu/{id}")
    public ResponseData updateArea(@RequestBody Menu menu, @PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            Menu ar = menuService.update(menu, id, request);
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

}
