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

import com.inovex.main.entity.Organization;
import com.inovex.main.service.OrganizationService;

@Controller
public class RoleViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    OrganizationService orgService;

    @RequestMapping("/superadmin/role")
    public ModelAndView getEmployee(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();

        model.addAttribute("baseurl", baseurl);
        mv.setViewName("admin-pages/role/role-home");

        return mv;
    }

    @RequestMapping("/superadmin/add-role")
    public ModelAndView addRegion(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();

        List<Organization> orgList = orgService.getAllOrganizations();
        model.addAttribute("baseurl", baseurl);
        model.addAttribute("orgList", orgList);
        mv.setViewName("admin-pages/role/role-add-form");

        return mv;

    }

    @RequestMapping("/superadmin/edit-user-role/{id}")
    public ModelAndView editRegion(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();

        model.addAttribute("id", id);
        model.addAttribute("baseurl", baseurl);
        mv.setViewName("admin-pages/role/role-edit-form");

        return mv;
    }

    @RequestMapping("/role-1")
    public ModelAndView getEmployee1(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/role/role-home1");
        }
        return mv;
    }
}
