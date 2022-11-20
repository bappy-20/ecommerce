package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.ReturnOrder;

@Repository
public interface ReturnOrderRepo extends JpaRepository<ReturnOrder, Long> {

}
