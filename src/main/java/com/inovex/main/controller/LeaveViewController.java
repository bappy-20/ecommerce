package com.inovex.main.controller;

import java.util.ArrayList;
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
import com.inovex.main.entity.User;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.UserService;

@Controller
@RequestMapping("/admin")
public class LeaveViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserService userService;

    @RequestMapping("/leave")
    public ModelAndView getLeave(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/leave/leave-home");
        }
        return mv;
    }

    @RequestMapping("/add-leave")
    public ModelAndView addLeave(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
        	List<User> users = new ArrayList<User>();
        	users = userService.getAllUser();
            model.addAttribute("emp", users);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/leave/leave-add-form");
        }
        return mv;
    }

    @RequestMapping("/edit-leave/{id}")
    public ModelAndView editLeave(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
        	List<User> users = new ArrayList<User>();
        	users = userService.getAllUser();
            model.addAttribute("emp", users);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/leave/leave-edit-form");
        }
        return mv;
    }

    @RequestMapping("/pendingLeave")
    public ModelAndView getPendingLeave(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/leave/pendingLeaveReport");
        }
        return mv;
    }

    @RequestMapping("/approved-leave")
    public ModelAndView getApprovedLeave(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/leave/approvedLeaveReport.html");
        }
        return mv;
    }

    @RequestMapping("/edit-PendingLeave/{id}")
    public ModelAndView editPendingLeave(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
        	List<User> users = new ArrayList<User>();
        	users = userService.getAllUser();
            model.addAttribute("emp", users);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/leave/pendingLeaveEditForm");
        }
        return mv;
    }
    
 
}
