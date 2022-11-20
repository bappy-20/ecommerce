package com.inovex.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")

public class SecondaryInventoryViewController {
	
	@GetMapping("/secondaryInventory")
	public String viewSecondaryInventory() {
		
		return "admin-pages/secondaryInventory/secondaryInventory-home";
		
	}
	
	@GetMapping("/add-secondaryInventory")
	public String adddSecondaryInventory() {
		
		return "admin-pages/secondaryInventory/secondaryInventory-add-form";
		
	}
	
	@GetMapping("/edit-secondaryInventory")
	public String editSecondaryInventory() {
		
		return "admin-pages/secondaryInventory/secondaryInventory-edit-form";
		
	}

}
