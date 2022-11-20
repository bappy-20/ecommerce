package com.inovex.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Location;

public interface LocationService {

    Optional<Location> findById(Long id);

    List<Location> findAll();

    List<Location> saveAll(List<Location> entities, long orgId);

    void deleteById(Long id);

    List<Location> getLocationOfAnEmployeeByName(String employeeId, Date startDate);

    public List<Object> getLocationsBymaxTime();

    Location save(Location entity, long orgId);

    Location save(Location entity, HttpServletRequest request);

}
