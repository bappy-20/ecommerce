package com.inovex.main.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.service.OrganizationService;

@Controller
@RequestMapping("/admin")
public class SupplierViewController {

    @Value("${base.url}")
    private String baseurl;
    @Autowired
    OrganizationService orgService;


    @RequestMapping("/supplier")
    public ModelAndView getSupplier(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/supplier/suplier-home");
        }
        return mv;

    }

    @RequestMapping("/add-supplier")
    public ModelAndView addSupplier(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/supplier/suplier-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-supplier/{id}")
    public ModelAndView editSupplier(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/supplier/suplier-edit-form");
        }
        return mv;
    }

}
