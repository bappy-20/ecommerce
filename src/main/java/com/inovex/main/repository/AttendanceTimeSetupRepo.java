package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.AttendanceTimeSetup;

@Repository
public interface AttendanceTimeSetupRepo extends JpaRepository<AttendanceTimeSetup, Long> {

}
