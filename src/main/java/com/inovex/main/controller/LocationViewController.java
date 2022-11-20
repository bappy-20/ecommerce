package com.inovex.main.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.User;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.UserService;

@Controller
@RequestMapping("/admin")
public class LocationViewController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserService userService;

    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/location-ofEmployeeByName")
    public ModelAndView addLocationOfEmployeeByName(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
        	List<User> userList = userService.getAllUser();
         	model.addAttribute("user", userList); 
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/location/location-ofAnEmoloyeeByName");
        }
        return mv;
    }

    @RequestMapping("/all-employee-track")
    public ModelAndView getAllEmpMaxLocation(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/location/all-employee-location");
        }
        return mv;
    }
}
