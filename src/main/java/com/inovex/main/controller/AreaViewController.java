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

import com.inovex.main.entity.RegionModel;
import com.inovex.main.service.RegionModelService;

@Controller
@RequestMapping("/admin")
public class AreaViewController {

    @Autowired
    RegionModelService regionService;
    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/area")
    public ModelAndView getArea(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/area/area-home");
        }
        return mv;
    }

    @RequestMapping("/add-area")
    public ModelAndView addArea(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<RegionModel> region = regionService.findAll();
            mv.addObject("region", region);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/area/area-add-form");
        }
        return mv;
    }

    @RequestMapping("/edit-area/{id}")
    public ModelAndView editArea(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("id", id);
            List<RegionModel> region = regionService.findAll();
            mv.addObject("region", region);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/area/area-edit-form");
        }
        return mv;
    }

    @RequestMapping("/area2")
    public ModelAndView getArea2(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/area/area-home1");
        }
        return mv;
    }

}