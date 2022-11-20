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

import com.inovex.main.entity.ProductMapping;
import com.inovex.main.service.ProductMappingService;
import com.inovex.main.service.ProductModelService;

@Controller
@RequestMapping("/admin")
public class ProductBatchViewController {

    @Value("${base.url}")
    private String baseurl;
    @Autowired
    ProductModelService productService;
    @Autowired
    ProductMappingService productMappingService;

    @RequestMapping("/product-batch")
    public ModelAndView getPrice(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product-batch/product-batch-home");
        }
        return mv;

    }

    @RequestMapping("/add-product-batch")
    public ModelAndView addPrice(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
           /// List<ProductModel> productList = productService.findAll();
        	List<ProductMapping> productList = productMappingService.findAll();
            model.addAttribute("product", productList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product-batch/product-batch-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-product-batch/{id}")
    public ModelAndView editPrice(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("id", id);
            //List<ProductModel> productList = productService.findAll();
        	List<ProductMapping> productList = productMappingService.findAll();
            model.addAttribute("product", productList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product-batch/product-batch-edit-form");
        }
        return mv;

    }
}
