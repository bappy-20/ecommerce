package com.inovex.main.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.DayModel;

@Repository
public interface DayModelRepo extends JpaRepository<DayModel, Long> {

    @Query("SELECT l FROM DayModel l where l.empId = :empId")
    List<DayModel> getDayListByEmpId(@Param("empId") String empId);
    @Transactional
    @Modifying
    @Query(value = "delete from organizations_day_models where organizations_day_models.organization_id =?1 and day_models_id =?2 ",nativeQuery = true)
    public int deleteFromOrg(long orgId, long dayId);
}
