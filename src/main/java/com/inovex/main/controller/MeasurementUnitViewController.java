package com.inovex.main.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class MeasurementUnitViewController {

    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/measurementUnit")
    public ModelAndView getMeasurementUnit(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/measurementUnit/measurementUnit-home");
        }
        return mv;
    }

    @RequestMapping("/add-measurementUnit")
    public ModelAndView addMeasurementUnit(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/measurementUnit/measurementUnit-add-form");
        }
        return mv;
    }

    @RequestMapping("/edit-measurementUnit/{id}")
    public ModelAndView editMeasurementUnit(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("id", id);
//          List<Market> market = mktService.findAll();
//          List<RetailType> retailList = retypeService.findAll();
//          mv.addObject("market", market);
//          mv.addObject("retailType", retailList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/measurementUnit/measurementUnit-edit-form");
        }
        return mv;
    }

}
