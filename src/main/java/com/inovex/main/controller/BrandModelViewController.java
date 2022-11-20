package com.inovex.main.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.CompanyModel;
import com.inovex.main.entity.Organization;
import com.inovex.main.service.OrganizationService;

@Controller
@RequestMapping("/admin")
public class BrandModelViewController {
    @Autowired
    OrganizationService orgService;
    @Value("${base.url}")
    private String baseurl;

    @GetMapping("/brand")
    public ModelAndView viewRegion(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/brand/brand-home");
        }
        return mv;
    }

    @GetMapping("/add-brand")
    public ModelAndView addRegion(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<CompanyModel> companyList = new HashSet<>();
            if (org.isPresent()) {
                companyList = org.get().getCompanyModel();
            }
            model.addAttribute("companyList", companyList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/brand/brand-add-form");
        }
        return mv;
    }

    @GetMapping("/edit-brand/{id}")
    public ModelAndView editRegion(HttpServletRequest request, @PathVariable long id, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<CompanyModel> companyList = new HashSet<>();
            if (org.isPresent()) {
                companyList = org.get().getCompanyModel();
            }
            model.addAttribute("companyList", companyList);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/brand/brand-edit-form");
        }
        return mv;
    }
}
