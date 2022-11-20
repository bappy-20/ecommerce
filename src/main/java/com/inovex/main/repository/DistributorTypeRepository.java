package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.DistributorType;
@Repository
public interface DistributorTypeRepository extends JpaRepository<DistributorType, Long> {

}
