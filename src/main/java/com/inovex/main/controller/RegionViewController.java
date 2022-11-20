package com.inovex.main.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class RegionViewController {
    @Value("${base.url}")
    private String baseurl;
    
    @RequestMapping("/region")
    public ModelAndView getEmployee(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/region/region-home");
        }
        return mv;
    }
    
    @RequestMapping("/add-region")
    public ModelAndView addRegion(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
//            List<Employee> empList = empService.getAll();
//            List<UserType> typeList = userTypeService.findAll();
//            model.addAttribute("empList", empList);
//            model.addAttribute("typeList", typeList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/region/region-add-form");
        }
        return mv;

    }
    
    @RequestMapping("/edit-region/{id}")
    public ModelAndView editRegion(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
//            List<Employee> empList = empService.getAll();
//            List<UserType> typeList = userTypeService.findAll();
//            model.addAttribute("empList", empList);
//            model.addAttribute("typeList", typeList);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/region/region-edit-form2");
        }
        return mv;
    }
}
