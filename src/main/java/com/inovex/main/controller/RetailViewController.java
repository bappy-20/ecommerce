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

import com.inovex.main.entity.Market;
import com.inovex.main.entity.RetailType;
import com.inovex.main.service.MarketService;
import com.inovex.main.service.RetailTypeService;

@Controller
@RequestMapping("/admin")
public class RetailViewController {
    @Autowired
    MarketService mktService;
    @Autowired
    RetailTypeService retypeService;
    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/retail")
    public ModelAndView getRetail(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/retail/retail-home");
        }
        return mv;

    }

    @RequestMapping("/pending-retail-home")
    public ModelAndView getPendingRetail(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/retail/pending-retail-home");
        }
        return mv;

    }

    @RequestMapping("/add-retail")
    public ModelAndView addRetail(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<Market> market = mktService.findAll();
            List<RetailType> retailList = retypeService.findAll();
            mv.addObject("market", market);
            mv.addObject("retailType", retailList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/retail/retail-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-retail/{id}")
    public ModelAndView editRetail(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("id", id);
            List<Market> market = mktService.findAll();
            List<RetailType> retailList = retypeService.findAll();
            mv.addObject("market", market);
            mv.addObject("retailType", retailList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/retail/retail-edit-form");
        }
        return mv;

    }

}
