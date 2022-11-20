package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.AreaModel;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.RegionModel;
import com.inovex.main.entity.User;
import com.inovex.main.repository.AreaRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.RegionModelRepository;
import com.inovex.main.repository.UserRepository;
import com.inovex.main.service.AreaService;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    AreaRepo areaRepo;
    @Autowired
    RegionModelRepository regionService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    UserRepository userRepo;

    @Override
    public Optional<AreaModel> findById(Long id) {
        // TODO Auto-generated method stub
        return areaRepo.findById(id);
    }

    @Override
    public List<AreaModel> findAll() {
        // TODO Auto-generated method stub
        return areaRepo.findAll();
    }

    @Override
    public AreaModel getArea(Long id) {
        Optional<AreaModel> areaModel = areaRepo.findById(id);
        if (areaModel.isPresent())
            return areaModel.get();
        throw new NotFoundException();
    }

    @Override
    public AreaModel update(AreaModel area, Long id, HttpServletRequest request) {

        Optional<AreaModel> areaModelUpdate = areaRepo.findById(id);
        areaModelUpdate.get().setAreaName(area.getAreaName());
        if (areaModelUpdate.get().getRegionId1() != 0 && areaModelUpdate.get().getRegionId1() != area.getRegionId1()) {
            throw new NotFoundException("Area already assigned in a region.Please delete and re-create");
        }

        areaModelUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        areaModelUpdate.get().setRegionId1(area.getRegionId1());
        areaRepo.save(areaModelUpdate.get());
        return areaModelUpdate.get();

    }

    @Override
    public AreaModel save(AreaModel entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        AreaModel areaModel = new AreaModel();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<AreaModel> list = new HashSet<AreaModel>();
            Set<AreaModel> areaList = new HashSet<AreaModel>();
            if (org.isPresent()) {
                list = org.get().getAreaModels();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                areaModel = areaRepo.save(entity);
                Optional<RegionModel> region = regionService.findById(entity.getRegionId1());
                if (region.isPresent()) {
                    areaList = region.get().getAreas();
                    areaList.add(areaModel);
                    region.get().setAreas(areaList);
                    regionService.save(region.get());
                }
                list.add(areaModel);
                org.get().setAreaModels(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
            	areaRepo.deleteFromAreaTerritory(id);
                areaRepo.deleteFromOrg(orgid, id);
                areaRepo.deleteRef(id);
                areaRepo.deleteById(id);
              

            }
        } else {
            System.out.println("org not found");
        }
    }

    @Override
    public void saveAreaMapping(List<Long> areaId, Long id) {
        Optional<User> user = userRepo.findById(id);
        Set<AreaModel> areaList = new HashSet<>();
        Set<User> userList = new HashSet<>();
        if (user.isPresent()) {
            for (Long long1 : areaId) {
                Optional<AreaModel> area = areaRepo.findById(long1);
                if (area.isPresent()) {
                    areaList.add(area.get());
                    userList = area.get().getUsers();
                    userList.add(user.get());
                    area.get().setCustomUser(userList);
                    areaRepo.save(area.get());

                }
            }
            user.get().setCustomAreaModel(areaList);
            userRepo.save(user.get());
        }

    }
}
