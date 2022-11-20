package com.inovex.main.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.Distributor;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.ExpenseService;
import com.inovex.main.service.OrderHisoryService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.PurchaseOrderService;

@Controller
@RequestMapping("/admin")
public class WelcomeController {
    @Value("${base.url}")
    private String baseurl;

    @Autowired
    DistributorService distributorService;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    OrderHisoryService orderHistoryService;
    @Autowired
    PurchaseOrderService purchaseOrderService;
    @Autowired
    ExpenseService expenseService;

    @GetMapping("/home")
    public ModelAndView viewHome(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            int pending = orderHistoryService.getPendingOrderByCurdate().size();
            model.addAttribute("pending", pending);
            request.getSession().setAttribute("pending", pending);
            long id = (long) request.getSession().getAttribute("distId");
            Optional<Distributor> dist = distributorService.findById(id);
            if (dist.isPresent()) {
                request.getSession().setAttribute("orglogo", dist.get().getTradeImage());

            }
            Optional<String> totalSale = orderHistoryService.getTotalSale();
            Optional<String> totalSaleToday = orderHistoryService.getTotalSaleToday();
            Optional<String> totalSaleMonth = orderHistoryService.getTotalSaleMonth();

            Optional<String> totalDue = orderHistoryService.getTotalDue();
            Optional<String> totalDueToday = orderHistoryService.getTotalDueToday();
            Optional<String> totalDueMonth = orderHistoryService.getTotalDueMonth();

            Optional<String> totalPurchase = purchaseOrderService.getTotalPurchase();
            Optional<String> totalPurchaseToday = purchaseOrderService.getTotalPurchaseToday();
            Optional<String> totalPurchaseMonth = purchaseOrderService.getTotalPurchaseMonth();

            Optional<String> totalExp = expenseService.getTotalExpense();
            Optional<String> totalExpToday = expenseService.getTotalExpenseToday();
            Optional<String> totalExpMonth = expenseService.getTotalExpenseMonth();

            if (totalSale.isPresent()) {
                model.addAttribute("totalSale", totalSale.get());
            } else {
                model.addAttribute("totalSale", 0);
            }
            if (totalSaleToday.isPresent()) {
                model.addAttribute("totalSaleToday", totalSaleToday.get());
            } else {
                model.addAttribute("totalSaleToday", 0);
            }
            if (totalSaleMonth.isPresent()) {
                model.addAttribute("totalSaleMonth", totalSaleMonth.get());
            } else {
                model.addAttribute("totalSaleMonth", 0);
            }

            if (totalDue.isPresent()) {
                model.addAttribute("totalDue", totalDue.get());
            } else {
                model.addAttribute("totalDue", 0);
            }
            if (totalDueToday.isPresent()) {
                model.addAttribute("totalDueToday", totalDueToday.get());
            } else {
                model.addAttribute("totalDueToday", 0);
            }
            if (totalDueMonth.isPresent()) {
                model.addAttribute("totalDueMonth", totalDueMonth.get());
            } else {
                model.addAttribute("totalDueMonth", 0);
            }

            if (totalPurchase.isPresent()) {
                model.addAttribute("totalPurchase", totalPurchase.get());
            } else {
                model.addAttribute("totalPurchase", 0);
            }
            if (totalPurchaseToday.isPresent()) {
                model.addAttribute("totalPurchaseToday", totalPurchaseToday.get());
            } else {
                model.addAttribute("totalPurchaseToday", 0);
            }
            if (totalPurchaseMonth.isPresent()) {
                model.addAttribute("totalPurchaseMonth", totalPurchaseMonth.get());
            } else {
                model.addAttribute("totalPurchaseMonth", 0);
            }

            if (totalExp.isPresent()) {
                model.addAttribute("totalExp", totalExp.get());
            } else {
                model.addAttribute("totalExp", 0);
            }
            if (totalExpMonth.isPresent()) {
                model.addAttribute("totalExpMonth", totalExpMonth.get());
            } else {
                model.addAttribute("totalExpMonth", 0);
            }
            if (totalExpToday.isPresent()) {
                model.addAttribute("totalExpToday", totalExpToday.get());
            } else {
                model.addAttribute("totalExpToday", 0);
            }
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("home");
        }
        return mv;
    }
}
