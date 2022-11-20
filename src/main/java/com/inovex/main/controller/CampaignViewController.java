package com.inovex.main.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.Campaign;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.Price;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.service.OrganizationService;

@Controller
@RequestMapping("/admin")
public class CampaignViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    OrganizationService orgService;

    @GetMapping("/campaign")
    public ModelAndView viewCampaign(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/campaign/campaign-home");
        }
        return mv;
    }

    @GetMapping("/add-campaign")
    public ModelAndView addCampaign(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
//            long orgId = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgService.findById(orgId);
//            Set<CampaignType> camTypeList = new HashSet<>();
//            if (org.isPresent()) {
//                camTypeList = org.get().getCampaignType();
//            }
//            model.addAttribute("camList", camTypeList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/campaign/campaign-add-form");
        }
        return mv;
    }

    @GetMapping("/edit-campaign/{id}")
    public ModelAndView editCampaign(HttpServletRequest request, @PathVariable long id, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
//            long orgId = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgService.findById(orgId);
//            Set<CampaignType> camTypeList = new HashSet<>();
//
//            if (org.isPresent()) {
//                camTypeList = org.get().getCampaignType();
//
//            }
//            model.addAttribute("camList", camTypeList);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/campaign/campaign-edit-form");
        }
        return mv;
    }

    @GetMapping("/add-product-wise-campaign")
    public ModelAndView addProductWiseCampaign(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<Campaign> campaignList = new HashSet<>();
            Set<ProductMapping> prdList = new HashSet<>();
            Set<Price> priceList = new HashSet<>();
            if (org.isPresent()) {

            	campaignList = org.get().getCampaigns().stream().filter(p -> p.getCampaignType().equals("Product Wise Campaign"))
                        .collect(Collectors.toSet());

                prdList = org.get().getProductMapping();
                priceList = org.get().getPrice();
            }
            model.addAttribute("campaignList", campaignList);
            model.addAttribute("prdList", prdList);
            model.addAttribute("priceList", priceList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/campaign/add-product-wise-campaign");
        }
        return mv;
    }

    @GetMapping("/product-wise-campaign")
    public ModelAndView viewProductWiseCampaign(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/campaign/product-wise-campaign-home");
        }
        return mv;
    }

    @GetMapping("/edit-product-wise-campaign/{id}")
    public ModelAndView editProductWiseCampaign(HttpServletRequest request, @PathVariable long id, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
          //  Set<CampaignType> camTypeList = new HashSet<>();
            Set<Campaign> campaignList = new HashSet<>();
            Set<ProductMapping> prdList = new HashSet<>();
            Set<Price> priceList = new HashSet<>();
            if (org.isPresent()) {
             //   camTypeList = org.get().getCampaignType();
                campaignList = org.get().getCampaigns();
                prdList = org.get().getProductMapping();
                priceList = org.get().getPrice();
            }

            //model.addAttribute("camTypeList", camTypeList);
            model.addAttribute("campaignList", campaignList);
            model.addAttribute("prdList", prdList);
            model.addAttribute("priceList", priceList);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/campaign/edit-product-wise-campaign");
        }
        return mv;
    }
}