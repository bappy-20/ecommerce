package com.inovex.main.controller.rest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.FirebaseToken;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Notification;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.NotificationResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.FirebaseTokenService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.NotificationService;
import com.inovex.main.service.UserService;

@RestController
@RequestMapping("/api")
public class NotificationRestController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    EmployeeService empService;
    @Autowired
    FirebaseTokenService firebaseTokenService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;
    @Autowired
    UserService userService;

    @GetMapping("/notification")
    public Set<Notification> getAllNotifications(HttpServletRequest request) {

        Set<Notification> notificationlist = new HashSet<Notification>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {

            try {
                notificationlist = org.get().getNotifications();
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
        return notificationlist;
    }

    @PostMapping("/notification")
    public ResponseData createNotification(@RequestBody Notification notification, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            Notification ntf = notificationService.save(notification, request);
            responseData.setData(ntf);
            responseData.setStatusCode(201);
            responseData.setMessage("Notification created successfully");

            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setData(null);
            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }

    }

    @GetMapping("/notification/{id}")
    public ResponseData getNotification(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Notification ntf = notificationService.getNotification(id);
            responseData.setData(ntf);
            responseData.setStatusCode(200);
            responseData.setMessage("ok");
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);

            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    @DeleteMapping("/delete-notification/{id}")
    public ResponseData deleteNotification(@PathVariable Long id, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            notificationService.deleteById(id, request);
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

    @PutMapping("/notification/{id}")
    public ResponseData updateNotification(@RequestBody Notification notification, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            Notification ntf = notificationService.update(notification, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(ntf);
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

//    @RequestMapping(value = "/send-notification/{flag}/{empId}/{notificationId}", method = RequestMethod.POST)
//    public ResponseEntity<?> notification(HttpServletRequest request, Model model, @PathVariable String flag,
//            @PathVariable String empId, @PathVariable String notificationId, Principal principal) {
//        String response = null;
//        System.out.println(flag);
//        if (!flag.isEmpty() || !flag.equals("")) {
//            if (flag.equals("employee")) {
//                System.out.println(flag);
//                Optional<FirebaseToken> token = firebaseTokenService.findByEmployeeId(empId);
//                if (token.isPresent()) {
//                    response = notificationService.sendMessageWithFile(notificationId, token.get().getToken());
//                } else {
//                    response = "Token not present";
//                }
//            } else {
//                if (flag.equals("all")) {
//                    List<Employee> empList = empService.getAll();
//                    if (!empList.isEmpty()) {
//                        for (Employee emp : empList) {
//                            Optional<FirebaseToken> token = firebaseTokenService.findByEmployeeId(emp.getEmployeeId());
//
//                            if (token.isPresent()) {
//                                response += notificationService.sendMessageWithFile(notificationId,
//                                        token.get().getToken());
//                            } else {
//                                response += "Token not present : " + emp.getEmployeeId();
//                            }
//                        }
//                    }
//                } else {
//                    List<Employee> empList = empService.getEmployeeListByCategory(Long.parseLong(empId));
//                    if (!empList.isEmpty()) {
//                        for (Employee emp : empList) {
//                            Optional<FirebaseToken> token = firebaseTokenService.findByEmployeeId(empId);
//                            if (token.isPresent()) {
//                                response += notificationService.sendMessageWithFile(notificationId,
//                                        token.get().getToken());
//                            } else {
//                                response += "Token not present " + emp.getEmployeeId();
//                            }
//                        }
//                    }
//                }
//
//            }
//        }
//        return new ResponseEntity(response, HttpStatus.OK);
//    }
    
    @RequestMapping(value = "/send-notification/{flag}/{empId}/{notificationId}", method = RequestMethod.POST)
    public ResponseEntity<?> notification(HttpServletRequest request, Model model, @PathVariable String flag,
            @PathVariable String empId, @PathVariable String notificationId, Principal principal) {
        String response = null;
        System.out.println(flag);
        if (!flag.isEmpty() || !flag.equals("")) {
            if (flag.equals("employee")) {
                System.out.println(flag);
                Optional<FirebaseToken> token = firebaseTokenService.findByEmployeeId(empId);
                if (token.isPresent()) {
                    response = notificationService.sendMessageWithFile(notificationId, token.get().getToken());
                } else {
                    response = "Token not present";
                }
            } else {
                if (flag.equals("all")) {
                   // List<Employee> empList = empService.getAll();
                    List<User> userList = userService.getAllUser();

                    if (!userList.isEmpty()) {
                        for (User emp : userList) {
                            Optional<FirebaseToken> token = firebaseTokenService.findByEmployeeId(String.valueOf(emp.getId()));

                            if (token.isPresent()) {
                                response += notificationService.sendMessageWithFile(notificationId,
                                        token.get().getToken());
                            } else {
                                response += "Token not present : " + emp.getId();
                            }
                        }
                    }
                } else {
                    //List<Employee> empList = empService.getEmployeeListByCategory(Long.parseLong(empId));
                    List<User> userList = userService.findByUserType(empId);//empService.getEmployeeListByCategory(Long.parseLong(empId));
                	if (!userList.isEmpty()) {
//                        for (Employee emp : empList) {
                		  for (User emp : userList) {
                            Optional<FirebaseToken> token = firebaseTokenService.findByEmployeeId(empId);
                            if (token.isPresent()) {
                                response += notificationService.sendMessageWithFile(notificationId,
                                        token.get().getToken());
                            } else {
                                response += "Token not present " + emp.getId();
                            }
                        }
                    }
                }

            }
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/notification-list-role")
    public List<NotificationResponse> getAllNotificationListRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<NotificationResponse> response = new ArrayList<NotificationResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "notification";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (Notification mn : org.get().getNotifications()) {
                        NotificationResponse res = new NotificationResponse();
                        res.setId(mn.getId());
                        res.setEmployeeId(mn.getEmployeeId());
                        res.setFileName(mn.getFileName());
                        res.setImei(mn.getImei());
                        res.setMessage(mn.getMessage());
                        res.setTitle(mn.getTitle());
                        res.setNotificationType(mn.getNotificationType());
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        response.add(res);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

}
