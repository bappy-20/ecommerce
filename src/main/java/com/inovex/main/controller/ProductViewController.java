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

import com.inovex.main.entity.BrandModel;
import com.inovex.main.entity.Category;
import com.inovex.main.entity.MeasurementUnit;
import com.inovex.main.entity.Supplier;
import com.inovex.main.service.BrandModelService;
import com.inovex.main.service.MeasurementUnitService;
import com.inovex.main.service.ProductCategoryService;
import com.inovex.main.service.SupplierService;

@Controller
@RequestMapping("/admin")
public class ProductViewController {
    @Autowired
    ProductCategoryService productCategory;
    @Autowired
    SupplierService supplierService;
    @Autowired
    MeasurementUnitService measurementUnitService;
    @Autowired
    BrandModelService brandService;
    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/product")
    public ModelAndView getProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product/product-home");
        }
        return mv;

    }

    @RequestMapping("/add-product")
    public ModelAndView addProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<BrandModel> brandList = brandService.findAll();
            model.addAttribute("brand", brandList);
            List<Category> cateList = productCategory.findAll();
            model.addAttribute("category", cateList);
            List<Supplier> supList = supplierService.findAll();
            model.addAttribute("supplier", supList);
            List<MeasurementUnit> msrList = measurementUnitService.findAll();
            model.addAttribute("measure", msrList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product/product-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-product/{id}")
    public ModelAndView editProduct(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<Category> cateList = productCategory.findAll();
            model.addAttribute("category", cateList);
            List<Supplier> supList = supplierService.findAll();
            model.addAttribute("supplier", supList);
            List<MeasurementUnit> msrList = measurementUnitService.findAll();
            model.addAttribute("measure", msrList);
            List<BrandModel> brandList = brandService.findAll();
            model.addAttribute("brand", brandList);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product/product-edit-form");
        }
        return mv;

    }

}
