package com.inovex.main.controller;

import java.util.HashSet;
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

import com.inovex.main.entity.BrandModel;
import com.inovex.main.entity.Category;
import com.inovex.main.entity.CompanyModel;
import com.inovex.main.entity.MeasurementUnit;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductModel;
import com.inovex.main.entity.ProductSubCategory;
import com.inovex.main.entity.Supplier;
import com.inovex.main.service.BrandModelService;
import com.inovex.main.service.MeasurementUnitService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.ProductCategoryService;
import com.inovex.main.service.SupplierService;

@Controller
@RequestMapping("/admin")
public class ProductMappingViewController {
    @Autowired
    ProductCategoryService productCategory;
    @Autowired
    SupplierService supplierService;
    @Autowired
    OrganizationService orgService;
    @Autowired
    MeasurementUnitService measurementUnitService;
    @Autowired
    BrandModelService brandService;
    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/product-mapping")
    public ModelAndView getProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product-mapping/product-mapping-home");
        }
        return mv;

    }

    @RequestMapping("/add-product-mapping")
    public ModelAndView addProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<Category> cateList = new HashSet<>();
            Set<Supplier> supList = new HashSet<>();
            Set<MeasurementUnit> msrList = new HashSet<>();
            //Set<BrandModel> brandList = new HashSet<>();
            Set<CompanyModel> companyList = new HashSet<>();
            Set<ProductModel> productList = new HashSet<>();
            //Set<ProductSubCategory> subCatList = new HashSet<>();
            if (org.isPresent()) {
                cateList = org.get().getCategories();
                supList = org.get().getSuppliers();
                msrList = org.get().getMeasurementUnits();
                //brandList = org.get().getBrandModels();
                productList = org.get().getProductModel();
                //subCatList = org.get().getProductSubCategory();
                companyList = org.get().getCompanyModel();
            }

            model.addAttribute("category", cateList);
            model.addAttribute("supplier", supList);
            model.addAttribute("measure", msrList);
            //model.addAttribute("brand", brandList);
            model.addAttribute("productList", productList);
            //model.addAttribute("subCatList", subCatList);
            model.addAttribute("companyList", companyList);

            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product-mapping/product-mapping-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-product-mapping/{id}")
    public ModelAndView editProduct(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<Category> cateList = new HashSet<>();
            Set<Supplier> supList = new HashSet<>();
            Set<MeasurementUnit> msrList = new HashSet<>();
            Set<BrandModel> brandList = new HashSet<>();
            Set<CompanyModel> companyList = new HashSet<>();
            Set<ProductModel> productList = new HashSet<>();
            Set<ProductSubCategory> subCatList = new HashSet<>();
            if (org.isPresent()) {
                cateList = org.get().getCategories();
                supList = org.get().getSuppliers();
                msrList = org.get().getMeasurementUnits();
               // brandList = org.get().getBrandModels();
                brandList = org.get().getBrandModels();
                productList = org.get().getProductModel();
                subCatList = org.get().getProductSubCategory();
                companyList = org.get().getCompanyModel();
            }

            model.addAttribute("category", cateList);
            model.addAttribute("supplier", supList);
            model.addAttribute("measure", msrList);
            model.addAttribute("brand", brandList);
            model.addAttribute("productList", productList);
            model.addAttribute("subCatList", subCatList);
            model.addAttribute("companyList", companyList);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/product-mapping/product-mapping-edit-form");
        }
        return mv;

    }

}
