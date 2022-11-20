package com.inovex.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AssignRouteViewController {
	
	@GetMapping("/assignRoute")
	public String viewAssignRoute() {
		return "admin-pages/assignRoute/assignRoute-home";
		
	}
	
	@GetMapping("/add-assignRoute")
	public String addAssignRoute() {
		return "admin-pages/assignRoute/assignRoute-add-form";
		
		
	}
	
	@GetMapping("/edit-assignRoute")
	public String editAssignRoute() {
		return "admin-pages/assignRoute/assignRoute-edit-form";
		
		
	}
	

}
