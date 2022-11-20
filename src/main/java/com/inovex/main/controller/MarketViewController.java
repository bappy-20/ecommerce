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

import com.inovex.main.entity.TerritoryModel;
import com.inovex.main.service.TerritoryService;

@Controller
@RequestMapping("/admin")
public class MarketViewController {
    @Autowired
    TerritoryService territoryService;
    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/market")
    public ModelAndView getMarket(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/market/market-home");
        }
        return mv;

    }

    @RequestMapping("/add-market")
    public ModelAndView addMarket(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<TerritoryModel> territory = territoryService.findAll();
            mv.addObject("territory", territory);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/market/market-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-market/{id}")
    public ModelAndView editMarket(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            List<TerritoryModel> territory = territoryService.findAll();
            mv.addObject("territory", territory);
            mv.setViewName("admin-pages/market/market-edit-form");
        }
        return mv;

    }

}
