package com.inovex.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")

public class CurrentLocationViewController {
	
	@GetMapping("/currentLocation")
	public String viewCurrentLocation() {
		
		return "admin-pages/currentLocation/currentLocation-home";
	}

}
