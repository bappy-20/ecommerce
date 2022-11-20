package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.DistributorRequisitionReceive;

@Repository
public interface DistributorRequisisionReceiveRepo extends JpaRepository<DistributorRequisitionReceive, Long> {

}
