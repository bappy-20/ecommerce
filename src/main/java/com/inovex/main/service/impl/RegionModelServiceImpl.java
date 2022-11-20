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

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.RegionModel;
import com.inovex.main.entity.User;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.RegionModelRepository;
import com.inovex.main.repository.UserRepository;
import com.inovex.main.service.RegionModelService;

@Service
@Transactional
public class RegionModelServiceImpl implements RegionModelService {

    @Autowired
    RegionModelRepository regionMOdelRepo;;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    UserRepository userRepo;

    @Override
    public Optional<RegionModel> findById(Long id) {
        // TODO Auto-generated method stub
        return regionMOdelRepo.findById(id);
    }

    @Override
    public List<RegionModel> findAll() {
        // TODO Auto-generated method stub
        return regionMOdelRepo.findAll();
    }

    @Override
    public RegionModel save(RegionModel entity, HttpServletRequest request) {

        RegionModel region = new RegionModel();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<RegionModel> list = new HashSet<RegionModel>();
            if (org.isPresent()) {
                list = org.get().getRegions();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                /* entity.setCreatedBy((long) request.getSession().getAttribute("userId")); */
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                region = regionMOdelRepo.save(entity);
                list.add(region);
                org.get().setRegions(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is null");
        }
        return region;

    }

    @Override
    public RegionModel update(RegionModel entity, Long id, HttpServletRequest request) {

        if (!id.equals(entity.getId())) {
            throw new NotFoundException("Region not found");
        }
        Optional<RegionModel> region = regionMOdelRepo.findById(id);
        region.get().setRegionName(entity.getRegionName());
        region.get().setUpdatedAt(new Date());
        /*
         * region.get().setCreatedBy((long)
         * request.getSession().getAttribute("userId"));
         */
        region.get().setCreatedBy(1);
        regionMOdelRepo.save(region.get());
        return region.get();
    }

    @Override
    public List<RegionModel> saveAll(List<RegionModel> entities, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return regionMOdelRepo.saveAll(entities);
    }

    @Override
    public void deleteById(long id, HttpServletRequest request) {

        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
                regionMOdelRepo.deleteFromOrg(orgid, id);
                regionMOdelRepo.deleteById(id);

            } else {
                System.out.println("org not found");

            }

        }
    }

    @Override
    public void saveUserMapping(List<Long> regionId, Long id) {
        Optional<User> user = userRepo.findById(id);
        Set<RegionModel> list = new HashSet<>();
        Set<User> userList = new HashSet<>();
        if (user.isPresent()) {
            for (Long long1 : regionId) {
                Optional<RegionModel> area = regionMOdelRepo.findById(long1);
                if (area.isPresent()) {
                    list.add(area.get());
                    userList = area.get().getUsers();
                    userList.add(user.get());
                    area.get().setCustomUser(userList);
                    regionMOdelRepo.save(area.get());

                }
            }
            user.get().setCustomRegionModel(list);
            userRepo.save(user.get());
        }

    }
}
