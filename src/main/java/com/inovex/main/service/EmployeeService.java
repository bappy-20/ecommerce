package com.inovex.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Employee;

public interface EmployeeService {
    
	public Employee saveEmployee(Employee employee, HttpServletRequest request);

    public List<Employee> getEmployeeIdList(String empCategory);

    public String findEmployeeName(String employeeId);

    public List<Employee> getAll();

    public Optional<Employee> findById(long id);

    Optional<Employee> findByEmployeeId(String employeeId);

    public List<Employee> getEmployeeIdListById(String employeeId);

    Optional<Employee> findEmployeeId(String employeeId, String password);

    public List<Employee> findEmployeeByDistName(String reportingId);

    Employee getEmployee(Long id);

    void deleteById(Long id, HttpServletRequest request);

    void resetEmployeePasswordById(String id, String pass);

    Employee update(Employee employee, long id, HttpServletRequest request);

    public List<Employee> getEmployeeListByCategory(long empCategory);

    public ArrayList<Object> getPagination(int start, int length, String searchParam);

    public Long getCountWithSearchParm(String searchParam);

    Employee inactiveEmployee(Long id);

}
