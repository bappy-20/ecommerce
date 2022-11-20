package com.inovex.main.controller;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.service.OrganizationService;

@Controller
@RequestMapping("/admin")
public class PriceViewController {

    @Value("${base.url}")
    private String baseurl;
    @Autowired
    OrganizationService orgService;

    @RequestMapping("/price")
    public ModelAndView getPrice(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/price/price-home");
        }
        return mv;

    }

    @RequestMapping("/add-price")
    public ModelAndView addPrice(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                Set<ProductMapping> productList = org.get().getProductMapping();
                model.addAttribute("product", productList);
                model.addAttribute("baseurl", baseurl);
                mv.setViewName("admin-pages/price/price-add-form");
            }

        }
        return mv;

    }

    @RequestMapping("/edit-price/{id}")
    public ModelAndView editPrice(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                Set<ProductMapping> productList = org.get().getProductMapping();
                model.addAttribute("product", productList);
                model.addAttribute("id", id);
                model.addAttribute("baseurl", baseurl);
                mv.setViewName("admin-pages/price/price-edit-form");

            }

        }
        return mv;

    }
}
