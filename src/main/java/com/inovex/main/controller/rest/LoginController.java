package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.entity.Password;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.SecurityService;
import com.inovex.main.service.UserService;

/**
 * LoginController class
 *
 * @author Faruk
 * @author rabiul
 *
 */
@RestController
public class LoginController {

    @Value("${base.url}")
    private String baseurl;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = { "/" }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/api/user-by-type/{userType}")
    public List<User> getUserByType(@PathVariable String userType) {

        List<User> user = new ArrayList<User>();
        System.out.println("userType " + userType);
        try {
            user = userService.findByUserType(userType);

        } catch (Exception e) {

        }
        return user;

    }

    @RequestMapping(value = { "/login" }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     * Method: loginSubmit, Type: POST
     *
     * @param loginForm
     * @param BindingResult
     * @param Model
     * @return
     */

    /**
     * Method: loginSubmit, Type: POST
     *
     * @param loginForm
     * @param BindingResult
     * @param Model
     * @return
     */
    @RequestMapping(value = "/loginSubmit", method = RequestMethod.POST)
    public ModelAndView loginSubmit(@ModelAttribute("loginForm") User loginForm, BindingResult bindingResult,
            Model model, HttpServletRequest request) throws Exception {
        Optional<User> user = userService.findByUsername(loginForm.getUsername());
        //loginForm.getOr
        ModelAndView map = new ModelAndView();
        String message;
        if (user.isPresent()) {
            boolean login = securityService.autologin(loginForm.getUsername(), loginForm.getPassword());
            Long orgId = userService.getOrganizationByUser(user.get());
            if (login) {
                request.getSession().setAttribute("role", user.get().getUserType());
                request.getSession().setAttribute("orgId", orgId);
                request.getSession().setAttribute("distId", Long.parseLong("295"));
                request.getSession().setAttribute("userId", user.get().getId());
                request.getSession().setAttribute("username", user.get().getUsername());
                request.getSession().setAttribute("img", user.get().getProfileImage());
                request.getSession().setAttribute("dealerid", user.get().getDealerId());
                request.getSession().setAttribute("userType", user.get().getUserType());
                request.getSession().setAttribute("dealeriiiid", Long.parseLong("91"));
                
                if (request.isUserInRole("ROLE_USER")) {
                    return new ModelAndView("redirect:" + "/admin/home");
                } else {
                    return new ModelAndView("redirect:" + "/admin/home");
                }
            }
        }
        message = "Incorrect! Username or Password for your organization";
        map.addObject("message", message);
        map.setViewName("login");

        return map;
    }

    @GetMapping(value = "/users")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User users = new User();
        modelAndView.addObject("user", users);
        modelAndView.setViewName("admin/registration");
        return modelAndView;
    }

    @PostMapping(value = "/api/user/registration")
    public @ResponseBody User createUser(@RequestBody User user, HttpServletRequest request) throws Exception {
        System.out.println("getEmail " + user.getEmail());

        Optional<User> userExists = userService.findByUsername(user.getUsername());
        if (userExists.isPresent()) {
            return userExists.get();
        }
        user = userService.save(user, request);
        return user;
    }

//    @PostMapping("/api/user/registration1")
//    public ResponseData createUser1(@RequestBody User user, HttpServletRequest request) {
//        ResponseData responseData = new ResponseData();
//        try {
//        	User usr = userService.saveUser(user, request);
//            responseData.setData(usr);
//            responseData.setStatusCode(201);
//            responseData.setMessage("User created successfully");
//
//            return responseData;
//        } catch (Exception e) {
//            e.printStackTrace();
//            responseData.setData(null);
//            responseData.setMessage(e.getMessage());
//            responseData.setStatusCode(500);
//            responseData.setMessage(e.getMessage());
//
//            return responseData;
//        }
//    }
//

    @PostMapping(value = "/api/user")
    public @ResponseBody Object login(@RequestBody User user, HttpServletRequest request) throws Exception {
        Optional<User> userExist = userService.findByUsername(user.getUsername());
        if (userExist.isPresent()) {
            boolean login = securityService.autologin(user.getUsername(), user.getPassword());
            if (login) {
                if (request.isUserInRole("ROLE_ADMIN")) {
                    return "Admin login successfull";
                } else {
                    return "User login successfull";
                }
            }
        }
        return "Login not successfull";
    }

    // **r4tu1**

    @PutMapping("/api/user/update/{id}")
    public ResponseData updateUser(@RequestBody User user, @PathVariable Long id, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        System.out.println("called !" + id);

        try {
            User data = userService.updateUser(user, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(data);

            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }

    }

    @DeleteMapping("/api/user/delete/{id}")
    public ResponseData deleteUser(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            userService.deleteUser(id, request);
            responseData.setStatusCode(204);
            responseData.setMessage("delete successfully");
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    @GetMapping("/api/user")
    public List<User> getAllUser(HttpServletRequest request) {

        List<User> user = new ArrayList<User>();
        try {
            user = userService.getAllUser();
        } catch (Exception e) {

        }
        return user;

    }

    @GetMapping("/api/user/{id}")
    public User getUser(@PathVariable Long id) {

        User user = new User();
        try {
            user = userService.getUser(id);

        } catch (Exception e) {

        }
        return user;
    }

    @PutMapping("/api/user/update/password")
    public ResponseData updatePassword(@RequestBody Password password, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        System.out.println("called !");

        try {
            Password data = userService.updateUserPassword(password, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(data);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }

    }

    @GetMapping("/api/user/myprofile")
    public User getUserProfile(HttpServletRequest request) {
        User user = new User();
        try {
            if (request.getSession().getAttribute("userId") != null) {

                long id = (long) request.getSession().getAttribute("userId");
                user = userService.getUser(id);

                if (user != null) {
                    System.out.println("org is not null");
                }
            }

        } catch (Exception e) {

        }
        return user;

    }

}
