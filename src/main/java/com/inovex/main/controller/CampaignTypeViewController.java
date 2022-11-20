package com.inovex.main.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class CampaignTypeViewController {
	 @Value("${base.url}")
	 private String baseurl;
	 
	 @GetMapping("/campaign-type")
	    public ModelAndView viewCampaignType(HttpServletRequest request, Model model) {
	        ModelAndView mv = new ModelAndView();
	        if (request.getSession().getAttribute("orgId") == null) {
	            mv.setViewName("redirect:/login");
	        } else {
	            model.addAttribute("baseurl", baseurl);
	            mv.setViewName("admin-pages/campaigntype/campaigntype-home");
	        }
	        return mv;
	    }

	    @GetMapping("/add-campaign-type")
	    public ModelAndView addCampaignType(HttpServletRequest request, Model model) {
	        ModelAndView mv = new ModelAndView();
	        if (request.getSession().getAttribute("orgId") == null) {
	            mv.setViewName("redirect:/login");
	        } else {
	            model.addAttribute("baseurl", baseurl);
	            mv.setViewName("admin-pages/campaigntype/campaigntype-add-form");
	        }
	        return mv;
	    }

	    @GetMapping("/edit-campaign-type/{id}")
	    public ModelAndView editCampaignType(HttpServletRequest request, @PathVariable long id, Model model) {
	        ModelAndView mv = new ModelAndView();
	        if (request.getSession().getAttribute("orgId") == null) {
	            mv.setViewName("redirect:/login");
	        } else {
	            model.addAttribute("id", id);
	            model.addAttribute("baseurl", baseurl);
	            mv.setViewName("admin-pages/campaigntype/campaigntype-edit-form");
	        }
	        return mv;
	    }

}
