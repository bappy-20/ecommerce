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

import com.inovex.main.entity.ExpenseType;
import com.inovex.main.entity.User;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.ExpenseTypeService;
import com.inovex.main.service.UserService;

@Controller
@RequestMapping("/admin")
public class ExpenseViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    EmployeeService empService;
    @Autowired
    ExpenseTypeService expTypeService;
    @Autowired
    UserService userService;

    @RequestMapping("/expense")
    public ModelAndView getExpense(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/expense/expense-home");
        }
        return mv;
    }

    @RequestMapping("/approved-expense")
    public ModelAndView getApprovedExpense(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/expense/approved-expense-home");
        }
        return mv;
    }

    @RequestMapping("/add-expense")
    public ModelAndView addExpense(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
        	List<User> userList = userService.getAllUser();
        	model.addAttribute("user", userList); 
            List<ExpenseType> expList = expTypeService.findAll();
            model.addAttribute("expenseType", expList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/expense/expense-add-form");
        }
        return mv;
    }

    @RequestMapping("/edit-expense/{id}")
    public ModelAndView editExpense(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
        	List<User> userList = userService.getAllUser();
        	model.addAttribute("user", userList);
            List<ExpenseType> expList = expTypeService.findAll();
            model.addAttribute("expenseType", expList);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/expense/expense-edit-form");
        }
        return mv;
    }
    
    @RequestMapping("/expenseReportByExpTypeAndDateRange")
    public ModelAndView getExpenseReportByExpenseTypeANdDateRange(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            List<ExpenseType> expList = expTypeService.findAll();
            model.addAttribute("expenseType", expList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/expense/expenseReportByExpTypeAndDateRange");
        }
        return mv;
    }

    @RequestMapping("/expenseReportOfApprovedByDateRange")
    public ModelAndView getApprovedExpenseReportByDateRange(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/expense/expenseReportApprovedByDateRange");
        }
        return mv;
    }

    @RequestMapping("/expenseReportOfPendingByDateRange")
    public ModelAndView getPendingExpenseReportByDateRange(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/expense/pendingExpenseReportAByDateRange");
        }
        return mv;
    }
}