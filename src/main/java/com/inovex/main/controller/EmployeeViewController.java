package com.inovex.main.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.Employee;
import com.inovex.main.entity.UserType;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.UserTypeService;

@Controller
@RequestMapping("/admin")
public class EmployeeViewController {
    @Autowired
    EmployeeService empService;
    @Autowired
    UserTypeService userTypeService;
    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/employee")
    public ModelAndView getEmployee(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/employee/employee-home");
        }
        return mv;

    }

    @RequestMapping("/employee-profile")
    public ModelAndView getEmployeeProfile(HttpServletRequest request, @PathVariable String id) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            mv.addObject("id", id);
            mv.setViewName("admin-pages/employee/employee-profile");
        }
        return mv;

    }

    @RequestMapping("/add-employee")
    public ModelAndView addEmployee(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<Employee> empList = empService.getAll();
            List<UserType> typeList = userTypeService.findAll();
            model.addAttribute("empList", empList);
            model.addAttribute("typeList", typeList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/employee/employee-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-employee/{id}")
    public ModelAndView editEmployee(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<Employee> empList = empService.getAll();
            List<UserType> typeList = userTypeService.findAll();
            model.addAttribute("empList", empList);
            model.addAttribute("typeList", typeList);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/employee/employee-edit-form");
        }
        return mv;

    }
}