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
public class ProductModelViewController {

    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/product-model")
    public ModelAndView getProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product-model/product-model-home");
        }
        return mv;

    }

    @RequestMapping("/add-product-model")
    public ModelAndView addProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product-model/product-model-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-product-model/{id}")
    public ModelAndView editProduct(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product-model/product-model-edit-form");
        }
        return mv;

    }

}
