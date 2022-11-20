package com.inovex.main.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.BrandModel;

@Repository
public interface BrandModelRepo extends JpaRepository<BrandModel, Long> {
    List<BrandModel> findAllByCompanyId(Long companyId);

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_brand_models where organizations_brand_models.organization_id = ?1 and brand_models_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long brandModelId);

    @Transactional
    @Modifying
    @Query(value = "delete FROM company_model_brand_model where brand_model_id = ?1", nativeQuery = true)
    public int deleteFromCompanyBrand(long brandModelId);

}
