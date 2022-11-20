package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.DistributorRequisitionProduct;

@Repository
public interface DistributorProductRepo extends JpaRepository<DistributorRequisitionProduct, Long> {

}
