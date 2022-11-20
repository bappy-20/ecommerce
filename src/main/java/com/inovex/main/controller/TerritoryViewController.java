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

import com.inovex.main.entity.AreaModel;
import com.inovex.main.service.AreaService;

@Controller
@RequestMapping("/admin")

public class TerritoryViewController {
    @Autowired
    AreaService areaService;
    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/territory")
    public ModelAndView getTerritory(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/territory/territory-home");
        }
        return mv;

    }

    @RequestMapping("/add-territory")
    public ModelAndView addTerritory(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<AreaModel> area = areaService.findAll();
            mv.addObject("area", area);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/territory/territory-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-territory/{id}")
    public ModelAndView editTerritory(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("id", id);
            List<AreaModel> area = areaService.findAll();
            mv.addObject("area", area);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/territory/territory-edit-form");
        }
        return mv;
    }

}
