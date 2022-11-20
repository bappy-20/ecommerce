package com.inovex.main.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.AssignRouteModel;
import com.inovex.main.repository.AssignRouteRepo;
import com.inovex.main.service.AssignRouteService;

@Service
@Transactional
public class AssignRouteServiceImpl implements AssignRouteService {

    @Autowired
    AssignRouteRepo assignRepo;

    @Override
    public Optional<AssignRouteModel> findById(Long id) {
        // TODO Auto-generated method stub
        return assignRepo.findById(id);
    }

    @Override
    public List<AssignRouteModel> findAll() {
        // TODO Auto-generated method stub
        return assignRepo.findAll();
    }

    @Override
    public AssignRouteModel save(AssignRouteModel entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        entity.setCreatedAt(new Date());
        entity.setActive(true);
        entity.setUpdatedAt(new Date());
        /* entity.setCreatedBy((long) request.getSession().getAttribute("userId")); */
        entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
        return assignRepo.save(entity);
    }

    @Override
    public List<AssignRouteModel> saveAll(List<AssignRouteModel> entities) {
        // TODO Auto-generated method stub
        return assignRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        assignRepo.deleteById(id);
    }

}
