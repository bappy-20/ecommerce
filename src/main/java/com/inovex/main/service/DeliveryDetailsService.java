package com.inovex.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.DeliveryDetails;

public interface DeliveryDetailsService {

    Optional<DeliveryDetails> findById(Long id);

    List<DeliveryDetails> findAll();

    DeliveryDetails save(DeliveryDetails entity, HttpServletRequest request);

    List<DeliveryDetails> saveAll(List<DeliveryDetails> entities);

    void deleteById(Long id);

    DeliveryDetails update(DeliveryDetails dayModel, Long id, HttpServletRequest request);

    public List<Object> findProductByDate(Date startDate, Date endDate);

    public List<Object> findTopSellingProduct();
}
