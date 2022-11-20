package com.inovex.main.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Retail;

@Repository
public interface RetailRepo extends JpaRepository<Retail, Long> {
    @Query("SELECT l FROM Retail l where l.submittedBy = :submittedBy")
    List<Retail> findAllByEmpId(@Param("submittedBy") String submittedBy);

    @Query("SELECT l FROM Retail l where l.status ='Pending'")
    List<Retail> findAllPendingRetail();

    @Query("SELECT l FROM Retail l where l.status ='Approved'")
    List<Retail> findAllApprovedRetail();

    @Transactional
    @Modifying
    @Query(value = "delete from market_retails where market_retails.retails_id= ?1", nativeQuery = true)
    public int deleteRef(long id);
    @Transactional
    @Modifying
    @Query(value = "delete from organizations_retails where organization_id =?1 and retails_id = ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long retailId);
}
