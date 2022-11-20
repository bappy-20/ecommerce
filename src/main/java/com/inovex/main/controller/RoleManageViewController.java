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
import com.inovex.main.entity.Role;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.UserTypeService;

@Controller
@RequestMapping("/admin")
/*
 * 1.first add role in the add form 2.add it into edit form 3.modify user.js
 * method setTheRoleValues 4.modify menu.js method createMenu 5.add menu id with
 * layout page 6.menu id must same with the manage role add form
 */

public class RoleManageViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    UserTypeService userTypeService;
    @Autowired
    OrganizationService orgService;

    @RequestMapping("/manage-role")
    public ModelAndView getUser(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {

            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user/user-role-home");
        }
        return mv;

    }

    @RequestMapping("/edit-role/{id}")
    public ModelAndView editUser(@PathVariable Long id, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<Role> list = new HashSet<>();
            if (org.isPresent()) {
                list = org.get().getRole();
            }
            model.addAttribute("roles", list);
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user/menu-assign-to-role-edit-form");
        }
        return mv;

    }

    @RequestMapping("/create-role")
    public ModelAndView assingMenu(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgService.findById(orgId);
            Set<Role> list = new HashSet<>();
            if (org.isPresent()) {
                list = org.get().getRole();
            }
            model.addAttribute("roles", list);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user/menu-assign-to-add-form-role");
        }
        return mv;

    }

}
