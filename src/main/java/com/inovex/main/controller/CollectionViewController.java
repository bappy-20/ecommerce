package com.inovex.main.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class CollectionViewController {	
    @GetMapping("/collection")
    public ModelAndView viewColletion(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            mv.setViewName("admin-pages/collection/collection-home");
        }
        return mv;
    }
    @GetMapping("/collection-date-range")
    public ModelAndView viewColletionDateRange(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            mv.setViewName("admin-pages/collection/collection_by_date_range");
        }
        return mv;
    }
}
