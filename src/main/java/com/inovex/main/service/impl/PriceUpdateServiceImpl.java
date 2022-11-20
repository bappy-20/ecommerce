package com.inovex.main.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.PriceUpdateHistory;
import com.inovex.main.repository.PriceUpdateRepo;
import com.inovex.main.service.PriceUpdateService;

@Service
@Transactional
public class PriceUpdateServiceImpl implements PriceUpdateService {

    @Autowired
    PriceUpdateRepo priceUpdateRepo;

    @Override
    public Optional<PriceUpdateHistory> findById(Long id) {
        // TODO Auto-generated method stub
        return priceUpdateRepo.findById(id);
    }

    @Override
    public List<PriceUpdateHistory> findAll() {
        // TODO Auto-generated method stub
        return priceUpdateRepo.findAll();
    }

    @Override
    public PriceUpdateHistory save(PriceUpdateHistory entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        entity.setActive(true);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
        return priceUpdateRepo.save(entity);
    }

    @Override
    public List<PriceUpdateHistory> saveAll(List<PriceUpdateHistory> entities) {
        // TODO Auto-generated method stub
        return priceUpdateRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        priceUpdateRepo.deleteById(id);
    }

    @Override
    public Optional<Long> countTotalRecieve(long productId) {
        // TODO Auto-generated method stub
        return priceUpdateRepo.countTotalRecieve(productId);
    }

    @Override
    public Optional<PriceUpdateHistory> findByProductId(Long productId) {
        // TODO Auto-generated method stub
        return priceUpdateRepo.findByProductId(productId);
    }

    @Override
    public List<PriceUpdateHistory> findAllByProduct(long productId, Date startDate, Date endDate) {
        // TODO Auto-generated method stub
        return priceUpdateRepo.findAllByProduct(productId, startDate, endDate);
    }

}
