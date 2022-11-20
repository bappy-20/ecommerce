package com.inovex.main.controller.rest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.inovex.main.entity.MonthlyOrderTarget;
import com.inovex.main.entity.MonthlyTarget;
import com.inovex.main.entity.Price;
import com.inovex.main.entity.User;
import com.inovex.main.entity.VisitTarget;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.TargetOrderResponse;
import com.inovex.main.json.response.TargetResponse;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.MonthlyOrderTargetService;
import com.inovex.main.service.MonthlyTargetService;
import com.inovex.main.service.PriceService;
import com.inovex.main.service.ProductCategoryService;
import com.inovex.main.service.ProductModelService;
import com.inovex.main.service.UserService;
import com.inovex.main.service.VisitTargetService;

@RestController
@RequestMapping("/api")
public class MonthlyTargetRestController {
	@Autowired
	MonthlyTargetService monthlyTargetService;
	@Autowired
	MonthlyOrderTargetService monthlyOrderTargetService;
	@Autowired
	ProductModelService productService;
	@Autowired
	EmployeeService empService;
	@Autowired
	ProductCategoryService productcatService;
	@Autowired
	VisitTargetService visitTargetService;
	@Autowired
	OrganizationRepository orgRepo;
	@Autowired
	PriceService priceService;
	@Autowired
	UserService userService;

