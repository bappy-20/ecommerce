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

import com.inovex.main.entity.ExpenseType;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.json.response.ExpenseTypeResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.ExpenseTypeService;
import com.inovex.main.service.MenuService;

@RestController
@RequestMapping("/api")
public class ExpenseTypeRestController {

    @Autowired
    ExpenseTypeService expenseTypeService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/expense-Type")
    public Set<ExpenseType> getAllExpenseTypes(HttpServletRequest request) {

        Set<ExpenseType> expenseTypeList = new HashSet<ExpenseType>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                expenseTypeList = org.get().getExpenseTypes();
            } catch (Exception e) {
            }
        }
        return expenseTypeList;
    }

    @PostMapping("/save-expenseType")
    public ResponseData createExpenseType(@RequestBody ExpenseType expenseType, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            ExpenseType ext = expenseTypeService.save(expenseType, request);
            responseData.setData(ext);
            responseData.setStatusCode(201);
            responseData.setMessage("expenseType created successfully");

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

    @GetMapping("/get-expenseType/{id}")
    public ResponseData getexpenseType(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<ExpenseType> ext = expenseTypeService.findById(id);
            responseData.setData(ext.get());
            responseData.setStatusCode(200);
            responseData.setMessage("ok");
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

    @DeleteMapping("/delete-expenseType/{id}")
    public ResponseData deleteExpenseType(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            expenseTypeService.deleteById(id, request);
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

    @PutMapping("/update-expenseType/{id}")
    public ResponseData updateExpenseType(@RequestBody ExpenseType expenseType, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            ExpenseType brd = expenseTypeService.update(expenseType, id, request);
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

//    @GetMapping("/expense-by-user-type/{userType}")
//    public List<ExpenseType> getAllExpenseUserType(@PathVariable String userType, HttpServletRequest request) {
//
//        List<ExpenseType> expenseTypeList = new ArrayList<ExpenseType>();
//
//        try {
//
//            expenseTypeList = expenseTypeService.findAllByExpenseUser(userType);
//        }
//
//        catch (Exception e) {
//
//        }
//        return expenseTypeList;
//    }

    @GetMapping("/expense-type-role")
    public List<ExpenseTypeResponse> getAllExpenseTypeRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<ExpenseTypeResponse> response = new ArrayList<ExpenseTypeResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "expenseType";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (ExpenseType mn : org.get().getExpenseTypes()) {
                        ExpenseTypeResponse res = new ExpenseTypeResponse();
                        res.setId(mn.getId());
                        res.setExpenseType(mn.getExpenseType());
//                        res.setExpenseUser(mn.getExpenseUser());
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
