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

import com.inovex.main.entity.MeasurementUnit;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.json.response.MeasurementUnitResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MeasurementUnitService;
import com.inovex.main.service.MenuService;

@RestController
@RequestMapping("/api")
public class MeasurementUnitRestController {

    @Autowired
    MeasurementUnitService measurementUnitService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/mesurement-Unit")
    public Set<MeasurementUnit> getAllMeasurementUnits(HttpServletRequest request) {

        Set<MeasurementUnit> measurementUnitList = new HashSet<MeasurementUnit>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                measurementUnitList = org.get().getMeasurementUnits();
            } catch (Exception e) {
            }
        }
        return measurementUnitList;
    }

    @PostMapping("/save-measurementUnit")
    public ResponseData createMeasurementUnit(@RequestBody MeasurementUnit measurementUnit,
            HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            MeasurementUnit msu = measurementUnitService.save(measurementUnit, request);
            responseData.setData(msu);
            responseData.setStatusCode(201);
            responseData.setMessage("measurementUnit created successfully");

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

    @GetMapping("/get-measurementUnit/{id}")
    public ResponseData getmeasurementUnit(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<MeasurementUnit> msu = measurementUnitService.findById(id);
            responseData.setData(msu.get());
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

    @DeleteMapping("/delete-measurementUnit/{id}")
    public ResponseData deleteMeasurementUnit(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            measurementUnitService.deleteById(id, request);
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

    @PutMapping("/update-measurementUnit/{id}")
    public ResponseData updateMeasurementUnit(@RequestBody MeasurementUnit measurementUnit, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            MeasurementUnit brd = measurementUnitService.update(measurementUnit, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(brd);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/measurement-unit-list")
    public List<MeasurementUnitResponse> getMeasurementunit(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<MeasurementUnitResponse> response = new ArrayList<MeasurementUnitResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "measurementUnit";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (MeasurementUnit mn : org.get().getMeasurementUnits()) {
                        MeasurementUnitResponse res = new MeasurementUnitResponse();
                        res.setId(mn.getId());
                        res.setName(mn.getName());
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
