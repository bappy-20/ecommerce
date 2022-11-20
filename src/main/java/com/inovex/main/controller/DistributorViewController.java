package com.inovex.main.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.DistributorType;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.DistributorTypeService;

@Controller
@RequestMapping("/admin")

public class DistributorViewController {
    @Value("${base.url}")
    private String baseurl;
    
    @Autowired
    DistributorTypeService distTypeService;
    @Autowired
    DistributorService distService;

    @RequestMapping("/distributor")
    public ModelAndView getDistributor(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor/distributor-home");
        }
        return mv;

    }

    @RequestMapping("/add-distributor")
    public ModelAndView addDistributor(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
        	List<DistributorType> distTypes = distTypeService.findAll();
        	mv.addObject("distTypes", distTypes);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor/distributor-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-distributor/{id}")
    public ModelAndView editDistributor(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
        	List<DistributorType> distTypes = distTypeService.findAll();
        	mv.addObject("distTypes", distTypes);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor/distributor-edit-form");
        }
        return mv;

    }

    @RequestMapping("/get/distributor-profile")
    public ModelAndView editDistributor(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long id = (long) request.getSession().getAttribute("orgId");
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/distributor/distributor-profile");
        }
        return mv;

    }
    @RequestMapping("/distributor-profile/{id}")
    public ModelAndView getProfile(@PathVariable long id,HttpServletRequest request, Model model) {
    	ModelAndView mv = new ModelAndView();
    	 if (request.getSession().getAttribute("orgId") == null) {
             mv.setViewName("redirect:/login");
         } else {
        	 Optional<Distributor> dist = distService.findById(id);
        	 String image = dist.get().getTradeImage();
        	 mv.addObject("image",image);
         	 model.addAttribute("id", id);
             model.addAttribute("baseurl", baseurl);
             mv.setViewName("admin-pages/distributor/distributor-profile2");
         }
         return mv;
    	
    }
}
