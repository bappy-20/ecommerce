package com.inovex.main.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.DistributorRequisition;

@Repository
public interface DistributorRequisitionRepo extends JpaRepository<DistributorRequisition, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_distributor_requisition where organizations_distributor_requisition.organization_id = ?1 and organizations_distributor_requisition.distributor_requisition_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long reqId);

    @Query(value = "SELECT * FROM distributor_requisition where dealer_id = ?1", nativeQuery = true)
    public List<DistributorRequisition> findByDealerId(long delaerId);
}
