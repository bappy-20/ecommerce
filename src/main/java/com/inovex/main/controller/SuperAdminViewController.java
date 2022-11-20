package com.inovex.main.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/superadmin")
public class SuperAdminViewController {
    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/config-setup")
    public ModelAndView setup(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin-pages/super-admin/setup");
        return mv;

    }

    @RequestMapping("/org")
    public ModelAndView getOrg(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();

        model.addAttribute("baseurl", baseurl);
        mv.setViewName("admin-pages/organizations/org-home");

        return mv;

    }

    @RequestMapping("/add-org")
    public ModelAndView addOrg(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();

        model.addAttribute("baseurl", baseurl);
        mv.setViewName("admin-pages/organizations/org-add-form");

        return mv;

    }

    @RequestMapping("/edit-org/{id}")
    public ModelAndView editOrg(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();

        model.addAttribute("id", id);
        model.addAttribute("baseurl", baseurl);
        mv.setViewName("admin-pages/organizations/org-edit-form");

        return mv;

    }

}
