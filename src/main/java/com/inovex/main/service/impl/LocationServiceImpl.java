package com.inovex.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Location;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.LocationRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.LocationService;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepo locationRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<Location> findById(Long id) {
        // TODO Auto-generated method stub
        return locationRepo.findById(id);
    }

    @Override
    public List<Location> findAll() {
        // TODO Auto-generated method stub
        return locationRepo.findAll();
    }

    @Override
    public List<Location> saveAll(List<Location> entities, long orgId) {
        List<Location> ls = new ArrayList<>();
        Optional<Organization> org = orgRepo.findById(orgId);
        Set<Location> list = new HashSet<Location>();
        if (org.isPresent()) {
            list = org.get().getLocations();
            for (Location location : entities) {
                Location entity = new Location();
                entity.setActive(true);
                entity.setCreatedAt(new Date());
                entity.setUpdatedAt(new Date());
                entity.setUserDataTime(new Date());
                entity.setStartDate(new Date());
                entity.setCreatedBy(1);
                entity.setLatitude(location.getLatitude());
                entity.setLongitude(location.getLongitude());
                entity.setAddress(location.getAddress());
                entity.setEmpId(location.getEmpId());
                entity.setUserDataTime(location.getUserDataTime());
                entity.setStartDate(location.getStartDate());
                list.add(entity);
            }
            ls = locationRepo.saveAll(list);
            org.get().setLocations(list);
            orgRepo.save(org.get());
        }
        return ls;
    }

    @Override
    public void deleteById(Long id) {
        locationRepo.deleteById(id);
    }

    @Override
    public List<Location> getLocationOfAnEmployeeByName(String employeeId, Date startDate) {
        // TODO Auto-generated method stub
        return locationRepo.getLocationofAnEmployeeByName(employeeId, startDate);
    }

    @Override
    public List<Object> getLocationsBymaxTime() {
        // TODO Auto-generated method stub
        return locationRepo.getLocationsBymaxTime();
    }

    @Override
    public Location save(Location entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        Location location = new Location();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<Location> list = new HashSet<Location>();
            if (org.isPresent()) {
                list = org.get().getLocations();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                location = locationRepo.save(entity);
                list.add(location);
                org.get().setLocations(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

    @Override
    public Location save(Location entity, long orgId) {
        // TODO Auto-generated method stub
        Location location = new Location();
        Optional<Organization> org = orgRepo.findById(orgId);
        Set<Location> list = new HashSet<Location>();
        if (org.isPresent()) {
            list = org.get().getLocations();
            entity.setCreatedAt(new Date());
            entity.setActive(true);
            entity.setUpdatedAt(new Date());
            entity.setCreatedBy(0l);
            location = locationRepo.save(entity);
            list.add(location);
            org.get().setLocations(list);
            orgRepo.save(org.get());
        }
        return location;
    }
}
