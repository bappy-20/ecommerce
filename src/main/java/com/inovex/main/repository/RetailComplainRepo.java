package com.inovex.main.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.RetailComplain;

@Repository
public interface RetailComplainRepo extends JpaRepository<RetailComplain, Long> {

    List<RetailComplain> findAllByEmployeeId(String employeeId);
    
    @Transactional
    @Modifying
    @Query(value = "delete from organizations_retail_complains where organization_id =?1 and retail_complains_id = ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long retailComplainId);
}
