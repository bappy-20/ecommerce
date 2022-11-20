package com.inovex.main.controller.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.inovex.main.entity.Attendance;
import com.inovex.main.entity.Employee;
import com.inovex.main.repository.AttendanceRepo;
import com.inovex.main.service.EmployeeService;

@Configuration
@EnableScheduling
public class SchedularController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    EmployeeService empService;
    @Autowired
    AttendanceRepo attendanceRepo;

    @Scheduled(cron = "0 30 18 ? * *")
    public void scheduleFixedDelayTask() throws FileNotFoundException, IOException {
        System.out.println("called");
        List<Employee> findAll = empService.getAll();
        for (Employee employee : findAll) {
            Optional<Attendance> checkExistorNot = attendanceRepo.findByEmpIdAndLogDate(employee.getEmployeeId());
            if (!checkExistorNot.isPresent()) {
                Attendance entity = new Attendance();
                entity.setEmployeeId(employee.getEmployeeId());
                entity.setStatus("Absent");
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy(1);
                attendanceRepo.save(entity);
            }
        }

    }

}
