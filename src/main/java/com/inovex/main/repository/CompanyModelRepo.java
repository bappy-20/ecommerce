package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.CompanyModel;

@Repository
public interface CompanyModelRepo extends JpaRepository<CompanyModel, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_company_model where organizations_company_model.organization_id = ?1 and organizations_company_model.company_model_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long areaId);
}
