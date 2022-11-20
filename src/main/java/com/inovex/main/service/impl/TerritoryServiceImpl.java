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
import com.inovex.main.entity.TerritoryModel;
import com.inovex.main.entity.User;
import com.inovex.main.repository.AreaRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.TerritoryRepository;
import com.inovex.main.repository.UserRepository;
import com.inovex.main.service.TerritoryService;

@Service
@Transactional
public class TerritoryServiceImpl implements TerritoryService {

    @Autowired
    TerritoryRepository territoryRepo;
    @Autowired
    AreaRepo areaService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    UserRepository userRepo;

    @Override
    public java.util.Optional<TerritoryModel> findById(Long id) {

        return territoryRepo.findById(id);
    }

    @Override
    public List<TerritoryModel> findAll() {

        return territoryRepo.findAll();
    }

    @Override
    public TerritoryModel getTerritory(Long id) {
        Optional<TerritoryModel> territory = territoryRepo.findById(id);
        if (territory.isPresent())
            return territory.get();
        throw new NotFoundException();
    }

    @Override
    public void deleteById(long id, HttpServletRequest request) {
//        territoryRepo.deleteRef(id);
//        territoryRepo.deleteById(id);
        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
                territoryRepo.deleteFromOrg(orgid, id);
                territoryRepo.deleteTerrritoryMarket(id);
                territoryRepo.deleteRef(id);
                territoryRepo.deleteById(id);

            } else {
                System.out.println("org not found");
            }
        }
    }

    @Override
    public TerritoryModel update(TerritoryModel territory, Long id, HttpServletRequest request) {
        if (!id.equals(territory.getId())) {

            throw new NotFoundException("Territory not found");
        }
        Optional<TerritoryModel> territoryUpdate = territoryRepo.findById(id);
        territoryUpdate.get().setTerritoryName(territory.getTerritoryName());
        if (territoryUpdate.get().getAreaId1() != 0 && territoryUpdate.get().getAreaId1() != territory.getAreaId1()) {
            throw new NotFoundException("Territory already assigned in an Area.Please delete and re-create");
        }
        territoryUpdate.get().setAreaId1(territory.getAreaId1());
        territoryUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        territoryRepo.save(territoryUpdate.get());
        return territoryUpdate.get();

    }

    @Override
    public TerritoryModel save(TerritoryModel entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        TerritoryModel territoryModel = new TerritoryModel();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<TerritoryModel> list = new HashSet<TerritoryModel>();
            Set<TerritoryModel> teriList = new HashSet<TerritoryModel>();
            if (org.isPresent()) {
                list = org.get().getTerritoryModels();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                territoryModel = territoryRepo.save(entity);
                Optional<AreaModel> area = areaService.findById(entity.getAreaId1());
                if (area.isPresent()) {
                    teriList = area.get().getTerritories();
                    teriList.add(territoryModel);
                    area.get().setTerritories(teriList);
                    areaService.save(area.get());
                }
                list.add(territoryModel);
                org.get().setTerritoryModels(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

    @Override
    public void saveUserMapping(List<Long> territoryId, Long id) {
        Optional<User> user = userRepo.findById(id);
        Set<TerritoryModel> list = new HashSet<>();
        Set<User> userList = new HashSet<>();
        if (user.isPresent()) {
            for (Long long1 : territoryId) {
                Optional<TerritoryModel> area = territoryRepo.findById(long1);
                if (area.isPresent()) {
                    list.add(area.get());
                    userList = area.get().getUsers();
                    userList.add(user.get());
                    area.get().setCustomUser(userList);
                    territoryRepo.save(area.get());

                }
            }
            user.get().setCustomTerritoryModel(list);
            userRepo.save(user.get());
        }

    }
}
