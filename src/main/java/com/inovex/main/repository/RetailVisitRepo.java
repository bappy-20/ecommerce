package com.inovex.main.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.RetailVisit;

@Repository
public interface RetailVisitRepo extends JpaRepository<RetailVisit, Long> {

    @Query("SELECT l FROM RetailVisit l where l.srId = :srId")
    List<RetailVisit> findAllByEmpId(@Param("srId") long srId);

    @Query("SELECT l FROM RetailVisit l where l.srId = :srId AND Date(l.createdAt)=current_date")
    List<RetailVisit> findAllByEmpIdAndCurDate(@Param("srId") String srId);

    @Query("select e from RetailVisit e where year(e.createdAt) = year(current_date) and "
            + " month(e.createdAt) = month(current_date) and e.srId=:employeeId")
    List<RetailVisit> findAllOfCurrentMonth(@Param("employeeId") String employeeId);

    @Query(value = "SELECT r FROM RetailVisit r WHERE r.srId= :employeeId AND DATE(r.createdAt) = :selectedDate")
    List<RetailVisit> getRetailVisitofAnEmployeeByName(@Param("employeeId") String employeeId,
            @Param("selectedDate") Date selectedDate);
}
