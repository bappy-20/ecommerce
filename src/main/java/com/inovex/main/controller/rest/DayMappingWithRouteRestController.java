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

import com.inovex.main.entity.DayModel;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.DayModelResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.DayService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.UserService;

@RestController
@RequestMapping("/api")
public class DayMappingWithRouteRestController {

    @Autowired
    DayService dayModelService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;
    @Autowired
    UserService userService;

    @GetMapping("/day-Model")
    public Set<DayModel> getAllDayModels(HttpServletRequest request) {

        Set<DayModel> dayModelList = new HashSet<DayModel>();

        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                dayModelList = org.get().getDayModels();
            } catch (Exception e) {

            }
        }
        return dayModelList;
    }

    @PostMapping("/day-Model")
    public ResponseData createDayModel(@RequestBody DayModel dayModel, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            DayModel dm = dayModelService.save(dayModel, request);
            responseData.setData(dm);
            responseData.setStatusCode(201);
            responseData.setMessage("DayModel created successfully");

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

    @GetMapping("/day-Model/{id}")
    public ResponseData getDayModel(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            DayModel dm = dayModelService.getDayModel(id);
            responseData.setData(dm);
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

    @DeleteMapping("/delete-day-model/{id}")
    public ResponseData deleteDayModel(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            dayModelService.deleteById(id, request);
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

    @PutMapping("/day-Model/{id}")
    public ResponseData updateDayModel(@RequestBody DayModel dayModel, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            DayModel dm = dayModelService.update(dayModel, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(dm);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/day-modele-role")
    public List<DayModelResponse> getAlDayMOdelRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<DayModelResponse> response = new ArrayList<DayModelResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "assignRoute";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (DayModel mn : org.get().getDayModels()) {
                        DayModelResponse res = new DayModelResponse();
                        res.setId(mn.getId());
                        res.setRouteId(mn.getRouteId());

//                        String empId = mn.getEmpId();
//                        long empid = Long.parseLong(empId);
//                        Optional<User> user = userService.findUserById(empid);
                        Optional<User> user = userService.findByUsername(mn.getEmpId());
                        if (user.isPresent()) {
                            res.setEmpId(user.get().getUsername());
                        } else {
                            res.setEmpId("Not Found");

                        }
                        res.setDayName(mn.getDayName());
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
