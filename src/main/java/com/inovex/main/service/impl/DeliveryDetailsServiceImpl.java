package com.inovex.main.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.DeliveryDetails;
import com.inovex.main.repository.DeliveryDetailsRepo;
import com.inovex.main.service.DeliveryDetailsService;

@Service
@Transactional
public class DeliveryDetailsServiceImpl implements DeliveryDetailsService {

    @Autowired
    DeliveryDetailsRepo deliveryDetailsRepo;

    @Override
    public Optional<DeliveryDetails> findById(Long id) {
        // TODO Auto-generated method stub
        return deliveryDetailsRepo.findById(id);
    }

    @Override
    public List<DeliveryDetails> findAll() {
        // TODO Auto-generated method stub
        return deliveryDetailsRepo.findAll();
    }

    @Override
    public DeliveryDetails save(DeliveryDetails entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return deliveryDetailsRepo.save(entity);
    }

    @Override
    public List<DeliveryDetails> saveAll(List<DeliveryDetails> entities) {
        // TODO Auto-generated method stub
        return deliveryDetailsRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        deliveryDetailsRepo.deleteById(id);
    }

    @Override
    public DeliveryDetails update(DeliveryDetails dayModel, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Object> findProductByDate(Date startDate, Date endDate) {
        // TODO Auto-generated method stub
        return deliveryDetailsRepo.findProductByDate(startDate, endDate);
    }

    @Override
    public List<Object> findTopSellingProduct() {
        // TODO Auto-generated method stub
        return deliveryDetailsRepo.findTopSellingProduct();
    }

}
