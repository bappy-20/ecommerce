package com.inovex.main.controller.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.inovex.main.entity.Expense;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.ExpenseResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.ExpenseService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.UserService;

@RestController
@RequestMapping("/api")
public class ExpenseRestController {
    @Autowired
    ExpenseService expenseService;
    @Autowired
    EmployeeService empService;
    @Autowired
    OrganizationService orgService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;
    @Autowired
    UserService userService;

    @GetMapping("/expense-ReportByExpTypeAndDateRange/{expenseType}/{date1}/{date2}")
    public List<ExpenseResponse> getExpenseReportByExpTypeAndDateRange(@PathVariable String expenseType,
            @PathVariable String date1, @PathVariable String date2) {

        List<Expense> expenseList = new ArrayList<>();
        List<ExpenseResponse> expenseResList = new ArrayList<ExpenseResponse>();

        try {
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = df2.parse(date1);
            Date enDate = df2.parse(date2);
//            System.out.println(stDate);
//            System.out.println(enDate);
//            System.out.println(expenseType);
            expenseList = expenseService.getExpRptByExpTypeAndDateRange(expenseType, stDate, enDate);
            for (Expense exp : expenseList) {
                ExpenseResponse res = new ExpenseResponse();
                res.setId(exp.getId());
                res.setAmount(exp.getAmount());
                res.setApprovedAmount(exp.getApprovedAmount());
                String expenseBy = exp.getExpenseBy();
               // long empId = Long.parseLong(expenseBy);
                Optional<User> emp = userService.findByUsername(expenseBy);
                if (emp.isPresent()) {
                	res.setExpenseBy(emp.get().getFullName());
                } else {
                	res.setExpenseBy("Employee Not found");
                	}
                res.setAttachment(exp.getAttachment());
                res.setExpenseType(exp.getExpenseType());
                res.setNote(exp.getNote());
                if (exp.getStatus() == 1) {
                    res.setStatus("Approved");
                } else {
                    res.setStatus("Pending");
                }
                long approvedBy = exp.getApprovedBy();
                //System.out.println(approvedBy);

                if (exp.getApprovedBy() > 0) {
                	  Optional<User> user2 = userService.findUserById(approvedBy);
                	  if (user2.isPresent()) {
						res.setApprovedBy(user2.get().getFullName());
					} else {
						res.setApprovedBy("Employye Not Found");

					}
                }

                expenseResList.add(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenseResList;
    }

    @GetMapping("/expense-ReportOfApprovedByDateRange/{date1}/{date2}")
    public List<ExpenseResponse> getApprovedExpenseReportByDateRange(@PathVariable String date1,
            @PathVariable String date2, HttpServletRequest request) {
        Set<Expense> expenseList = new HashSet<>();
        List<ExpenseResponse> expenseResList = new ArrayList<ExpenseResponse>();
        List<Expense> explist = new ArrayList<Expense>();
       // Long orgId = Long.parseLong("295");
       // Optional<Organization> org = orgRepo.findById(orgId);
        
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
       
        if (org.isPresent()) {
            try {
                DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                Date stDate = df2.parse(date1);
                Date enDate = df2.parse(date2);
                expenseList = org.get().getExpense();
                explist = expenseList.stream()
                        .filter(p -> p.getCreatedAt().compareTo(stDate) >= 0 && p.getCreatedAt().compareTo(enDate) <= 0)
                        .collect(Collectors.toList());
                
//                explist = expenseList.stream()
//                      .filter(p -> p.getCreatedAt().after(stDate)  && p.getCreatedAt().before(enDate))
//                      .collect(Collectors.toList());
               // explist = expenseService.findAllApprovedExpense();
                for (Expense exp : explist) {
                    ExpenseResponse res = new ExpenseResponse();
                    System.out.println(exp.getId());
                    res.setId(exp.getId());
                    res.setAmount(exp.getAmount());
                    res.setApprovedAmount(exp.getApprovedAmount());
                    String expenseBy = exp.getExpenseBy();
//                    long empId = Long.parseLong(expenseBy);
                    Optional<User> emp = userService.findByUsername(expenseBy);
                    if (emp.isPresent()) {
                    	res.setExpenseBy(emp.get().getFullName());
                    } else {
                    	res.setExpenseBy("Employee Not found");
                    	}
                    res.setAttachment(exp.getAttachment());
                    res.setExpenseType(exp.getExpenseType());
                    res.setNote(exp.getNote());
                    if (exp.getStatus() == 1) {
                        res.setStatus("Approved");
                    } else {
                        res.setStatus("Pending");
                    }

                    long approvedBy = exp.getApprovedBy();
                  //  System.out.println(approvedBy);
                    if (exp.getApprovedBy() > 0) {
                    	  Optional<User> user2 = userService.findUserById(approvedBy);
                    	  if (user2.isPresent()) {
							res.setApprovedBy(user2.get().getFullName());
						} else {
							res.setApprovedBy("Employye Not Found");

						}
                    }

                    expenseResList.add(res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
        return expenseResList;
    }

    @GetMapping("/expense-ReportOfPendingByDateRange/{date1}/{date2}")
    public List<ExpenseResponse> getPendingExpenseReportByDateRange(@PathVariable String date1,
            @PathVariable String date2,HttpServletRequest request) {
        Set<Expense> expenseList = new HashSet<>();
        List<ExpenseResponse> expenseResList = new ArrayList<ExpenseResponse>();
        List<Expense> explist = new ArrayList<Expense>();
//        Long orgId = Long.parseLong("295");
//        Optional<Organization> org = orgRepo.findById(orgId);
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
       
        if (org.isPresent()) {
            try {
                DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                Date stDate = df2.parse(date1);
                Date enDate = df2.parse(date2);
                expenseList = org.get().getExpense();
                explist = expenseList.stream()
                        .filter(p -> p.getCreatedAt().compareTo(stDate) >= 0 && p.getCreatedAt().compareTo(enDate) <= 0 && p.getStatus()==0)
                        .collect(Collectors.toList());
                //explist = expenseService.findAllPending();
                //explist = explist.stream().filter(p ->p.getStatus())
                for (Expense exp : explist) {
                    ExpenseResponse res = new ExpenseResponse();
                    res.setId(exp.getId());
                    res.setAmount(exp.getAmount());
                    res.setApprovedAmount(exp.getApprovedAmount());
                    String expenseBy = exp.getExpenseBy();
                   // long empId = Long.parseLong(expenseBy);
                    Optional<User> emp = userService.findByUsername(expenseBy);
                    if (emp.isPresent()) {
                    	res.setExpenseBy(emp.get().getFullName());
                    } else {
                    	res.setExpenseBy("Employee Not found");
                    	}
                    res.setAttachment(exp.getAttachment());
                    res.setExpenseType(exp.getExpenseType());
                    res.setNote(exp.getNote());
                    if (exp.getStatus() == 1) {
                        res.setStatus("Approved");
                    } else {
                        res.setStatus("Pending");
                    }

                    long approvedBy = exp.getApprovedBy();
                  // System.out.println(approvedBy);
                    
                    if (exp.getApprovedBy() > 0) {
                    	  Optional<User> user2 = userService.findUserById(approvedBy);
                    	  if (user2.isPresent()) {
							res.setApprovedBy(user2.get().getFullName());
						} else {
							res.setApprovedBy("Employye Not Found");

						}
                    }
                    expenseResList.add(res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
        return expenseResList;
    }

    @PutMapping("/expense-ApprovedStatus/{id}")
    public ResponseData approveTripStatus(@PathVariable Long id) {

        ResponseData responseData = new ResponseData();
        try {
            Expense ex = expenseService.approveStatus(id);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(ex);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/expense")
    public List<ExpenseResponse> getAllExpenses(HttpServletRequest request) {

        Set<Expense> expenseList = new HashSet<Expense>();
        List<ExpenseResponse> expenseResList = new ArrayList<ExpenseResponse>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                expenseList = org.get().getExpense();
                for (Expense exp : expenseList) {
                    ExpenseResponse res = new ExpenseResponse();
                    res.setId(exp.getId());
                    res.setAmount(exp.getAmount());
                    res.setApprovedAmount(exp.getApprovedAmount());
                    //System.out.println(exp.getExpenseBy());
//                    Optional<Employee> emp = empService.findByEmployeeId(exp.getExpenseBy());
//                    if (emp.isPresent()) {
//                        res.setExpenseBy(emp.get().getEmpName());
//                    } else {
//                        res.setExpenseBy("Not found");
//                    }
                    String expenseBy = exp.getExpenseBy();
                    long empId = Long.parseLong(expenseBy);
                    Optional<User> emp = userService.findUserById(empId);//findByEmployeeId(exp.getExpenseBy());
                    if (emp.isPresent()) {
                    	res.setExpenseBy(emp.get().getFullName());//getEmpName());
                    } else {
                    	res.setExpenseBy("Not found");
                    	}
                    res.setAttachment(exp.getAttachment());
                    res.setExpenseType(exp.getExpenseType());
                    res.setNote(exp.getNote());
                    res.setExpenseDate(exp.getExpenseDate());
                    if (exp.getStatus() == 1) {
                        res.setStatus("Approved");
                    } else {
                        res.setStatus("Pending");
                    }

                    if (exp.getApprovedBy() > 0) {
                        Optional<Employee> emp1 = empService.findById(exp.getApprovedBy());
                        if (emp1.isPresent()) {
                            res.setApprovedBy(emp1.get().getEmpName());
                        } else {
                            res.setApprovedBy("Not found");
                        }
                    } else {
                        res.setApprovedBy("Not found");
                    }
                    

                    expenseResList.add(res);
                }
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return expenseResList;
    }

    @GetMapping("/approved-expense")
    public List<ExpenseResponse> getAllApprovedExpenses(HttpServletRequest request) {

        List<Expense> expenseList = new ArrayList<Expense>();
        List<ExpenseResponse> expenseResList = new ArrayList<ExpenseResponse>();

        try {
            expenseList = expenseService.findAllApprovedExpense();
            for (Expense exp : expenseList) {
                ExpenseResponse res = new ExpenseResponse();
                res.setId(exp.getId());
                res.setAmount(exp.getAmount());
                res.setApprovedAmount(exp.getApprovedAmount());
                Optional<Employee> emp = empService.findByEmployeeId(exp.getExpenseBy());
                if (emp.isPresent()) {
                    res.setExpenseBy(emp.get().getEmpName());
                } else {
                    res.setExpenseBy("Not found");
                }
                res.setAttachment(exp.getAttachment());
                res.setExpenseType(exp.getExpenseType());
                res.setNote(exp.getNote());
                if (exp.getStatus() == 1) {
                    res.setStatus("Approved");
                } else {
                    res.setStatus("Pending");
                }

                if (exp.getApprovedBy() > 0) {
                    Optional<Employee> emp1 = empService.findById(exp.getApprovedBy());
                    if (emp1.isPresent()) {
                        res.setApprovedBy(emp1.get().getEmpName());
                    } else {
                        res.setApprovedBy("Not found");
                    }
                } else {
                    res.setApprovedBy("Not found");
                }

                expenseResList.add(res);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return expenseResList;
    }

    @PostMapping("/save-expense")
    public ResponseData createExpense(@RequestBody Expense expense, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();

        try {

            Expense exp = expenseService.save(expense, request);
            responseData.setData(exp);
            responseData.setStatusCode(201);
            responseData.setMessage("expense created successfully");

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

    @GetMapping("/get-expense/{id}")
    public ResponseData getExpense(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<Expense> ext = expenseService.findById(id);
            responseData.setData(ext.get());
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

    @DeleteMapping("/delete-expense/{id}")
    public ResponseData deleteExpense(@PathVariable Long id, HttpServletRequest request) {

        long orgid = (long) request.getSession().getAttribute("orgId");
        ResponseData responseData = new ResponseData();
        try {
            expenseService.deleteExpenseFromOrg(orgid, id);
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

    @PutMapping("/update-expense/{id}")
    public ResponseData updateExpense(@RequestBody Expense expense, @PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            Expense brd = expenseService.update(expense, id, request);
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

    @GetMapping("/expense-list-role")
    public List<ExpenseResponse> getAllExpenseRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<ExpenseResponse> response = new ArrayList<ExpenseResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "expense";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                   
                    for (Expense exp : org.get().getExpense()) {
                        ExpenseResponse res = new ExpenseResponse();
                        res.setId(exp.getId());
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        res.setAmount(exp.getAmount());
                        res.setApprovedAmount(exp.getApprovedAmount());
                        
//                        String expenseBy = exp.getExpenseBy();
//                        long empId = Long.parseLong(expenseBy);
//                        Optional<User> emp = userService.findUserById(empId);
                        Optional<User> emp = userService.findByUsername(exp.getExpenseBy());
                        if (emp.isPresent()) {
                        	res.setExpenseBy(emp.get().getFullName());
                        } else {
                        	res.setExpenseBy("Employee Not found");
                        	}
                        res.setAttachment(exp.getAttachment());
                        res.setExpenseType(exp.getExpenseType());
                        res.setNote(exp.getNote());
                        if (exp.getStatus() == 1) {
                            res.setStatus("Approved");
                        } else {
                            res.setStatus("Pending");
                        }
                        long approvedBy = exp.getApprovedBy();
                      //  System.out.println(approvedBy);

                        if (exp.getApprovedBy() > 0) {
                        	  Optional<User> user2 = userService.findUserById(approvedBy);
                        	  if (user2.isPresent()) {
								res.setApprovedBy(user2.get().getFullName());
							} else {
								res.setApprovedBy("Employye Not Found");

							}
                        }
                        res.setExpenseDate(exp.getExpenseDate());
                        response.add(res);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @GetMapping("/expense-approve-list-role")
    public List<ExpenseResponse> getAllApprovedExpenseRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<ExpenseResponse> response = new ArrayList<ExpenseResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "approvedExpense";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    //Set<Expense> approvedExpenseList = new  HashSet<Expense>();
                   //approvedExpenseList =  org.get().getExpense().stream().filter(p->p.getStatus().equals(1)).collect(Collectors.toList()));
                
                    for (Expense exp : org.get().getExpense().stream().filter(p->p.getStatus()==1).collect(Collectors.toList())) {
                    	ExpenseResponse res = new ExpenseResponse();
                    	res.setId(exp.getId());
                    	res.setExpenseType(exp.getExpenseType());
                    	res.setAmount(exp.getAmount());
                    	res.setNote(exp.getNote());
                    	String expenseBy = exp.getExpenseBy();
                    	Optional<User> emp = userService.findByUsername(expenseBy); 
                    	if (emp.isPresent()) {
                         	res.setExpenseBy(emp.get().getFullName());
                         } else {
                         	res.setExpenseBy("Employee Not found");
                         	}
                         
                    	if(exp.getStatus() == 1) {
                    		res.setStatus("Approved");
                    	}
                    	else {
                    		res.setStatus("Pending");	
                    	}
                    	
                    	 long approvedBy = exp.getApprovedBy();
                        // System.out.println(approvedBy);

                         if (exp.getApprovedBy() > 0) {
                         	  Optional<User> user2 = userService.findUserById(approvedBy);
                         	  if (user2.isPresent()) {
 								res.setApprovedBy(user2.get().getFullName());
 							} else {
 								res.setApprovedBy("Employye Not Found");

 							}
                         }
                    	res.setApprovedAmount(exp.getApprovedAmount());
                    	res.setAttachment(exp.getAttachment());
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