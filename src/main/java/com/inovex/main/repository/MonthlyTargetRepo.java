package com.inovex.main.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.MonthlyTarget;

@Repository
public interface MonthlyTargetRepo extends JpaRepository<MonthlyTarget, Long> {

	@Query("select e from MonthlyTarget e where year(e.targetMonth) = year(current_date) and "
			+ " month(e.targetMonth) = month(current_date) and e.empId=:employeeId and e.orgId=:orgId")
	List<MonthlyTarget> findAllOfCurrentMonth(@Param("employeeId") String employeeId, @Param("orgId") Long orgId);

	@Query(value = "select e.emp_id,sum(e.quantity),sum(e.total_value),e.target_month from monthly_target as e,organizations_monthly_targets where "
			+ "year(e.target_month) = year(current_date) and " + " month(e.target_month) = month(current_date) "
			+ " and organizations_monthly_targets.monthly_targets_id=e.id "
			+ "and organizations_monthly_targets.organization_id=?1 group by e.emp_id,e.target_month", nativeQuery = true)
	List<Object> findAllEmployeeOfCurrentMonth(long orgId);

	@Query("select e from MonthlyTarget e where year(e.targetMonth) =?1 and MONTH(e.targetMonth) =?2 and "
			+ " e.productName=?3 and e.empId=?4")
	Optional<MonthlyTarget> findExistorNotOfCurrentMonth(int targetYear, int targetMonth, String productName,
			String employeeId);

	@Transactional
	@Modifying
	@Query(value = "delete from organizations_monthly_targets where organization_id =?1 and monthly_targets_id = ?2", nativeQuery = true)
	public int deleteFromOrg(long orgId, long montargId);
		
	@Query(value ="select e.emp_id,sum(e.quantity),sum(e.total_value),month(e.target_month) from monthly_target as e,organizations_monthly_targets where EXTRACT(YEAR FROM target_month) = ?1 and EXTRACT(month FROM target_month) = ?2 and organizations_monthly_targets.monthly_targets_id = e.id and organizations_monthly_targets.organization_id = ?3 group by e.emp_id,e.target_month", nativeQuery = true)
	List<Object> findAllEmployeeOfSelectedMonth7(String year,String month,long orgId);
	

}
