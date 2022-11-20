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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.AreaModel;
import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.Market;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.RegionModel;
import com.inovex.main.entity.TerritoryModel;
import com.inovex.main.entity.User;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.UserService;

@Controller
@RequestMapping("/admin")
public class UserMappingViewController {

    @Autowired
    UserService userService;
    @Autowired
    DistributorService distService;
    @Autowired
    OrganizationService orgService;
    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/map-user-to-area")
    public ModelAndView addArea(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<User> users = new HashSet<>();
            Set<AreaModel> areas = new HashSet<AreaModel>();

            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                users = org.get().getUsers().stream().filter(p -> p.getUserType().equals("ASM"))
                        .collect(Collectors.toSet());
                areas = org.get().getAreaModels();

            }
            mv.addObject("users", users);
            mv.addObject("areas", areas);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user-mapping/map-user-to-area");
        }
        return mv;
    }

    @RequestMapping("/map-user-to-territory")
    public ModelAndView mapTerritory(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<User> users = new HashSet<>();
            Set<TerritoryModel> territorys = new HashSet<TerritoryModel>();

            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                users = org.get().getUsers().stream()
                        .filter(p -> p.getUserType().equals("TSM") || p.getUserType().equals("TSO"))
                        .collect(Collectors.toSet());
                territorys = org.get().getTerritoryModels();

            }
            mv.addObject("users", users);
            mv.addObject("territorys",territorys );
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user-mapping/map-user-to-territory");
        }
        return mv;
    }

    @RequestMapping("/map-user-to-region")
    public ModelAndView mapUsreseReion(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<User> users = new HashSet<>();
            Set<RegionModel> regions = new HashSet<RegionModel>();

            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                users = org.get().getUsers().stream().filter(p -> p.getUserType().equals("RSM"))
                        .collect(Collectors.toSet());
                regions = org.get().getRegions();

            }
            mv.addObject("users", users);
            mv.addObject("regions", regions);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user-mapping/map-user-to-region");
        }
        return mv;
    }

    @RequestMapping("/map-user-to-distributor")
    public ModelAndView mapUsreseDistributor(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<Distributor> dealers = new HashSet<>();
            Set<User> users = new HashSet<>();
            
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                dealers = org.get().getDistributors();
                users = org.get().getUsers().stream().filter(p -> p.getUserType().equals("Distributor"))
                        .collect(Collectors.toSet());

            }
            mv.addObject("dealers", dealers);
            mv.addObject("users", users);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user-mapping/map-user-to-dealer");
        }
        return mv;
    }

    @RequestMapping("/edit-map-user-to-distributor/{id}")
    public ModelAndView editmapUsreseDistributor(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<Distributor> dealers = new HashSet<>();
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                dealers = org.get().getDistributors();

            }
            mv.addObject("dealers", dealers);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user-mapping/edit-map-user-to-dealer");
        }
        return mv;
    }

    @RequestMapping("/edit-map-user/{id}")
    public ModelAndView editMappingArea(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<User> users = new HashSet<>();
            Set<AreaModel> arealist = new HashSet<>();
            Set<TerritoryModel> terlist = new HashSet<>();
            Set<RegionModel> regionlist = new HashSet<>();
            Set<Market> mktlist = new HashSet<>();
            Set<Distributor> dealers = new HashSet<>();
            Optional<User> user = userService.findUserById(id);
            if (user.isPresent()) {
                long orgId = (long) request.getSession().getAttribute("orgId");
                Optional<Organization> org = orgService.findById(orgId);
                if (org.isPresent()) {
                    if (user.get().getUserType().equals("ASM")) {
                        users = org.get().getUsers().stream().filter(p -> p.getUserType().equals("ASM"))
                                .collect(Collectors.toSet());
                        arealist = user.get().getAreas();
                        model.addAttribute("areas", arealist);
                        mv.setViewName("admin-pages/user-mapping/edit-map-user-to-area");
                    } else if (user.get().getUserType().equals("RSM")) {
                        users = org.get().getUsers().stream().filter(p -> p.getUserType().equals("RSM"))
                                .collect(Collectors.toSet());
                        regionlist = user.get().getRegions();

                        model.addAttribute("regions", regionlist);
                        mv.setViewName("admin-pages/user-mapping/edit-map-user-to-region");
                    } else if (user.get().getUserType().equals("TSM") || user.get().getUserType().equals("TSO")) {
                        users = org.get().getUsers().stream()
                                .filter(p -> p.getUserType().equals("TSM") || p.getUserType().equals("TSO"))
                                .collect(Collectors.toSet());
                        terlist = user.get().getTerritories();
                        model.addAttribute("territories", terlist);
                        mv.setViewName("admin-pages/user-mapping/edit-map-user-to-territory");
                    } else if (user.get().getUserType().equals("SR") || user.get().getUserType().equals("DSR")) {
                        users = org.get().getUsers().stream()
                                .filter(p -> p.getUserType().equals("SR") || p.getUserType().equals("DSR"))
                                .collect(Collectors.toSet());
                        mktlist = user.get().getMkts();
                        model.addAttribute("mktlist", mktlist);
                        mv.setViewName("admin-pages/user-mapping/edit-map-user-to-market");
                    }
                    else if (user.get().getUserType().equals("Distributor")) {
                        users = org.get().getUsers().stream()
                                .filter(p -> p.getUserType().equals("Distributor"))
                                .collect(Collectors.toSet());
                        dealers = org.get().getDistributors();
                        model.addAttribute("dealers", dealers);
                        mv.setViewName("admin-pages/user-mapping/edit-map-user-to-dealer");
                    }else {
                        mv.setViewName("admin-pages/user-mapping/error-home");
                    }
                }
            }
            mv.addObject("users", users);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
        }
        return mv;
    }

    @RequestMapping("/map-user-to-market")
    public ModelAndView mapUsreseMarket(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            Set<User> users = new HashSet<>();
            Set<Market> markets = new HashSet<>();

            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                users = org.get().getUsers().stream()
                        .filter(p -> p.getUserType().equals("SR") || p.getUserType().equals("DSR"))
                        .collect(Collectors.toSet());
                
                markets = org.get().getMkt();

            }
            mv.addObject("users", users);
            mv.addObject("markets", markets);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user-mapping/map-user-to-market");
        }
        return mv;
    }
}