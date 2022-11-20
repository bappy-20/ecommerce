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

public class ProductSubCategoryViewController {

    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/sub-category/{id}")
    public ModelAndView getCategory(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            request.getSession().setAttribute("cateId", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product-sub-category/sub-category-home");
        }
        return mv;

    }

    @RequestMapping("/add-sub-category")
    public ModelAndView addCategory(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long catId = (long) request.getSession().getAttribute("cateId");
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("catId", catId);
            mv.setViewName("admin-pages/product-sub-category/sub-category-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-sub-category/{id}")
    public ModelAndView editCategory(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long catId = (long) request.getSession().getAttribute("cateId");
            model.addAttribute("catId", catId);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product-sub-category/sub-category-edit-form");
        }
        return mv;
    }

}
