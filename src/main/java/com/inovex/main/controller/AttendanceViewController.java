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

import com.inovex.main.entity.User;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.UserService;

@Controller
@RequestMapping("/admin")
public class AttendanceViewController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserService userService;

    @Value("${base.url}")
    private String baseurl;

    @RequestMapping("/attendance")
    public ModelAndView getAttendance(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/attendance/attendance-home");
        }
        return mv;

    }

    @RequestMapping("/attendance-dayWise")
    public ModelAndView addAttendanceDayWise(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        if (request.getSession().getAttribute("orgId") == null) {
            mv.setViewName("redirect:/login");
        } else {
            model.addAttribute("baseurl", baseurl);
            mv.setViewName("admin-pages/attendance/attendance-dayWise");
        }
        return mv;

    }

    @RequestMapping("/attendance-betweenTwoDate")
    public ModelAndView addAttendanceBetweenTwoDate(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        model.addAttribute("baseurl", baseurl);
        mv.setViewName("admin-pages/attendance/attendance-betweenTwoDate");
        return mv;

    }

    @RequestMapping("/attendance-ofAnEmployee/{employeeId}/{startDate}/{endDate}/{employeeName}")
    public ModelAndView addAttendanceOfAnEmployee(@PathVariable Long employeeId, @PathVariable String startDate,
            @PathVariable String endDate, @PathVariable String employeeName, HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("employeeName", employeeName);
        model.addAttribute("baseurl", baseurl);
        mv.setViewName("admin-pages/attendance/attendance-ofAnEmployee");
        return mv;

    }

    @RequestMapping("/attendance-ofEmployeeByName")
    public ModelAndView addAttendanceOfEmployeeByName(HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        List<User> userList = userService.getAllUser();
    	model.addAttribute("user", userList); 
        model.addAttribute("baseurl", baseurl);
        mv.setViewName("admin-pages/attendance/attendance-ofEmployeeByName");
        return mv;
    }

}