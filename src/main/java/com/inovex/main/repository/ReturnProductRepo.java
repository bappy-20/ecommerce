package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.ReturnProduct;

@Repository
public interface ReturnProductRepo extends JpaRepository<ReturnProduct, Long> {

}
