package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.Employee;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.UserType;
import com.inovex.main.json.response.EmployeeResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.UserService;
import com.inovex.main.service.UserTypeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserTypeService userTypeService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;
    @Autowired
    UserService userService;

    @GetMapping("/get-employee")
    public List<EmployeeResponse> getAllEmployees(HttpServletRequest request) {
        List<EmployeeResponse> res = new ArrayList<>();

        Set<Employee> employee = new HashSet<Employee>();
        Long orgId = Long.parseLong("1");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                employee = org.get().getEmployess();
                for (Employee employee2 : employee) {
                    EmployeeResponse empRes = new EmployeeResponse();
                    empRes.setId(employee2.getId());
                    empRes.setEmployeeId(employee2.getEmployeeId());
                    empRes.setEmpName(employee2.getEmpName());

                    Optional<UserType> usertype = userTypeService.findById(employee2.getEmpCategory());
                    if (usertype.isPresent()) {
                        empRes.setEmpCategory(usertype.get().getUserType());
                    } else {
                        empRes.setEmpCategory("not found!");
                    }
                    Optional<Employee> reporId = employeeService.findByEmployeeId(employee2.getReportingId());
                    if (reporId.isPresent()) {
                        empRes.setReportingName(reporId.get().getEmpName());
                    } else {
                        empRes.setReportingName("not found!");
                    }
                    empRes.setEmpAddress(employee2.getEmpAddress());
                    empRes.setEmpImage(employee2.getEmpImage());
                    empRes.setEmpPhone(employee2.getEmpPhone());
                    empRes.setPassword(employee2.getPassword());
                    res.add(empRes);
                }
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;

    }

    @PostMapping("/get-employee")
    public ResponseData createEmployee(@RequestBody Employee employee, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            Employee emp = employeeService.saveEmployee(employee, request);
            responseData.setData(emp);
            responseData.setStatusCode(201);
            responseData.setMessage("Employee created successfully");

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

    @GetMapping("/get-employee/{id}")
    public ResponseData getEmployee(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Employee emp = employeeService.getEmployee(id);
            responseData.setData(emp);
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

    @DeleteMapping("/delete-employee/{id}")
    public ResponseData deleteEmployee(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            employeeService.deleteById(id, request);
            // employeeService.deleteById(id, request);
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

    @PutMapping("/reset-employee/{id}")
    public ResponseData resetEmployeePass(@PathVariable String id) {

        ResponseData responseData = new ResponseData();
        try {
            String pass = "1234567";
            employeeService.resetEmployeePasswordById(id, pass);
            responseData.setStatusCode(204);
            responseData.setMessage("Reset successfully");
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    @PutMapping("/get-employee/{id}")
    public ResponseData updateEmployee(@RequestBody Employee employee, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            Employee emp = employeeService.update(employee, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(emp);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }
    
//    @RequestMapping(value = "/employee-server-side", method = RequestMethod.POST)
//    public String getDailySalary(@RequestParam("start") int start, @RequestParam("length") int length,
//            @RequestParam("search[value]") String searchParam) {
//
//        ArrayList<Object> emp = userService.getPagination(start, length, searchParam);
//        JSONArray main = new JSONArray();
//        JSONArray obj1 = new JSONArray();
//
//        Iterator itr = emp.iterator();
//        while (itr.hasNext()) {
//            obj1 = new JSONArray();
//            Object[] obj = (Object[]) itr.next();
//            obj1.put(String.valueOf(obj[0]));
//            obj1.put(String.valueOf(obj[1]));
//            obj1.put(String.valueOf(obj[2]));
//            obj1.put(0);
//            obj1.put(0);
//            main.put(obj1);
//        }
//        JSONObject responseObj = new JSONObject();
//        Long totalLength = employeeService.getCountWithSearchParm(searchParam);
//        responseObj.put("data", main);
//        responseObj.put("recordsTotal", totalLength);
//        responseObj.put("recordsFiltered", totalLength);
//        return responseObj.toString();
//    }

    @RequestMapping(value = "/employee-server-side", method = RequestMethod.POST)
    public String getDailySalary(@RequestParam("start") int start, @RequestParam("length") int length,
            @RequestParam("search[value]") String searchParam) {

        ArrayList<Object> emp = employeeService.getPagination(start, length, searchParam);
        JSONArray main = new JSONArray();
        JSONArray obj1 = new JSONArray();

        Iterator itr = emp.iterator();
        while (itr.hasNext()) {
            obj1 = new JSONArray();
            Object[] obj = (Object[]) itr.next();
            obj1.put(String.valueOf(obj[0]));
            obj1.put(String.valueOf(obj[1]));
            obj1.put(String.valueOf(obj[2]));
            obj1.put(0);
            obj1.put(0);
            main.put(obj1);
        }
        JSONObject responseObj = new JSONObject();
        Long totalLength = employeeService.getCountWithSearchParm(searchParam);
        responseObj.put("data", main);
        responseObj.put("recordsTotal", totalLength);
        responseObj.put("recordsFiltered", totalLength);
        return responseObj.toString();
    }

    @PutMapping("/employee-Inactive/{id}")
    public ResponseData approveTripStatus(@PathVariable Long id) {

        ResponseData responseData = new ResponseData();
        try {
            Employee emp = employeeService.inactiveEmployee(id);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(emp);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/employee-list")
    public List<EmployeeResponse> getAllEmployeeList(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<EmployeeResponse> response = new ArrayList<EmployeeResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "appUser";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

                    for (Employee employee2 : org.get().getEmployess()) {
                        EmployeeResponse empRes = new EmployeeResponse();
                        empRes.setId(employee2.getId());
                        empRes.setEmployeeId(employee2.getEmployeeId());
                        empRes.setEmpName(employee2.getEmpName());
                        empRes.setCanEdit(rights.get(0));
                        empRes.setCanDelete(rights.get(1));
                        Optional<UserType> usertype = userTypeService.findById(employee2.getEmpCategory());
                        if (usertype.isPresent()) {
                            empRes.setEmpCategory(usertype.get().getUserType());
                        } else {
                            empRes.setEmpCategory("not found!");
                        }
                        Optional<Employee> reporId = employeeService.findByEmployeeId(employee2.getReportingId());
                        if (reporId.isPresent()) {
                            empRes.setReportingName(reporId.get().getEmpName());
                        } else {
                            empRes.setReportingName("not found!");
                        }
                        empRes.setEmpAddress(employee2.getEmpAddress());
                        empRes.setEmpImage(employee2.getEmpImage());
                        empRes.setEmpPhone(employee2.getEmpPhone());
                        empRes.setPassword(employee2.getPassword());
                        response.add(empRes);
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
