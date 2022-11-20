package com.inovex.main.controller.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.Attendance;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.AttendanceOfAnEmployeeResponse;
import com.inovex.main.json.response.AttendanceResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.service.AttendanceService;
import com.inovex.main.service.CategoryService;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.UserService;
import com.inovex.main.service.UserTypeService;

@RestController
@RequestMapping("/api")
public class AttendanceRestController {
	@Autowired
	AttendanceService attendanceService;
	@Autowired
	EmployeeService empService;
	@Autowired
	CategoryService catService;
	@Autowired
	UserService userService;
	@Autowired
	UserTypeService usertypeService;

	@GetMapping("/attendance-daywise/{date1}")
	public List<AttendanceOfAnEmployeeResponse> getAttendanceDayWise(@PathVariable String date1) {
		ResponseData responseData = new ResponseData();
		List<Attendance> attn = new ArrayList<>();
		List<AttendanceOfAnEmployeeResponse> attResList = new ArrayList<>();
		try {
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
			Date selectedDate = df2.parse(date1);
			attn = attendanceService.getAttendanceDayWise(selectedDate);
			long serial = 1;
			for (Attendance attendance : attn) {
				AttendanceOfAnEmployeeResponse att = new AttendanceOfAnEmployeeResponse();
				att.setId(serial);
				att.setEmployeeId(attendance.getEmployeeId());
				String userName = attendance.getEmployeeId();
				Optional<User> emp = userService.findByUsername(userName);
				if (emp.isPresent()) {
					att.setEmployeeName(emp.get().getFullName());
				} else {
					att.setEmployeeName("Not Found");
				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (attendance.getInTime() != null) {
					String in = df.format(attendance.getInTime());
					att.setInTime(in);
				}
				if (attendance.getOutTime() != null) {
					String out = df.format(attendance.getOutTime());
					att.setOutTime(out);
				}
				att.setAddressIn(attendance.getCheckInAddress());
				att.setAddressOut(attendance.getCheckOutAddress());
				att.setStatus(attendance.getStatus());
				attResList.add(att);

				serial++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseData.setData(null);

			responseData.setMessage(e.getMessage());
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());

		}
		return attResList;
	}

	@GetMapping("/attendance-today")
	public List<Attendance> getTodayAttendance() {
		List<Attendance> attn = new ArrayList<>();
		try {
			attn = attendanceService.findAllByCurDate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attn;
	}

	@GetMapping("/attendance-between-twodate/{date1}/{date2}")
	public List<Attendance> getAttendanceBetweentwoDate(@PathVariable String date1, @PathVariable String date2) {
		ResponseData responseData = new ResponseData();
		List<Attendance> attn = new ArrayList<>();
		try {
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
			Date stDate = df2.parse(date1);
			Date enDate = df2.parse(date2);

			attn = attendanceService.getAttendanceBetweenDates(stDate, enDate);

		} catch (Exception e) {
			responseData.setData(null);

			responseData.setMessage(e.getMessage());
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());

		}
		return attn;
	}

	@GetMapping("/attendance-between-twodate1/{date1}/{date2}")
	public List<AttendanceResponse> getAttendanceBetweentwoDate1(@PathVariable String date1, @PathVariable String date2,
			HttpServletRequest request) {
		List<AttendanceResponse> responseList = new ArrayList<>();
		List<Object> attn = new ArrayList<>();
		try {
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
			Date stDate = df2.parse(date1);
			Date enDate = df2.parse(date2);
			attn = attendanceService.getAllEmployeeAttendanceSummeryByDate(stDate, enDate, request);
			Iterator itr = attn.iterator();
			long count = 1;
			while (itr.hasNext()) {
				AttendanceResponse response = new AttendanceResponse();
				Object[] obj = (Object[]) itr.next();
				response.setId(count);
				response.setEmployeeId(String.valueOf(obj[0]));
				String userName = String.valueOf(obj[0]);
				Optional<User> emp = userService.findByUsername(userName);
				if (emp.isPresent()) {
					response.setEmpName(emp.get().getFullName());
					response.setEmpCategory(emp.get().getUserType());
				} else {
					response.setEmpName("Emp Not Found");
					response.setEmpCategory("Emp Not Found");
				}
				response.setTotal(Long.parseLong(String.valueOf(obj[1])));
				response.setPresent(Long.parseLong(String.valueOf(obj[2])));
				response.setAbsent(Long.parseLong(String.valueOf(obj[3])));
				response.setLatIn(Long.parseLong(String.valueOf(obj[6])));
				response.setEarlyOut(Long.parseLong(String.valueOf(obj[5])));
				response.setInleave(Long.parseLong(String.valueOf(obj[4])));
				responseList.add(response);
				count++;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return responseList;
	}

	@GetMapping("/attendance-ofAnEmployee/{employeeId}/{date1}/{date2}")
	public List<Attendance> getAttendanceOfAnEmployee(@PathVariable String employeeId, @PathVariable String date1,
			@PathVariable String date2) {
		ResponseData responseData = new ResponseData();
		List<Attendance> attn = new ArrayList<>();
		try {
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
			Date stDate = df2.parse(date1);
			Date enDate = df2.parse(date2);
			System.out.println(stDate + " " + stDate + " " + employeeId);
			attn = attendanceService.getAttendanceOfAnEmployee(employeeId, stDate, enDate);
			// attn=attendanceService.getAttendanceOfAnEmployee(employeeId, stDate,
			// enDate,employeeName);
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setData(null);

			responseData.setMessage(e.getMessage());
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());
		}
		return attn;
	}

	@GetMapping("/attendance-ofAnEmployeeByName/{employeeId}/{date1}/{date2}")
	public List<Attendance> getAttendanceOfAnEmployeeByName(@PathVariable String employeeId, @PathVariable String date1,
			@PathVariable String date2) {
		ResponseData responseData = new ResponseData();
		List<Attendance> attn = new ArrayList<>();
		try {
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
			Date stDate = df2.parse(date1);
			Date enDate = df2.parse(date2);
			attn = attendanceService.getAttendanceOfAnEmployeeByName(employeeId, stDate, enDate);

		} catch (Exception e) {
			responseData.setData(null);
			e.printStackTrace();
			responseData.setMessage(e.getMessage());
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());
		}
		return attn;
	}

	@GetMapping("/attendance-summery/{date1}")
	public List<Object> getAttendanceSummery(@PathVariable String date1) {
		List<Object> attn = new ArrayList<>();
		try {
			attn = attendanceService.getAttendanceSummeryByADate(date1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return attn;
	}

}
