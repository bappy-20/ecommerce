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
import com.inovex.main.entity.CampaignType;
import com.inovex.main.entity.CashBackCampaign;
import com.inovex.main.entity.Organization;
import com.inovex.main.service.CampaignService;
import com.inovex.main.service.CampaignTypesService;
import com.inovex.main.service.CashBackCampaignService;
import com.inovex.main.service.OrganizationService;

@Controller
@RequestMapping("/admin")
public class CashBackCampaignViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    CampaignTypesService campTypeService;
    @Autowired
    OrganizationService orgService;
    @Autowired
    CashBackCampaignService cashBackCampaignService;
    @Autowired
    CampaignService campaignService;

    @GetMapping("/cash-back-campaign")
    public ModelAndView viewCampaign(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/cash-back-campaign/cash-back-campaign-home");
        }
        return mv;
    }

    @GetMapping("/edit-cash-back-campaign/{id}")
    public ModelAndView editCampaign(HttpServletRequest request, @PathVariable long id, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<CampaignType> camTypeList = new HashSet<>();
            Set<Campaign> campaignList = new HashSet<>();
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                camTypeList = org.get().getCampaignType();
                campaignList = org.get().getCampaigns();
            }
            long campaignId = 0;
            Optional<CashBackCampaign> cbc = cashBackCampaignService.findById(id);
            if (cbc.isPresent()) {
                campaignId = cbc.get().getCampaignId();
                Optional<Campaign> camp = campaignService.findById(campaignId);
                if (camp.isPresent()) {
                    model.addAttribute("campName", camp.get().getCampaignName());
                } else {
                    model.addAttribute("campName", "Not found");
                }

            }

            model.addAttribute("camTypeList", camTypeList);
            model.addAttribute("campaignList", campaignList);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/cash-back-campaign/cash-back-campaign-edit-form");
        }
        return mv;
    }

    @GetMapping("/add-cash-back-campaign")
    public ModelAndView viewDhamaka(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<CampaignType> camTypeList = new HashSet<>();
            Set<Campaign> campaignList = new HashSet<>();
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                camTypeList = org.get().getCampaignType();
//                campaignList = org.get().getCampaigns().stream().filter(p -> p.getCampaignType().equals("Cash Back"))
//                        .collect(Collectors.toSet());
                campaignList = org.get().getCampaigns().stream().filter(p -> p.getCampaignType().equals("CashBack Campaign"))
                     .collect(Collectors.toSet());
            }
            model.addAttribute("camTypeList", camTypeList);
            model.addAttribute("campaignList", campaignList);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/cash-back-campaign/cash-back-campaign-add-form");
        }
        return mv;
    }
}
