package com.inovex.main.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.RouteModel;
import com.inovex.main.entity.User;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.RouteModelService;
import com.inovex.main.service.UserService;

@Controller
@RequestMapping("/admin")
public class DayMappingWithRouteViewController {
    @Autowired
    EmployeeService empService;
    @Autowired
    RouteModelService routeService;
    @Autowired
    UserService userService;

    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/dayModel")
    public ModelAndView getDayModel(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/dayModel/dayModel-home");
        }
        return mv;

    }

    @RequestMapping("/add-dayModel")
    public ModelAndView addDayModel(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else { 
        	List<User> userList = userService.getAllUser().stream()
        			  .filter(c -> c.getUserType().equalsIgnoreCase("sr"))
        			  .collect(Collectors.toList());
        	model.addAttribute("user", userList);   
        	//model.addAttribute("emp", empList);   	
            List<RouteModel> routeList = routeService.findAll();
            model.addAttribute("route", routeList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/dayModel/dayModel-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-dayModel/{id}")
    public ModelAndView editDayModel(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("id", id);
            List<User> userList = userService.getAllUser().stream()
      			  .filter(c -> c.getUserType().equalsIgnoreCase("sr"))
      			  .collect(Collectors.toList());
            model.addAttribute("user", userList); 
            List<RouteModel> routeList = routeService.findAll();
            model.addAttribute("route", routeList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/dayModel/dayModel-edit-form");
        }
        return mv;
    }
}
