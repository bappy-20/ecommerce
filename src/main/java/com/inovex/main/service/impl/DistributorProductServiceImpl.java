package com.inovex.main.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.DistributorRequisitionProduct;
import com.inovex.main.repository.DistributorProductRepo;
import com.inovex.main.service.DistributorRequisitionProductService;

@Service
@Transactional
public class DistributorProductServiceImpl implements DistributorRequisitionProductService {

    @Autowired
    DistributorProductRepo dealerProductRepo;

    @Override
    public Optional<DistributorRequisitionProduct> findById(Long id) {
        // TODO Auto-generated method stub
        return dealerProductRepo.findById(id);
    }

    @Override
    public List<DistributorRequisitionProduct> findAll() {
        // TODO Auto-generated method stub
        return dealerProductRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        dealerProductRepo.deleteById(id);
    }

    @Override
    public DistributorRequisitionProduct update(DistributorRequisitionProduct entity, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DistributorRequisitionProduct save(DistributorRequisitionProduct entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return dealerProductRepo.save(entity);
    }

}
