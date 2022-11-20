package com.inovex.main.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.ProductWiseCampaign;

@Repository
public interface ProductWiseCampaignRepository extends JpaRepository<ProductWiseCampaign, Long> {

    Optional<ProductWiseCampaign> findByProductId(Long productId);

    List<ProductWiseCampaign> findAllByProductId(Long productId);

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_product_wise_campaigns where organization_id =?1 and product_wise_campaigns_id = ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long retailId);

}
