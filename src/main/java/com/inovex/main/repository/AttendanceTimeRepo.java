package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.AttendanceTime;

@Repository
public interface AttendanceTimeRepo extends JpaRepository<AttendanceTime, Long> {

}
