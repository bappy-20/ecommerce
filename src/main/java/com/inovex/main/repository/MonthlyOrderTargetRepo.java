package com.inovex.main.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.MonthlyOrderTarget;

@Repository
public interface MonthlyOrderTargetRepo extends JpaRepository<MonthlyOrderTarget, Long> {
    @Query("select e from MonthlyOrderTarget e where year(e.targetMonth) =?1 and MONTH(e.targetMonth) =?2 and e.empId=?3")
    Optional<MonthlyOrderTarget> findAllOfCurrentMonth(int targetYear, int targetMonth1, String employeeId);

    @Query("select e from MonthlyOrderTarget e where year(e.targetMonth) =year(current_date) and"
            + " MONTH(e.targetMonth) =MONTH(current_date) and e.orgId=?1")
    List<MonthlyOrderTarget> findAllByCurMonth(long orgId);

    @Query("select e from MonthlyOrderTarget e where year(e.targetMonth) =?1 and MONTH(e.targetMonth) =?2")
    List<MonthlyOrderTarget> findAllByMonth(int targetYear, int targetMonth1);

    @Query("select e from MonthlyOrderTarget e where year(e.targetMonth) =year(current_date) and MONTH(e.targetMonth) =MONTH(current_date) and e.empId=?1")
    Optional<MonthlyOrderTarget> findAllByCurMonthAndEmpId(String employeeId);
   
    @Query(value ="select e.emp_id,e.emp_name,(e.order_quantity),sum(e.order_value),month(e.target_month) from monthly_order_target as e,organizations_monthly_order_targets where EXTRACT(YEAR FROM target_month) = ?1 and EXTRACT(month FROM target_month) = ?2 and organizations_monthly_order_targets.monthly_order_targets_id = e.id and organizations_monthly_order_targets.organization_id = ?3 group by e.emp_id,e.target_month", nativeQuery = true)
	List<Object> findAllDeliveryExecutiveOfSelectedMonth7(String year,String month,long orgId);
    
    @Transactional
    @Modifying
    @Query(value = "delete FROM organizations_monthly_order_targets where organization_id = ?1 and monthly_order_targets_id = ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long monthlyOrderTargetId);
    
    

    
}
