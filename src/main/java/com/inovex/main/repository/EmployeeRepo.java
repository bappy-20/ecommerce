package com.inovex.main.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeId(String employeeId);

    @Query("SELECT l FROM Employee l where l.empCategory = :empCategory")
    public List<Employee> getEmployeeIdList(@Param("empCategory") String empCategory);

    @Query("SELECT l FROM Employee l where l.empCategory = :empCategory")
    public List<Employee> getEmployeeListByCategory(@Param("empCategory") long empCategory);

    @Query("SELECT l FROM Employee l where l.employeeId = :employeeId")
    public List<Employee> getEmployeeIdListById(@Param("employeeId") String employeeId);

    @Query("SELECT l.empName FROM Employee l where l.employeeId = :employeeId ")
    public String findEmployeeName(@Param("employeeId") String employeeId);

    @Query("SELECT l.employeeId,l.empName FROM Employee l where l.reportingId = :reportingId ")
    public List<Employee> findEmployeeByDistName(@Param("reportingId") String reportingId);

    @Query("SELECT l FROM Employee l where l.employeeId = :employeeId AND l.password = :password")
    Optional<Employee> findEmployeeId(@Param("employeeId") String employeeId, @Param("password") String password);

    // server side pagination
    @Query(value = "SELECT employee.id,employee.emp_name,employee.employee_id"
            + " FROM employee inner join usertype on employee.emp_category=usertype.id "
            + "and usertype.user_type='DE' LIMIT ?1,?2", nativeQuery = true)
    public ArrayList<Object> getPagination(int start, int length);

    @Query(value = "SELECT employee.id,employee.emp_name,employee.employee_id "
            + " FROM employee inner join usertype on employee.emp_category=usertype.id and usertype.user_type='DE' "
            + " WHERE " + " employee.id like ?1%" + " OR employee.emp_name like ?1%"
            + " OR employee.employee_id like ?1%  " + " LIMIT ?2,?3", nativeQuery = true)
    public ArrayList<Object> getPaginationWithSerachParam(String query, int start, int length);

    @Query(value = "select count(*) from employee " + " inner join usertype on employee.emp_category=usertype.id"
            + " and usertype.user_type='DE' " + " WHERE " + " employee.id like ?1%" + " OR employee.emp_name like ?1%"
            + " OR employee.employee_id like ?1% ", nativeQuery = true)
    public Long countBySearchParam(String searchParam);

    @Query(value = "select count(*) from employee", nativeQuery = true)
    public long count();
    
    @Transactional
    @Modifying
    @Query(value = "delete from distributor_employess where distributor_employess.distributor_id = ?1 "
            + "and distributor_employess.employess_id= ?2", nativeQuery = true)
    public int deleteRef(long distId, long empId);
    
    @Transactional
    @Modifying
    @Query(value = "delete from organizations_employess where organization_id = ?1 and employess_id =?2",nativeQuery = true)
    public int deleteFromOrg(long orgId, long empId);
    
  
    
}
