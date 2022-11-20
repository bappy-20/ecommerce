package com.inovex.main.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Supplier;

public interface SupplierService {

    Optional<Supplier> findById(Long id);

    List<Supplier> findAll();

    Supplier getSupplier(Long id);

    void deleteById(long id,HttpServletRequest request);

    Supplier update(Supplier supplier, long id, HttpServletRequest request);
    
    Supplier save(Supplier supplier, HttpServletRequest request);
}
