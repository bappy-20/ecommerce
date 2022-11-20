package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import com.inovex.main.entity.DistributorType;

public interface DistributorTypeService {
	
		Optional<DistributorType> findById(long id);

	    List<DistributorType> findAll();

	    DistributorType getDistributorType(long id);

//	    void deleteById(long id,HttpServletRequest request);
//	    
//	    DistributorType save(DistributorType entity, HttpServletRequest request);
//
//	    DistributorType update(DistributorType distributorType, Long id, HttpServletRequest request);

}
