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

import com.inovex.main.entity.Employee;
import com.inovex.main.entity.Market;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.Retail;
import com.inovex.main.entity.RetailType;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.RetailResponse;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.MarketService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.RetailService;
import com.inovex.main.service.RetailTypeService;
import com.inovex.main.service.UserService;

@RestController
@RequestMapping("/api")
public class RetailRestController {

    @Autowired
    RetailService retailService;
    @Autowired
    RetailTypeService retailTypeService;
    @Autowired
    MarketService mktService;
    @Autowired
    EmployeeService empService;
    @Autowired
    UserService userService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/retail")
    public List<RetailResponse> getAllRetails(HttpServletRequest request) {
        List<RetailResponse> retailRes = new ArrayList<RetailResponse>();
        Set<Retail> retail = new HashSet<Retail>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                retail = org.get().getRetails();
                for (Retail retail2 : retail) {
                    RetailResponse res = new RetailResponse();
                    res.setId(retail2.getId());
                    res.setRetailName(retail2.getRetailName());
                    res.setRetailAddress(retail2.getRetailAddress());
                    res.setRetailLatLong(retail2.getRetailLat() + "," + retail2.getRetailLong());
                    res.setRetailOwner(retail2.getRetailOwner());
                    res.setRetailPhone(retail2.getRetailPhone());

                    Optional<RetailType> type = retailTypeService.findById(retail2.getRetailType());
                    if (type.isPresent()) {
                        res.setRetailType(type.get().getRetailType());
                    } else {
                        res.setRetailType("not found!");
                    }
                    Optional<Market> mkt = mktService.findById(retail2.getMarketId());
                    if (mkt.isPresent()) {
                        res.setMarketName(mkt.get().getMarketName());
                    } else {
                        res.setMarketName("not found!");
                    }
                    res.setStoreImage1(retail2.getStoreImage1());

                    res.setStatus(retail2.getStatus());

                    Optional<Employee> emp = empService.findByEmployeeId(retail2.getSubmittedBy());
                    if (emp.isPresent()) {
                        res.setSubmittedBy(emp.get().getEmpName());
                    } else {
                        res.setSubmittedBy("not found!");
                    }
                    if (retail2.getApprovedBy() != 0) {
                        Optional<User> user = userService.findUserById(retail2.getApprovedBy());
                        if (user.isPresent()) {
                            res.setApprovedBy(user.get().getFullName());
                        } else {
                            res.setApprovedBy("not found!");
                        }
                    } else {
                        res.setApprovedBy("not found!");
                    }

                    res.setNationalId(retail2.getNationalId());
                    retailRes.add(res);
                }
            }

