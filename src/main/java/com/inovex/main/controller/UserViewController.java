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
import com.inovex.main.entity.User;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.UserService;
import com.inovex.main.service.UserTypeService;

@Controller
@RequestMapping("/admin")

public class UserViewController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    UserTypeService userTypeService;
    @Autowired
    OrganizationService orgService;
    @Autowired
    UserService userService;

    @RequestMapping("/user")
    public ModelAndView getUser(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user/user-home");
        }
        return mv;

    }

    @RequestMapping("/add-user")
    public ModelAndView addUser(HttpServletRequest request, Model model) {

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
            mv.setViewName("admin-pages/user/user-add-form");
        }
        return mv;

    }

    @RequestMapping("/edit-user/{id}")
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
            mv.setViewName("admin-pages/user/user-edit-form");
        }
        return mv;

    }

    @RequestMapping("/user/changepassword")
    public ModelAndView editPassword(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user/password-edit-form");
        }
        return mv;

    }

    @RequestMapping("/user/myprofile")
    public ModelAndView getProfile(HttpServletRequest request, Model model) {

        ModelAndView mv = new ModelAndView();

        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            long id = (long) request.getSession().getAttribute("userId");
            model.addAttribute("id", id);
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user/user-profile");
        }
        return mv;

    }
    @RequestMapping("/user-1")
    public ModelAndView getUser1(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/user/user-home1");
        }
        return mv;

    }
    
    @RequestMapping("/user-profile/{id}")
    public ModelAndView getUserProfile(@PathVariable Long id,HttpServletRequest request, Model model) {
    	ModelAndView mv = new ModelAndView();
    	if (request.getSession().getAttribute("orgId") == null) {
			mv.setViewName("redirect:/login");
		} else {
			Optional<User> user = userService.findUserById(id);
			String profileImage = user.get().getProfileImage();
			mv.addObject("profileImage", profileImage);
			model.addAttribute("id",id);
			model.addAttribute("baseurl",baseurl);
			mv.setViewName("admin-pages/user/user-profile");
		}
		return mv;	
    }

}
