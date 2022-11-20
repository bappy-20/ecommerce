package com.inovex.main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.AreaModel;
import com.inovex.main.entity.Market;
import com.inovex.main.entity.RegionModel;
import com.inovex.main.entity.RouteModel;
import com.inovex.main.entity.TerritoryModel;
import com.inovex.main.service.AreaService;
import com.inovex.main.service.RegionModelService;
import com.inovex.main.service.RouteModelService;
import com.inovex.main.service.TerritoryService;

@Controller
@RequestMapping("/admin")
public class RouteViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    RegionModelService regionService;
    @Autowired
    RouteModelService routeService;
    @Autowired
    AreaService areaService;
    @Autowired
    TerritoryService territoryService;

    @GetMapping("/route")
    public ModelAndView viewRoute(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/route/route-home");
        }
        return mv;
    }

    @GetMapping("/add-route")
    public ModelAndView addRoute(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("distId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("route", new RouteModel());
            List<RegionModel> group = regionService.findAll();
            model.addAttribute("group", group);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/route/route-add-form");
        }
        return mv;
    }

    @PostMapping("/add-route")
    public ModelAndView saveRoute(@Valid RouteModel route, BindingResult bindingResult, Model model,
            HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("distId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            if (bindingResult.hasErrors()) {
                mv.setViewName("redirect:/admin/add-route");
            }
            try {
                Optional<RegionModel> region = regionService.findById(Long.parseLong(route.getRegionName()));
                route.setRegionName(region.get().getRegionName());
                Optional<AreaModel> area = areaService.findById(Long.parseLong(route.getAreaName()));
                route.setAreaName(area.get().getAreaName());
                Optional<TerritoryModel> territory = territoryService
                        .findById(Long.parseLong(route.getTerritoryName()));
                route.setTerritoryName(territory.get().getTerritoryName());
                Set<Market> mkt = route.getMarkets();
                List<String> mktList = new ArrayList<String>();
                for (Market market : mkt) {
                    mktList.add(market.getMarketName());
                }
                route.setMarketName(mktList.toString());
                routeService.save(route, request);

                mv.setViewName("redirect:/admin/route");
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("message",
                        "Failed! You select a market which already added in another route.Please select new market list");
                model.addAttribute("route", new RouteModel());
                List<RegionModel> group = regionService.findAll();
                model.addAttribute("group", group);
                model.addAttribute("baseurl", baseurl);
                mv.setViewName("admin-pages/route/route-add-form");

            }
        }
        return mv;
    }

    @GetMapping("/edit-route/{id}")
    public ModelAndView editRoute(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("route", new RouteModel());
            List<RegionModel> group = regionService.findAll();
            model.addAttribute("group", group);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/route/route-edit-form");
        }
        return mv;
    }

}
