package com.inovex.main.service;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Distributor;

public interface DistributorService {
    Optional<Distributor> findById(Long id);

    List<Distributor> findAll();

    Distributor getDistributor(Long id);

    void deleteById(Long id,HttpServletRequest request);

    Distributor update(Distributor distributor, Long id, HttpServletRequest request);

    long findDistByUserId(long employeeId);
    
    Distributor save(Distributor entity, HttpServletRequest request);

    Long findDistributorIdByUserId(long userId);
    
   // List<Boolean> getUserRight(Set<Distributor> distributor, HttpServletRequest request, String rolemanagement);

}
