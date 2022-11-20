package com.inovex.main.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.OrderEcommerceModel;
import com.inovex.main.repository.OrderModelRepo;
import com.inovex.main.service.OrderModelService;

@Service
@Transactional
public class OrderModelServiceImpl implements OrderModelService {
	
	@Autowired
    OrderModelRepo orderRepo;

    @Override
    public Optional<OrderEcommerceModel> findById(Long id) {
        // TODO Auto-generated method stub
        return orderRepo.findById(id);
    }

    @Override
    public List<OrderEcommerceModel> findAll() {
        // TODO Auto-generated method stub
        return orderRepo.findAll();
    }

    @Override
    public OrderEcommerceModel save(OrderEcommerceModel entity) {
        // TODO Auto-generated method stub
        entity.setActive(true);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        entity.setCreatedBy(1);
        return orderRepo.save(entity);
    }

    @Override
    public OrderEcommerceModel update(OrderEcommerceModel entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<OrderEcommerceModel> saveAll(List<OrderEcommerceModel> entities) {
        // TODO Auto-generated method stub
        return orderRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        orderRepo.deleteById(id);
    }



}
