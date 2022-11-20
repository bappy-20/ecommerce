package com.inovex.main.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.ProductWiseCampaign;
import com.inovex.main.json.response.ProductWiseCampaignResponse;

public interface ProductWiseCampaignService {

    Optional<ProductWiseCampaign> findById(long id);

    Optional<ProductWiseCampaign> findByProductId(long productId);

    List<ProductWiseCampaign> findAllByProductId(Long productId);

    void deleteById(long id);

    Set<ProductWiseCampaignResponse> getAvailableCashBack(HttpServletRequest request);

    List<ProductWiseCampaign> findAll();

    ProductWiseCampaign getCampaign(long id);

    void deleteById(long id, HttpServletRequest request);

    ProductWiseCampaign save(ProductWiseCampaign entity, HttpServletRequest request);

    ProductWiseCampaign update(ProductWiseCampaign productWiseCampaign, Long id, HttpServletRequest request);

    List<ProductWiseCampaign> saveListOfCampaign(List<ProductWiseCampaign> entities, HttpServletRequest request);
}
