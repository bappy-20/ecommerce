package com.inovex.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Employee;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.EmployeeRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.OrganizationService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepo employeerepo;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    OrganizationService organizationService;

    @Override
    public Employee saveEmployee(Employee employee, HttpServletRequest request) {
    	
        Employee emp = new Employee();
        if (request.getSession().getAttribute("orgId") != null) {
        
            long id =  (long) request.getSession().getAttribute("orgId");
            System.out.println("######"+id);
            Optional<Organization> org = orgRepo.findById(id);
            Set<Employee> list = new HashSet<Employee>();
            if (org.isPresent()) {
                list = org.get().getEmployess();
                employee.setCreatedAt(new Date());
                employee.setActive(true);
                employee.setUpdatedAt(new Date());
                employee.setActiveStatus("Active");
                /* entity.setCreatedBy((long) request.getSession().getAttribute("userId")); */
                employee.setCreatedBy((long) request.getSession().getAttribute("userId"));
                emp = employeerepo.save(employee);
                list.add(emp); 
                org.get().setEmployess(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("OrgId is null");
        }
        return emp;

    }

    @Override
    public List<Employee> getEmployeeIdList(String empCategory) {

        return employeerepo.getEmployeeIdList(empCategory);
    }

    @Override
    public String findEmployeeName(String employeeId) {

        return employeerepo.findEmployeeName(employeeId);
    }

    @Override
    public List<Employee> getAll() {
        // TODO Auto-generated method stub
        return employeerepo.findAll();
    }

    @Override
    public List<Employee> getEmployeeIdListById(String employeeId) {
        // TODO Auto-generated method stub
        return employeerepo.getEmployeeIdListById(employeeId);
    }

    @Override
    public List<Employee> findEmployeeByDistName(String reportingId) {
        // TODO Auto-generated method stub
        return employeerepo.findEmployeeByDistName(reportingId);
    }

    @Override
    public Optional<Employee> findById(long id) {
        // TODO Auto-generated method stub
        return employeerepo.findById(id);
    }

    @Override
    public Optional<Employee> findEmployeeId(String employeeId, String password) {
        // TODO Auto-generated method stub
        return employeerepo.findEmployeeId(employeeId, password);
    }

    @Override
    public Employee getEmployee(Long id) {
        Optional<Employee> employee = employeerepo.findById(id);
        if (employee.isPresent())
            return employee.get();
        throw new NotFoundException();
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {
    	if (request.getSession().getAttribute("orgId")!=null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
            	employeerepo.deleteFromOrg(orgid, id);
            	employeerepo.deleteById(id);
			} else {
				System.out.println("org not found");
			}
        }

    }

    @Override
    public Employee update(Employee employee, long id, HttpServletRequest request) {
        Optional<Employee> employeeUpdate = employeerepo.findById(id);
        Employee emp1 = new Employee();
        if (employeeUpdate.isPresent()) {
            employeeUpdate.get().setEmployeeId(employee.getEmployeeId());
            employeeUpdate.get().setEmpName(employee.getEmpName());
            employeeUpdate.get().setEmpAddress(employee.getEmpAddress());
            employeeUpdate.get().setEmpPhone(employee.getEmpPhone());
            employeeUpdate.get().setEmpCategory(employee.getEmpCategory());
            employeeUpdate.get().setReportingId(employee.getReportingId());
            employeeUpdate.get().setEmpImage(employee.getEmpImage());
            employeeUpdate.get().setFatherName(employee.getFatherName());
            employeeUpdate.get().setMotherName(employee.getMotherName());
            employeeUpdate.get().setEmpNid(employee.getEmpNid());
            employeeUpdate.get().setGeneralBankInfo(employee.getGeneralBankInfo());
            employeeUpdate.get().setMobileBankInfo(employee.getMobileBankInfo());
            employeeUpdate.get().setEmergencyContact(employee.getEmergencyContact());
            employeeUpdate.get().setUpdatedAt(new Date());
            employeeUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));

            emp1 = employeerepo.save(employeeUpdate.get());
            return emp1;
        } else {
            return emp1;
        }

    }

    @Override
    public Optional<Employee> findByEmployeeId(String employeeId) {
        // TODO Auto-generated method stub
        return employeerepo.findByEmployeeId(employeeId);
    }

    @Override
    public List<Employee> getEmployeeListByCategory(long empCategory) {
        // TODO Auto-generated method stub
        return employeerepo.getEmployeeListByCategory(empCategory);
    }

    @Override
    public ArrayList<Object> getPagination(int start, int length, String searchParam) {
        if (searchParam == null || searchParam.isEmpty()) {
            return employeerepo.getPagination(start, length);
        } else {
            return employeerepo.getPaginationWithSerachParam(searchParam, start, length);
        }
    }

    @Override
    public Long getCountWithSearchParm(String searchParam) {
        if (searchParam == null || searchParam.isEmpty()) {
            return employeerepo.count();
        } else {

            return employeerepo.countBySearchParam(searchParam);

        }
    }

    @Override
    public void resetEmployeePasswordById(String id, String pass) {
        Optional<Employee> employeeUpdate = employeerepo.findByEmployeeId(id);
        if (employeeUpdate.isPresent()) {
            employeeUpdate.get().setPassword(pass);
            employeerepo.save(employeeUpdate.get());
        }

    }

    @Override
    public Employee inactiveEmployee(Long id) {
        // TODO Auto-generated method stub
        Optional<Employee> inactiveEmployee = employeerepo.findById(id);
        inactiveEmployee.get().setActiveStatus("Inactive");
        inactiveEmployee.get().setPassword("000000");
        employeerepo.save(inactiveEmployee.get());
        return inactiveEmployee.get();
    }

}
