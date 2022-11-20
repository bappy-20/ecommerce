package com.inovex.main.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.MonthlyTarget;
import com.inovex.main.repository.TargetRepo;
import com.inovex.main.service.TargetService;

@Service
@Transactional
public class TargetServiceImpl implements TargetService {

    @Autowired
    TargetRepo targetRepo;

    @Override
    public Optional<MonthlyTarget> findById(Long id) {
        // TODO Auto-generated method stub
        return targetRepo.findById(id);
    }

    @Override
    public List<MonthlyTarget> findAll() {
        // TODO Auto-generated method stub
        return targetRepo.findAll();
    }

    @Override
    public MonthlyTarget save(MonthlyTarget entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        entity.setActive(true);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
        return targetRepo.save(entity);
    }

    @Override
    public List<MonthlyTarget> saveAll(List<MonthlyTarget> entities) {
        // TODO Auto-generated method stub
        return targetRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        targetRepo.deleteById(id);
    }

}
