package com.inovex.main.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.Category;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.User;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.ProductCategoryService;

@Controller
@RequestMapping("/admin")
public class MonthlyTargetViewController {

    @Value("${base.url}")
    private String baseurl;
    @Autowired
    EmployeeService empService;
    @Autowired
    ProductCategoryService productCategory;
    @Autowired
    OrganizationService orgService;

    @RequestMapping("/monthlyTarget/{id}")
    public ModelAndView getMonthlyTarget1(@PathVariable String id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("id", id);
            mv.setViewName("admin-pages/monthlyTarget/monthlyTarget-home");
        }
        return mv;

    }

    @RequestMapping("/monthlyTarget-summery")
    public ModelAndView getMonthlyTargetSummery(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/monthlyTarget/monthlyTarget-summery");
        }
        return mv;

    }

    @RequestMapping("/add-monthlyTarget")
    public ModelAndView addMonthlyTarget(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<User> users = new HashSet<>();
            Set<Category> catList = new HashSet<>();

            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                users = org.get().getUsers().stream()
                        .filter(p -> p.getUserType().equals("SR") || p.getUserType().equals("DSR"))
                        .collect(Collectors.toSet());
                catList = org.get().getCategories();

            }
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("emp", users);
            model.addAttribute("category", catList);
            mv.setViewName("admin-pages/monthlyTarget/monthlyTarget-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-monthlyTarget/{id}")
    public ModelAndView editMonthlyTarget(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<User> users = new HashSet<>();
            Set<Category> catList = new HashSet<>();

            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                users = org.get().getUsers().stream()
                        .filter(p -> p.getUserType().equals("SR") || p.getUserType().equals("DSR"))
                        .collect(Collectors.toSet());
                catList = org.get().getCategories();

            }

            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("emp", users);
            model.addAttribute("category", catList);
            mv.setViewName("admin-pages/monthlyTarget/monthlyTarget-edit-form");
        }
        return mv;

    }

    @RequestMapping("/monthlyTarget1")
    public ModelAndView getMonthlyTarget(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<User> users = new HashSet<>();

            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                users = org.get().getUsers().stream()
                        .filter(p -> p.getUserType().equals("SR") || p.getUserType().equals("DSR"))
                        .collect(Collectors.toSet());

            }

            model.addAttribute("emp", users);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/monthlyTarget/monthlyTarget-home1");
        }
        return mv;

    }

    @RequestMapping("/deliveryman-order-target")
    public ModelAndView getMonthlyOrderTarget(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/monthlyTarget/monthlyTarget-for-delivery-man-home");
        }
        return mv;

    }

    @RequestMapping("/order-delivery-monthly-target")
    public ModelAndView getMonthlyTargetForDelivery(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/monthlyTarget/monthlyTarget-for-delivery-man");
        }
        return mv;

    }
    
    @RequestMapping("/monthlyTarget2")
    public ModelAndView getMonthlyTarget2(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<User> users = new HashSet<>();

            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                users = org.get().getUsers().stream()
                        .filter(p -> p.getUserType().equals("SR") || p.getUserType().equals("DSR"))
                        .collect(Collectors.toSet());
                
            }

            model.addAttribute("emp", users);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/monthlyTarget/monthlyTarget-home2");
        }
        return mv;

    }
}