package com.inovex.main.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.OrderDetails;
import com.inovex.main.repository.OrderDetailsRepo;
import com.inovex.main.service.OrderDetailsService;

@Service
@Transactional
public class OrderDetailsServiceImpl implements OrderDetailsService {
    @Autowired
    OrderDetailsRepo orderRepo;

    @Override
    public Optional<OrderDetails> findById(Long id) {
        // TODO Auto-generated method stub
        return orderRepo.findById(id);
    }

    @Override
    public List<OrderDetails> findAll() {
        // TODO Auto-generated method stub
        return orderRepo.findAll();
    }

    @Override
    public OrderDetails save(OrderDetails entity) {
        // TODO Auto-generated method stub
        entity.setActive(true);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        entity.setCreatedBy(1);
        return orderRepo.save(entity);
    }

    @Override
    public OrderDetails update(OrderDetails entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<OrderDetails> saveAll(List<OrderDetails> entities) {
        // TODO Auto-generated method stub
        return orderRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        orderRepo.deleteById(id);
    }

}
