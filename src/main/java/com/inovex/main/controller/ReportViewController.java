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

import com.inovex.main.entity.Category;
import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.ExpenseType;
import com.inovex.main.entity.Retail;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.ExpenseTypeService;
import com.inovex.main.service.ProductCategoryService;
import com.inovex.main.service.RetailService;

@Controller
@RequestMapping("/admin")
public class ReportViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    ProductCategoryService productCategory;
    @Autowired
    ExpenseTypeService expTypeService;
    @Autowired
    RetailService retailService;
    @Autowired
    DistributorService distributorService;

    @RequestMapping("/product-inventory")
    public ModelAndView getProduct(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<Category> catList = productCategory.findAll();
            model.addAttribute("category", catList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/report/product-inventory");
        }
        return mv;

    }

    @RequestMapping("/expense-report")
    public ModelAndView getExpenseReport(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<ExpenseType> expList = expTypeService.findAll();
            model.addAttribute("expenseType", expList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/report/expense-report");
        }
        return mv;

    }

    @RequestMapping("/product-current-stock")
    public ModelAndView getCurrentStockReport(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/report/all-product-current-stock");
        }
        return mv;

    }

    @RequestMapping("/get-pending-order-by-date")
    public ModelAndView getPendingOrerReportByDate(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/report/date-wise-pending-order");
        }
        return mv;

    }

    @RequestMapping("/get-delivered-order-by-date")
    public ModelAndView getDeliveredOrerReportByDate(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/report/date-wise-delivered-order");
        }
        return mv;

    }

    @RequestMapping("/get-delivered-order-details-by-date/{orderId}")
    public ModelAndView OrderDEtails(@PathVariable String orderId, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("orderId", orderId);
            mv.setViewName("admin-pages/report/order-details");
        }
        return mv;

    }

    @RequestMapping("/get-order-by-date-and-retail")
    public ModelAndView getRetailWiseOrerReportByDate(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<Retail> reList = retailService.findAll();
            model.addAttribute("baseurl", baseurl);
            model.addAttribute("reList", reList);
            mv.setViewName("admin-pages/report/retail-and-date-wise-order");
        }
        return mv;

    }

    @RequestMapping("/get-delivered-product-by-date")
    public ModelAndView getDeliveredProductReportByDate(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/report/date-wise-delivered-product");
        }
        return mv;

    }
    
    @RequestMapping("/primary-inventory-stock")
    public ModelAndView getPrimaryInventoryCurrentStockReport(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/report/primary-inventory-report");
        }
        return mv;

    }
    
    @RequestMapping("/secondary-inventory-stock")
    public ModelAndView getSecondaryInventoryCurrentStockReport(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/report/secondary-inventory-report");
        }
        return mv;

    }
    
    @RequestMapping("/secondary-inventory-selected-dealer")
    public ModelAndView getSecondaryInventoryStockofADealer(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
        	List<Distributor> distributorList = distributorService.findAll();
        	model.addAttribute("distributor", distributorList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/report/secondary-inventory-report-selected-dealer");
        }
        return mv;

    }


}
