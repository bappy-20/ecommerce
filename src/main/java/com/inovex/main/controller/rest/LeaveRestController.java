package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.inovex.main.entity.Employee;
import com.inovex.main.entity.LeaveModel;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.LeaveResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.LeaveService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.UserService;

@RestController
@RequestMapping("/api")
public class LeaveRestController {

    @Autowired
    LeaveService leaveService;
    @Autowired
    EmployeeService empService;
    @Autowired
    MenuService menuService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    UserService userService;

    @GetMapping("/leave")
    public List<LeaveModel> getAllLeaves(HttpServletRequest request) {

        List<LeaveModel> leaveList = new ArrayList<LeaveModel>();

        try {

            leaveList = leaveService.findAll();
        }

        catch (Exception e) {

        }
        return leaveList;
    }

    @PostMapping("/save-leaveModel")
    public ResponseData createLeaveModel(@RequestBody LeaveModel leave, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            LeaveModel lev = leaveService.save1(leave, request);
            responseData.setData(lev);
            responseData.setStatusCode(201);
            responseData.setMessage("leave created successfully");

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

    @GetMapping("/get-leave/{id}")
    public ResponseData getleave(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<LeaveModel> lev = leaveService.findById(id);
            responseData.setData(lev.get());
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

    @DeleteMapping("/delete-leave/{id}")
    public ResponseData deleteLeave(@PathVariable Long id,HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            leaveService.deleteById(id,request);
            responseData.setStatusCode(204);
            responseData.setMessage("delete successfully");
            return responseData;
        } catch (Exception e) {
        	e.printStackTrace();
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    @PutMapping("/update-leave/{id}")
    public ResponseData updateLeave(@RequestBody LeaveModel leave, @PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            LeaveModel lev = leaveService.update(leave, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(lev);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/leave-ReportOfPending")
    public List<LeaveModel> getPendingLeave() {
        ResponseData responseData = new ResponseData();
        List<LeaveModel> lev = new ArrayList<>();
        try {
            lev = leaveService.getPendingLeaveMethod();
        } catch (Exception e) {
            responseData.setData(null);

            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
        }
        return lev;
    }

    @PutMapping("/leave-ApprovedStatus/{id}")
    public ResponseData approveLeaveStatus(@PathVariable Long id) {

        ResponseData responseData = new ResponseData();
        try {
            LeaveModel lev = leaveService.approveStatus(id);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(lev);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/leave-pendingReport1")
    public List<LeaveResponse> getPendingLeaveReport() {
        List<LeaveResponse> responseList = new ArrayList<>();

        List<LeaveModel> lev = new ArrayList<>();
        try {
            lev = leaveService.getAllPendingLeave1();

            for (LeaveModel leaveModel : lev) {
                LeaveResponse response = new LeaveResponse();
                Optional<Employee> emp = empService.findByEmployeeId(leaveModel.getEmployeeId());
                if (emp.isPresent()) {
                    response.setId(leaveModel.getId());
                    response.setEmployeeId(leaveModel.getEmployeeId());
                    response.setEmployeeName(emp.get().getEmpName());
                    response.setLeaveType(leaveModel.getLeaveType());
                    response.setFromDate(leaveModel.getFromDate());
                    response.setToDate(leaveModel.getToDate());
                    response.setComment(leaveModel.getComment());
                    response.setDayCount(leaveModel.getDayCount());
                    response.setStatus(leaveModel.getStatus());
                    responseList.add(response);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return responseList;
    }

    @GetMapping("/leave-approved")
    public List<LeaveResponse> getApprovedLeaveReport() {
        List<LeaveResponse> responseList = new ArrayList<>();

        List<LeaveModel> lev = new ArrayList<>();
        try {
            lev = leaveService.getApprovedLeave();

            for (LeaveModel leaveModel : lev) {
                LeaveResponse response = new LeaveResponse();
                Optional<Employee> emp = empService.findByEmployeeId(leaveModel.getEmployeeId());
                if (emp.isPresent()) {
                    response.setId(leaveModel.getId());
                    response.setEmployeeId(leaveModel.getEmployeeId());
                    response.setEmployeeName(emp.get().getEmpName());
                    response.setLeaveType(leaveModel.getLeaveType());
                    response.setFromDate(leaveModel.getFromDate());
                    response.setToDate(leaveModel.getToDate());
                    response.setComment(leaveModel.getComment());
                    response.setDayCount(leaveModel.getDayCount());
                    response.setStatus(leaveModel.getStatus());
                    responseList.add(response);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return responseList;
    }

    @PutMapping("/reject-leave/{id}")
    public ResponseData rejectLeave(@PathVariable Long id) {

        ResponseData responseData = new ResponseData();
        try {
            LeaveModel lev = leaveService.rejectLave(id);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(lev);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/leave-list-role")
    public List<LeaveResponse> getAllAreas(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        Set<LeaveModel> list = new HashSet<>();
        List<LeaveResponse> response = new ArrayList<LeaveResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "leave";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    list = org.get().getLeaveModel();
                    for (LeaveModel mn : list) {
                        LeaveResponse res = new LeaveResponse();
                        res.setId(mn.getId());
                        Optional<User> user = userService.findByUsername(mn.getEmployeeId());;
						if (user.isPresent()) {
							res.setEmployeeId(user.get().getUsername());
							//res.setEmployeeName(user.get().getFullName());
						} else {
							res.setEmployeeId("not found");
							//res.setEmployeeName("not found");
						}
                        res.setLeaveType(mn.getLeaveType());
                        res.setFromDate(mn.getFromDate());
                        res.setToDate(mn.getToDate());
                        res.setDayCount(mn.getDayCount());
                        res.setComment(mn.getComment());
                        res.setStatus(mn.getStatus());
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

    @GetMapping("/leave-pending-list")
    public List<LeaveResponse> getLeavePendinListRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<LeaveResponse> response = new ArrayList<LeaveResponse>();
        Set<LeaveModel> list = new HashSet<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "pendingLeave";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    list = org.get().getLeaveModel().stream().filter(p -> p.getStatus().equals("Pending"))
                            .collect(Collectors.toSet());
                    for (LeaveModel mn : list) {
                        LeaveResponse res = new LeaveResponse();
                        res.setId(mn.getId());
                       // res.setEmployeeId(mn.getEmployeeId());
                      ///  long userId = Long.parseLong(mn.getEmployeeId());
                        
						Optional<User> user = userService.findByUsername(mn.getEmployeeId());
						//findUserById(userId);
						if (user.isPresent()) {
							res.setEmployeeId(user.get().getUsername());
							res.setEmployeeName(user.get().getFullName());
						} else {
							res.setEmployeeId("not found");
							res.setEmployeeName("not found");
						}
                        res.setLeaveType(mn.getLeaveType());
                        res.setFromDate(mn.getFromDate());
                        res.setToDate(mn.getToDate());
                        res.setDayCount(mn.getDayCount());
                        res.setComment(mn.getComment());
                        res.setStatus(mn.getStatus());
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

    @GetMapping("/leave-approved-list")
    public List<LeaveResponse> getLeaveApprovedListRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        Set<LeaveModel> list = new HashSet<>();
        List<LeaveResponse> response = new ArrayList<LeaveResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "approvedLeave";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    list = org.get().getLeaveModel().stream().filter(p -> p.getStatus().equals("Approved"))
                            .collect(Collectors.toSet());
                    for (LeaveModel mn : list) {
                        LeaveResponse res = new LeaveResponse();
                        res.setId(mn.getId());
                       // res.setEmployeeId(mn.getEmployeeId());
                       // long userId = Long.parseLong(mn.getEmployeeId());
						//Optional<User> user = userService.findUserById(userId);
                        Optional<User> user = userService.findByUsername(mn.getEmployeeId());
						if (user.isPresent()) {
							res.setEmployeeId(user.get().getUsername());
							res.setEmployeeName(user.get().getFullName());
						} else {
							res.setEmployeeId("not found");
							res.setEmployeeName("not found");
						}
                        res.setLeaveType(mn.getLeaveType());
                        res.setFromDate(mn.getFromDate());
                        res.setToDate(mn.getToDate());
                        res.setDayCount(mn.getDayCount());
                        res.setComment(mn.getComment());
                        res.setStatus(mn.getStatus());
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
