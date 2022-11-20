package com.inovex.main.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.LeaveModel;

@Repository
public interface LeaveRepo extends JpaRepository<LeaveModel, Long> {
    @Query("SELECT l FROM LeaveModel l where l.employeeId = :submittedBy")
    List<LeaveModel> findAllByEmpId(@Param("submittedBy") long submittedBy);

    @Query(value = "SELECT l FROM LeaveModel l WHERE l.status='Pending' ")
    List<LeaveModel> getPendingLeave();

    @Query(value = "SELECT l FROM LeaveModel l WHERE l.status='Approved' ")
    List<LeaveModel> getApprovedLeave();

    @Transactional
    @Modifying
    @Query(value = "delete FROM organizations_leave_model where organization_id = ?1 and leave_model_id = ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long leaveId);
}
