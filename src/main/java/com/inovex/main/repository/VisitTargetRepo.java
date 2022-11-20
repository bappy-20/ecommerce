package com.inovex.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.VisitTarget;

@Repository
public interface VisitTargetRepo extends JpaRepository<VisitTarget, Long> {

    @Query("select e from VisitTarget e where year(e.targetMonth) = year(current_date) and "
            + " month(e.targetMonth) = month(current_date) and e.empId=:employeeId")
    Optional<VisitTarget> findAllOfCurrentMonth(@Param("employeeId") String employeeId);

    @Query("select e from VisitTarget e where year(e.targetMonth) =?1 and MONTH(e.targetMonth) =?2 and "
            + " e.empId=?3")
    Optional<VisitTarget> findAllOfCurrentMonth(int targetYear, int targetMonth, String employeeId);
}
