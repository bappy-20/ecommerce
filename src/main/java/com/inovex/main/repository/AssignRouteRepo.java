package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.AssignRouteModel;

@Repository
public interface AssignRouteRepo extends JpaRepository<AssignRouteModel, Long> {

}
