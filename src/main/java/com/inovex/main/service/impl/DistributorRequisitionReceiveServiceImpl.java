package com.inovex.main.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.DistributorRequisitionReceive;
import com.inovex.main.repository.DistributorRequisisionReceiveRepo;
import com.inovex.main.service.DistributorRequisitionReceiveService;

@Service
@Transactional
public class DistributorRequisitionReceiveServiceImpl implements DistributorRequisitionReceiveService {

    @Autowired
    DistributorRequisisionReceiveRepo distReqReceive;

    @Override
    public Optional<DistributorRequisitionReceive> findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DistributorRequisitionReceive> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public DistributorRequisitionReceive update(DistributorRequisitionReceive entity, Long id,
            HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DistributorRequisitionReceive save(DistributorRequisitionReceive entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return distReqReceive.save(entity);
    }
}
