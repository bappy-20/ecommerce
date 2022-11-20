package com.inovex.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.FirebaseToken;

@Repository
public interface FirebaseTokenRepo extends JpaRepository<FirebaseToken, Long> {
    Optional<FirebaseToken> findByEmployeeId(String employeeId);
}
