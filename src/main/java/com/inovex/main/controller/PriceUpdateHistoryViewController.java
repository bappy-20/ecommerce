package com.inovex.main.controller;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.service.OrganizationService;

@Controller
@RequestMapping("/admin")
public class PriceUpdateHistoryViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    OrganizationService orgService;

    @RequestMapping("/price-update-history")
    public ModelAndView PriceUpdateHistory(HttpServletRequest request, Model model) {
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
                mv.setViewName("admin-pages/priceUpdateHistory/price-update-history");
            }

        }
        return mv;

    }

}
