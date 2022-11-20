package com.inovex.main.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.Employee;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.User;
import com.inovex.main.entity.UserType;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.UserTypeService;

@Controller
@RequestMapping("/admin")
public class NotificationViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    EmployeeService empService;
    @Autowired
    UserTypeService userTypeService;
    @Autowired
    OrganizationService orgService;

    @RequestMapping("/notification")
    public ModelAndView viewNotification(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            //Set<Employee> empList = new HashSet<>();
        	Set<User> userList = new HashSet<>();
            Set<UserType> typeList = new HashSet<>();
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
               // empList = org.get().getEmployess();
            	userList = org.get().getUsers();
                typeList = org.get().getUserType();
            }
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("emp", userList);
            model.addAttribute("typeList", typeList);
            mv.setViewName("admin-pages/notification/notification-home");
        }
        return mv;
    }

    @RequestMapping("/add-notification")
    public ModelAndView addNotification(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/notification/notification-add-form");
        }
        return mv;
    }

    @RequestMapping("/edit-notification/{id}")
    public ModelAndView editNotification(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/notification/notification-edit-form");
        }
        return mv;
    }
}