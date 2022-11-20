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

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductBatch;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.entity.PurchaseProduct;
import com.inovex.main.entity.Supplier;
import com.inovex.main.service.OrganizationService;

@Controller
@RequestMapping("/admin")
public class ProductPurchaseViewController {

    @Value("${base.url}")
    private String baseurl;
    @Autowired
    OrganizationService orgService;

    @RequestMapping("/incomingProduct")
    public ModelAndView getIncomingProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/incomingProduct/purchase-product-home");
        }
        return mv;

    }

    @RequestMapping("/add-incomingProduct")
    public ModelAndView addIncomingProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<Supplier> supList = new HashSet<>();
            Set<ProductMapping> productList = new HashSet<>();
            if (org.isPresent()) {
                supList = org.get().getSuppliers();
                productList = org.get().getProductMapping();

            }

            model.addAttribute("baseurl", baseurl);
            model.addAttribute("supplier", supList);
            model.addAttribute("product", productList);
            mv.setViewName("admin-pages/incomingProduct/incomingProduct-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-incomingProduct/{id}")
    public ModelAndView editIncomingProduct(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<Supplier> supList = new HashSet<>();
            Set<ProductMapping> productList = new HashSet<>();
            if (org.isPresent()) {
                supList = org.get().getSuppliers();
                productList = org.get().getProductMapping();

            }
            model.addAttribute("id", id);
            model.addAttribute("supplier", supList);
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("product", productList);
            mv.setViewName("admin-pages/incomingProduct/incomingProduct-edit-form");
        }
        return mv;

    }

    @RequestMapping("/product-purchase")
    public ModelAndView purchaseProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<Supplier> supList = new HashSet<>();
            Set<ProductMapping> productList = new HashSet<>();
            Set<ProductBatch> productBatchList = new HashSet<>();
            if (org.isPresent()) {
                supList = org.get().getSuppliers();
                productList = org.get().getProductMapping();
                productBatchList = org.get().getProductBatch();

            }
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("supplier", supList);
            model.addAttribute("productBatchList", productBatchList);
            model.addAttribute("product", productList);
            mv.setViewName("admin-pages/incomingProduct/product-purchase2");
        }
        return mv;

    }

    @RequestMapping("/get-product-details/{id}")
    public ModelAndView getProductDetails(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<ProductMapping> productList = new HashSet<>();
            if (org.isPresent()) {
                productList = org.get().getProductMapping();

            }

            model.addAttribute("id", id);
            model.addAttribute("product", productList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/incomingProduct/incomingProduct-home");
        }
        return mv;

    }

    @RequestMapping("/return-product-purchase")
    public ModelAndView ReturnPurchaseProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<Supplier> supList = new HashSet<>();
            Set<ProductMapping> productList = new HashSet<>();
            Set<PurchaseProduct> purchaseList = new HashSet<>();
            if (org.isPresent()) {
                supList = org.get().getSuppliers();
                productList = org.get().getProductMapping();
                purchaseList = org.get().getPurchaseProducts();
            }
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("supplier", supList);
            model.addAttribute("product", productList);
            model.addAttribute("purchaseList", purchaseList);
            mv.setViewName("admin-pages/incomingProduct/product-purchase-return");
        }
        return mv;

    }

    @RequestMapping("/return-purhcase-home")
    public ModelAndView getReturnProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/incomingProduct/return-purchase-product-home");
        }
        return mv;

    }

    @RequestMapping("/return-all-product-home/{id}")
    public ModelAndView getReturnProductDetails(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<ProductMapping> productList = new HashSet<>();
            if (org.isPresent()) {
                productList = org.get().getProductMapping();
            }
            model.addAttribute("id", id);
            model.addAttribute("product", productList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/incomingProduct/return-product-home");
        }
        return mv;

    }
}