            catch (Exception e) {
                e.printStackTrace();
            }

        }
        return retailRes;
    }

    @GetMapping("/pending-retail")
    public List<RetailResponse> getAllPendingRetails(HttpServletRequest request) {
        List<RetailResponse> retailRes = new ArrayList<RetailResponse>();
        List<Retail> retail = new ArrayList<Retail>();
        try {
            retail = retailService.findAllPendingRetail();
            for (Retail retail2 : retail) {
                RetailResponse res = new RetailResponse();
                res.setId(retail2.getId());
                res.setRetailName(retail2.getRetailName());
                res.setRetailAddress(retail2.getRetailAddress());
                res.setRetailLatLong(retail2.getRetailLat() + "," + retail2.getRetailLong());
                res.setRetailOwner(retail2.getRetailOwner());
                res.setRetailPhone(retail2.getRetailPhone());

                Optional<RetailType> type = retailTypeService.findById(retail2.getRetailType());
                if (type.isPresent()) {
                    res.setRetailType(type.get().getRetailType());
                } else {
                    res.setRetailType("not found!");
                }
                Optional<Market> mkt = mktService.findById(retail2.getMarketId());
                if (mkt.isPresent()) {
                    res.setMarketName(mkt.get().getMarketName());
                } else {
                    res.setMarketName("not found!");
                }
                res.setStoreImage1(retail2.getStoreImage1());

                res.setStatus(retail2.getStatus());

                Optional<Employee> emp = empService.findByEmployeeId(retail2.getSubmittedBy());
                if (emp.isPresent()) {
                    res.setSubmittedBy(emp.get().getEmpName());
                } else {
                    res.setSubmittedBy("not found!");
                }
                if (retail2.getApprovedBy() != 0) {
                    Optional<User> user = userService.findUserById(retail2.getApprovedBy());
                    if (user.isPresent()) {
                        res.setApprovedBy(user.get().getFullName());
                    } else {
                        res.setApprovedBy("not found!");
                    }
                } else {
                    res.setApprovedBy("not found!");
                }

                res.setNationalId(retail2.getNationalId());
                retailRes.add(res);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return retailRes;
    }

    @PostMapping("/retail")
    public ResponseData createRetail(@RequestBody Retail retail, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            Retail rtl = retailService.save(retail, request);
            responseData.setData(rtl);
            responseData.setStatusCode(201);
            responseData.setMessage("Retail created successfully");

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

    @GetMapping("/retail/{id}")
    public ResponseData getRetail(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Retail trt = retailService.getRetail(id);
            responseData.setData(trt);
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

    @DeleteMapping("/delete-retail/{id}")
    public ResponseData deleteRetail(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            retailService.deleteById(id, request);
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

    @PutMapping("/retail/{id}")
    public ResponseData updateRetail(@RequestBody Retail retail, @PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            Retail rtl = retailService.update(retail, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(rtl);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @PutMapping("/update-pending/{id}")
    public ResponseData approvedRetail(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            Retail rtl = retailService.ApproveRetail(id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(rtl);
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/retail-list-role")
    public List<RetailResponse> getAllRetailRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<RetailResponse> response = new ArrayList<RetailResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "retail";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    /*
                     * for (Menu mn : menu) { RetailResponse res = new RetailResponse();
                     * res.setId(mn.getId()); res.setCanEdit(rights.get(0));
                     * res.setCanDelete(rights.get(1)); response.add(res); }
                     */
                    for (Retail retail2 : org.get().getRetails()) {
                        RetailResponse res = new RetailResponse();
                        res.setId(retail2.getId());
                        res.setRetailName(retail2.getRetailName());
                        res.setRetailAddress(retail2.getRetailAddress());
                        res.setRetailLatLong(retail2.getRetailLat() + "," + retail2.getRetailLong());
                        res.setRetailOwner(retail2.getRetailOwner());
                        res.setRetailPhone(retail2.getRetailPhone());
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        Optional<RetailType> type = retailTypeService.findById(retail2.getRetailType());
                        if (type.isPresent()) {
                            res.setRetailType(type.get().getRetailType());
                        } else {
                            res.setRetailType("not found!");
                        }
                        Optional<Market> mkt = mktService.findById(retail2.getMarketId());
                        if (mkt.isPresent()) {
                            res.setMarketName(mkt.get().getMarketName());
                        } else {
                            res.setMarketName("not found!");
                        }
                        res.setStoreImage1(retail2.getStoreImage1());

                        res.setStatus(retail2.getStatus());

                        Optional<Employee> emp = empService.findByEmployeeId(retail2.getSubmittedBy());
                        if (emp.isPresent()) {
                            res.setSubmittedBy(emp.get().getEmpName());
                        } else {
                            res.setSubmittedBy("not found!");
                        }
                        if (retail2.getApprovedBy() != 0) {
                            Optional<User> user = userService.findUserById(retail2.getApprovedBy());
                            if (user.isPresent()) {
                                res.setApprovedBy(user.get().getFullName());
                            } else {
                                res.setApprovedBy("not found!");
                            }
                        } else {
                            res.setApprovedBy("not found!");
                        }

                        res.setNationalId(retail2.getNationalId());
                        response.add(res);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @GetMapping("/retail-list-role-pending")
    public List<RetailResponse> getAllPendingRetailRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<RetailResponse> response = new ArrayList<RetailResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "pendingRetail";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

                    for (Retail retail2 : org.get().getRetails()) {
                    	
                    	if (retail2.getStatus().equals("Pending")) {
                    		 RetailResponse res = new RetailResponse();
                             res.setId(retail2.getId());
                             res.setRetailName(retail2.getRetailName());
                             res.setRetailAddress(retail2.getRetailAddress());
                             res.setRetailLatLong(retail2.getRetailLat() + "," + retail2.getRetailLong());
                             res.setRetailOwner(retail2.getRetailOwner());
                             res.setRetailPhone(retail2.getRetailPhone());
                             res.setCanEdit(rights.get(0));
                             res.setCanDelete(rights.get(1));
                             Optional<RetailType> type = retailTypeService.findById(retail2.getRetailType());
                             if (type.isPresent()) {
                                 res.setRetailType(type.get().getRetailType());
                             } else {
                                 res.setRetailType("not found!");
                             }
                             Optional<Market> mkt = mktService.findById(retail2.getMarketId());
                             if (mkt.isPresent()) {
                                 res.setMarketName(mkt.get().getMarketName());
                             } else {
                                 res.setMarketName("not found!");
                             }
                             res.setStoreImage1(retail2.getStoreImage1());

                             res.setStatus(retail2.getStatus());

                             Optional<Employee> emp = empService.findByEmployeeId(retail2.getSubmittedBy());
                             if (emp.isPresent()) {
                                 res.setSubmittedBy(emp.get().getEmpName());
                             } else {
                                 res.setSubmittedBy("not found!");
                             }
                             if (retail2.getApprovedBy() != 0) {
                                 Optional<User> user = userService.findUserById(retail2.getApprovedBy());
                                 if (user.isPresent()) {
                                     res.setApprovedBy(user.get().getFullName());
                                 } else {
                                     res.setApprovedBy("not found!");
                                 }
                             } else {
                                 res.setApprovedBy("not found!");
                             }

                             res.setNationalId(retail2.getNationalId());
                             response.add(res);
                    	}
                    	else {
                    		System.out.println("Approved");
                    	}
                   }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