	@GetMapping("/monthly-Target1/{id}")
	public List<TargetResponse> getAllMonthlyTargets(@PathVariable String id, HttpServletRequest request) {
		List<MonthlyTarget> monthlyTarget = new ArrayList<MonthlyTarget>();
		List<TargetResponse> responseList = new ArrayList<TargetResponse>();
		try {
			if (request.getSession().getAttribute("orgId") != null) {
				long orgId = (long) request.getSession().getAttribute("orgId");
				monthlyTarget = monthlyTargetService.findAllOfCurrentMonth(id, orgId);
				for (MonthlyTarget monthlyTarget2 : monthlyTarget) {
					TargetResponse tr = new TargetResponse();
					tr.setId(monthlyTarget2.getId());
					tr.setEmpId(id);	Optional<User> user = userService.findByUsername(id);
					if (user.isPresent()) {
						tr.setEmpName(user.get().getFullName());						
					} else {
						tr.setEmpName("Not Found");
					}
					tr.setProductName(monthlyTarget2.getProductName());
					tr.setCategory(monthlyTarget2.getCategory());
					tr.setTargetquantity(monthlyTarget2.getQuantity());
					tr.setTargetTotalValue(monthlyTarget2.getTotalValue());
					tr.setTargetMonth(monthlyTarget2.getTargetMonth().toString());
					String date = monthlyTarget2.getTargetMonth().toString();
					int firstSlash = date.indexOf("-");
					int secondSlash = date.indexOf("-", firstSlash + 1);
					int month =Integer.parseInt (date.substring(firstSlash+1, secondSlash));
					//System.out.println(month);
					  switch(month){ 
					    case 1: tr.setTargetMonth("January");  
					    break;    
					    case 2: tr.setTargetMonth("February");  
					    break;    
					    case 3: tr.setTargetMonth("March");  
					    break;    
					    case 4: tr.setTargetMonth("April");  
					    break;    
					    case 5: tr.setTargetMonth("May");  
					    break;    
					    case 6: tr.setTargetMonth("June");  
					    break;    
					    case 7: tr.setTargetMonth("July");  
					    break;    
					    case 8: tr.setTargetMonth("August");  
					    break;    
					    case 9: tr.setTargetMonth("September"); 
					    break;    
					    case 10: tr.setTargetMonth("October"); 
					    break;    
					    case 11: tr.setTargetMonth("November"); 
					    break;    
					    case 12: tr.setTargetMonth("December");
					    break;    
					    default:System.out.println("Invalid Month!");    
					    }
					
					responseList.add(tr);
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

//	@GetMapping("/monthly-deliveryman-order-target")
//	public List<MonthlyOrderTarget> getAllMonthlyOrderTarget(HttpServletRequest request) {
//		List<MonthlyOrderTarget> responseList = new ArrayList<MonthlyOrderTarget>();
//		try {
//			responseList = monthlyOrderTargetService.findAllByCurMonth(request);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return responseList;
//	}
	
	@GetMapping("/monthly-deliveryman-order-target")
	public List<TargetOrderResponse> getAllMonthlyOrderTarget(HttpServletRequest request) {
		List<MonthlyOrderTarget> monthlyOrderTarget = new ArrayList<MonthlyOrderTarget>();
		List<TargetOrderResponse> responseList = new ArrayList<TargetOrderResponse>();
		try {
			monthlyOrderTarget = monthlyOrderTargetService.findAllByCurMonth(request);
			for (MonthlyOrderTarget monthlyOrderTarget1 : monthlyOrderTarget) {
				TargetOrderResponse res = new TargetOrderResponse();
				res.setId(monthlyOrderTarget1.getId());
				res.setEmpId(monthlyOrderTarget1.getEmpId());
				res.setEmpName(monthlyOrderTarget1.getEmpName());
				res.setOrderQuantity(monthlyOrderTarget1.getOrderQuantity());
				res.setOrderValue(monthlyOrderTarget1.getOrderValue());
				
//				String date = monthlyOrderTarget1.getTargetMonth().toString();
//				int firstSlash = date.indexOf("-");
//				int secondSlash = date.indexOf("-", firstSlash + 1);
//				int month =Integer.parseInt (date.substring(firstSlash+1, secondSlash));
//				//System.out.println(month);
//				  switch(month){ 
//				    case 1: res.setTargetMonth("January");  
//				    break;    
//				    case 2: res.setTargetMonth("February");  
//				    break;    
//				    case 3: res.setTargetMonth("March");  
//				    break;    
//				    case 4: res.setTargetMonth("April");  
//				    break;    
//				    case 5: res.setTargetMonth("May");  
//				    break;    
//				    case 6: res.setTargetMonth("June");  
//				    break;    
//				    case 7: res.setTargetMonth("July");  
//				    break;    
//				    case 8: res.setTargetMonth("August");  
//				    break;    
//				    case 9: res.setTargetMonth("September"); 
//				    break;    
//				    case 10: res.setTargetMonth("October"); 
//				    break;    
//				    case 11: res.setTargetMonth("November"); 
//				    break;    
//				    case 12: res.setTargetMonth("December");
//				    break;    
//				    default:System.out.println("Invalid Month!");    
//				    }	
				
				String date = monthlyOrderTarget1.getTargetMonth().toString();
				int firstSlash = date.indexOf("-");
				int secondSlash = date.indexOf("-", firstSlash + 1);
				int month =Integer.parseInt (date.substring(firstSlash+1, secondSlash));
				//System.out.println(month);
				  switch(month){ 
				    case 1: res.setTargetMonth("January");  
				    break;    
				    case 2: res.setTargetMonth("February");  
				    break;    
				    case 3: res.setTargetMonth("March");  
				    break;    
				    case 4: res.setTargetMonth("April");  
				    break;    
				    case 5: res.setTargetMonth("May");  
				    break;    
				    case 6: res.setTargetMonth("June");  
				    break;    
				    case 7: res.setTargetMonth("July");  
				    break;    
				    case 8: res.setTargetMonth("August");  
				    break;    
				    case 9: res.setTargetMonth("September"); 
				    break;    
				    case 10: res.setTargetMonth("October"); 
				    break;    
				    case 11: res.setTargetMonth("November"); 
				    break;    
				    case 12: res.setTargetMonth("December");
				    break;    
				    default:System.out.println("Invalid Month!");    
				    }	
				  responseList.add(res);							
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

	

	@GetMapping("/get-order-target-datewise/{targetMonth}")
	public List<MonthlyOrderTarget> getAllMonthlyOrderTargetByDate(@PathVariable Date targetMonth,
			HttpServletRequest request) {
		List<MonthlyOrderTarget> responseList = new ArrayList<MonthlyOrderTarget>();
		try {
			responseList = monthlyOrderTargetService.findAllByMonth(targetMonth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

	@GetMapping("/monthly-Target-summery")
	public List<TargetResponse> getAllMonthlyTargetsSummery(HttpServletRequest request) {
		List<Object> monthlyTarget = new ArrayList<Object>();
		List<TargetResponse> responseList = new ArrayList<TargetResponse>();
		try {
			monthlyTarget = monthlyTargetService.findAllEmployeeOfCurrentMonth(request);
			Iterator itr = monthlyTarget.iterator();
			while (itr.hasNext()) {
				TargetResponse tr = new TargetResponse();
				Object[] obj = (Object[]) itr.next();
				tr.setEmpId(String.valueOf(obj[0]));
//				Optional<Employee> emp = empService.findByEmployeeId(String.valueOf(obj[0]));
				///long userId = Long.parseLong(String.valueOf(obj[0])); 
				String userName = String.valueOf(obj[0]);
				//Optional<User> emp = userService.findUserById(userId); // empService.findByEmployeeId(String.valueOf(obj[0]));
				Optional<User> emp = userService.findByUsername(userName);
				//System.out.println(userId);
				if (emp.isPresent()) {
					tr.setEmpName(emp.get().getFullName());
					tr.setId(emp.get().getId());
				} else {
					tr.setEmpName("employee not found");
				}
				// full file name
				int iend = String.valueOf(obj[1]).indexOf(".");
				String subString;
				if (iend != -1) {
					subString = String.valueOf(obj[1]).substring(0, iend);
					tr.setTargetquantity(subString);// this will give abc
				} else {
					tr.setTargetquantity("0");
				}

				tr.setTargetTotalValue(String.valueOf(obj[2]));
				//tr.setTargetMonth(String.valueOf(obj[3]));
				String date = String.valueOf(obj[3]);
				int firstSlash = date.indexOf("-");
				int secondSlash = date.indexOf("-", firstSlash + 1);
				int month =Integer.parseInt (date.substring(firstSlash+1, secondSlash));
				//System.out.println(month);
				  switch(month){ 
				    case 1: tr.setTargetMonth("January");  
				    break;    
				    case 2: tr.setTargetMonth("February");  
				    break;    
				    case 3: tr.setTargetMonth("March");  
				    break;    
				    case 4: tr.setTargetMonth("April");  
				    break;    
				    case 5: tr.setTargetMonth("May");  
				    break;    
				    case 6: tr.setTargetMonth("June");  
				    break;    
				    case 7: tr.setTargetMonth("July");  
				    break;    
				    case 8: tr.setTargetMonth("August");  
				    break;    
				    case 9: tr.setTargetMonth("September"); 
				    break;    
				    case 10: tr.setTargetMonth("October"); 
				    break;    
				    case 11: tr.setTargetMonth("November"); 
				    break;    
				    case 12: tr.setTargetMonth("December");
				    break;    
				    default:System.out.println("Invalid Month!");    
				    }    
				responseList.add(tr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

	@PostMapping("/monthly-Target")
	public ResponseData createMonthlyTarget(@RequestBody MonthlyTarget monthlyTarget, HttpServletRequest request) {
		ResponseData responseData = new ResponseData();

		try {
			MonthlyTarget mnt = monthlyTargetService.save(monthlyTarget, request);
			responseData.setData(mnt);
			responseData.setStatusCode(201);
			responseData.setMessage("MonthlyTarget created successfully");

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

	@GetMapping("/monthly-Target/{id}")
	public ResponseData getMonthlyTarget(@PathVariable Long id) {
		ResponseData responseData = new ResponseData();
		try {
			MonthlyTarget mnt = monthlyTargetService.getMonthlyTarget(id);
			responseData.setData(mnt);
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

	@DeleteMapping("/monthly-Target/{id}")
	public ResponseData deleteMonthlyTarget(@PathVariable Long id, HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {
			monthlyTargetService.deleteById(id, request);
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

	@PutMapping("/monthly-Target/{id}")
	public ResponseData updateMonthlyTarget(@RequestBody MonthlyTarget monthlyTarget, @PathVariable Long id,
			HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {
			MonthlyTarget mnt = monthlyTargetService.update(monthlyTarget, id, request);
			responseData.setStatusCode(200);
			responseData.setMessage("update successfully");
			responseData.setData(mnt);
			return responseData;
		} catch (Exception e) {
			responseData.setData(null);
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());
			return responseData;
		}
	}

	@RequestMapping(value = "/get-product-price", method = RequestMethod.POST)
	public String updateSalary(@RequestParam("name") String name, @RequestParam("pk") long pk,
			@RequestParam("value") String value) {
		System.out.println(" product pk id " + pk);
		Optional<Price> sl = priceService.findByProductId(pk);
		long price = 0;
		if (sl.isPresent()) {
			price = sl.get().getProductPrice() * Long.parseLong(value);
		}

		return Long.toString(price);
	}

	@RequestMapping(value = "/set-product-target/{empId}/{targetMonth1}/{visitNumber}", method = RequestMethod.POST)
	public JSONObject setProductTarget(@PathVariable String empId, @PathVariable Date targetMonth1,
			@PathVariable String visitNumber, @RequestBody List<MonthlyTarget> target, HttpServletRequest request)
			throws ParseException {
		if (request.getSession().getAttribute("orgId") != null) {
			long id = (long) request.getSession().getAttribute("orgId");
			Optional<VisitTarget> rc = visitTargetService.findAllOfCurrentMonth(empId, targetMonth1);
			if (rc.isPresent()) {
				rc.get().setVisitNumber(visitNumber);
				rc.get().setTargetMonth(targetMonth1);
				rc.get().setUpdatedAt(new Date());
				visitTargetService.save(rc.get(), request);
			} else {
				VisitTarget visit = new VisitTarget();
				visit.setEmpId(empId);
				visit.setCreatedAt(new Date());
				visit.setUpdatedAt(new Date());
				visit.setCreatedBy(1);
				visit.setActive(true);
				visit.setVisitNumber(visitNumber);
				visit.setTargetMonth(targetMonth1);
				visitTargetService.save(visit, request);
			}

			Set<MonthlyTarget> allTarget = new HashSet<MonthlyTarget>();
			for (MonthlyTarget tr : target) {
				MonthlyTarget mt = new MonthlyTarget();
				Optional<MonthlyTarget> check = monthlyTargetService.findExistorNotOfCurrentMonth(tr.getProductName(),
						tr.getEmpId(), tr.getTargetMonth());
				if (check.isPresent()) {
					check.get().setQuantity(tr.getQuantity());
					check.get().setTotalValue(tr.getTotalValue());
					check.get().setUpdatedAt(new Date());
					allTarget.add(check.get());
				} else {
					mt.setCreatedAt(new Date());
					mt.setUpdatedAt(new Date());
					mt.setCreatedBy(1);
					mt.setActive(true);
					mt.setEmpId(tr.getEmpId());
					mt.setProductName(tr.getProductName());
					mt.setCategory(tr.getCategory());
					mt.setQuantity(tr.getQuantity());
					mt.setTotalValue(tr.getTotalValue());
					mt.setTargetMonth(tr.getTargetMonth());
					mt.setOrgId(id);
					allTarget.add(mt);
				}

			}
			monthlyTargetService.saveAll(allTarget, request);
		}

		JSONObject responseObj = new JSONObject();
		responseObj.put("result", "Product target setup successful");
		return responseObj;
	}

	@RequestMapping(value = "/set-delivery-target", method = RequestMethod.POST)
	public JSONObject setOrderDeliveryTarget(@RequestBody List<MonthlyOrderTarget> target, HttpServletRequest request)
			throws ParseException {

		monthlyOrderTargetService.saveAll(target, request);

		JSONObject responseObj = new JSONObject();
		responseObj.put("result", "Product target setup successful");
		return responseObj;
	}

	@DeleteMapping("/delete-monthly-order-target/{id}")
	public ResponseData deleteMonthlyorderTarget(@PathVariable Long id,HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {
			monthlyOrderTargetService.deleteById(id,request);
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
	
	@GetMapping("/monthy-target-summary8/{targetMonth}")
	public List<TargetResponse>getAllMOnthlyTarge8(HttpServletRequest request, @PathVariable String targetMonth){
	
		List<Object> monthlyTarget = new ArrayList<Object>();
		List<TargetResponse> responseList = new ArrayList<TargetResponse>();
		try {
			//System.out.println(targetMonth);
			monthlyTarget = monthlyTargetService.findAllEMployeeOfSelectedMonth7(request, targetMonth);
			Iterator itr = monthlyTarget.iterator();
			while (itr.hasNext()) {
				TargetResponse tr = new TargetResponse();
				Object[] obj = (Object[]) itr.next();
				tr.setEmpId(String.valueOf(obj[0]));
				Optional<Employee> emp = empService.findByEmployeeId(String.valueOf(obj[0]));
				if (emp.isPresent()) {
					tr.setEmpName(emp.get().getEmpName());
				} else {
					tr.setEmpName("employee not found");
				}
				// full file name
				int iend = String.valueOf(obj[1]).indexOf(".");
				String subString;
				if (iend != -1) {
					subString = String.valueOf(obj[1]).substring(0, iend);
					tr.setTargetquantity(subString);// this will give abc
				} else {
					tr.setTargetquantity("0");
				}

				tr.setTargetTotalValue(String.valueOf(obj[2]));
				tr.setTargetMonth(String.valueOf(obj[3]));
				responseList.add(tr);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return responseList;	
	}	
	
	@GetMapping("/get-order-target-datewise2/{targetMonth}")
	public List<TargetOrderResponse>getAllMonthlyOrderTargetForDeliveryMan(HttpServletRequest request, @PathVariable String targetMonth){

		List<Object> monthlyOrderTarget = new ArrayList<Object>();
		List<TargetOrderResponse> responseList = new ArrayList<TargetOrderResponse>();
		try {
			//System.out.println(targetMonth);
			monthlyOrderTarget = monthlyOrderTargetService.findAllDeliveryEMployeeOfSelectedMonth7(request, targetMonth);
			Iterator itr = monthlyOrderTarget.iterator();
			while (itr.hasNext()) {
				TargetOrderResponse tor = new TargetOrderResponse();
				Object[] obj = (Object[]) itr.next();
				tor.setEmpId(String.valueOf(obj[0]));
				tor.setEmpName(String.valueOf(obj[1]));
				tor.setOrderQuantity(String.valueOf(obj[2]));
				tor.setOrderValue(String.valueOf(obj[3]));
				tor.setTargetMonth(String.valueOf(obj[4]));
				responseList.add(tor);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return responseList;	
	}	
	
}
