package com.inovex.main.controller.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.Location;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.LocationRes;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.LocationService;
import com.inovex.main.service.UserService;

@RestController
@RequestMapping("/api")
public class LocationRestController {

    @Autowired
    LocationService locationService;
    @Autowired
    EmployeeService empService;
    @Autowired
    UserService userService;

    @GetMapping("/all-location/{employeeId}/{date1}")
    public List<Location> getAllLocationOfAnEmployeeByName(@PathVariable String employeeId,
            @PathVariable String date1) {
        List<Location> attn = new ArrayList<>();
        try {
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = df2.parse(date1);
            attn = locationService.getLocationOfAnEmployeeByName(employeeId, stDate);
        } catch (Exception e) {

            e.printStackTrace();

        }
        return attn;
    }

    @GetMapping("/location-ofAnEmployeeByName/{employeeId}/{date1}")
    public ResponseData getLocationOfAnEmployeeByName(@PathVariable String employeeId, @PathVariable String date1) {
        ResponseData responseData = new ResponseData();
        List<Location> attn = new ArrayList<>();
        List<String> latLongList = new ArrayList<>();
        try {
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = df2.parse(date1);
            attn = locationService.getLocationOfAnEmployeeByName(employeeId, stDate);
            boolean i = true;
            int count = 1;
            int size = attn.size();
            System.out.println("size " + size);
            int size1 = 0;
            for (Location loc : attn) {

                if (i) {
                    System.out.println("first element ");
                    latLongList.add(loc.getLatitude() + "," + loc.getLongitude());
                    i = false;
                }
                if (count == 10) {
                    count = 1;
                    latLongList.add(loc.getLatitude() + "," + loc.getLongitude());
                }

                size1++;
                if (size == size1) {
                    System.out.println("size " + size + " size1 " + size1);
                    latLongList.add(loc.getLatitude() + "," + loc.getLongitude());
                }
                count++;
            }
            System.out.println("size1 " + size1);
            responseData.setData(latLongList);
            responseData.setMessage("Data found");
            responseData.setStatusCode(200);
            responseData.setMessage("Data found success");
        } catch (Exception e) {
            responseData.setData(null);
            e.printStackTrace();
            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }

    @GetMapping("/emp-location-by-maxtime")
    public ResponseData getCarLocationByMaxTime() {
        ResponseData responseData = new ResponseData();
        try {

            List<Object> location = locationService.getLocationsBymaxTime();
            List<LocationRes> loc = new ArrayList<LocationRes>();
            Iterator itr = location.iterator();
            while (itr.hasNext()) {
                LocationRes res = new LocationRes();
                Object[] obj = (Object[]) itr.next();
                if (!String.valueOf(obj[7]).equals("") && !String.valueOf(obj[7]).equals(null)) {
                	String userId = String.valueOf(obj[7]);
                	System.out.println(userId);
                	long userid = Long.parseLong(userId);
                	Optional<User> user = userService.findUserById(userid);
                	if (user.isPresent()) {
                		res.setEmpName(user.get().getFullName());
                	}
                	else {
                		res.setEmpName("Not Found");
					}
						
					} else {
						 res.setEmpName("ID not found");

					}
                res.setLat(String.valueOf(obj[8]));
                res.setLng(String.valueOf(obj[9]));
                loc.add(res);
            }

            responseData.setData(loc);
            responseData.setMessage("Ok");
            responseData.setStatusCode(200);

            return responseData;
        } catch (Exception e) {
            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setData(null);

            return responseData;
        }
    }

}
