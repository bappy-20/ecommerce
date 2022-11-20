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
import com.inovex.main.entity.UserType;
import com.inovex.main.repository.DistributorRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.UserTypeRepo;
import com.inovex.main.service.UserTypeService;

@Service
@Transactional
public class UserTypeServiceImpl implements UserTypeService {

    @Autowired
    UserTypeRepo userTypeRepo;
    @Autowired
    DistributorRepo distService;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<UserType> findById(Long id) {
        // TODO Auto-generated method stub
        return userTypeRepo.findById(id);
    }

    @Override
    public List<UserType> findAll() {
        // TODO Auto-generated method stub
        return userTypeRepo.findAll();
    }

    @Override
    public UserType getUserType(Long id) {
        Optional<UserType> userType = userTypeRepo.findById(id);
        if (userType.isPresent())
            return userType.get();

        throw new NotFoundException();

    }

    @Override
    public void deleteById(long id, HttpServletRequest request) {
        if (request.getSession().getAttribute("orgId") != null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				userTypeRepo.deleteFromOrg(orgid, id);
				userTypeRepo.deleteById(id);
			} else {
				System.out.println("org not found");
			}
		}

    }

    @Override
    public UserType update(UserType userType, long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        Optional<UserType> userTypeUpdate = userTypeRepo.findById(id);

        userTypeUpdate.get().setUserType(userType.getUserType());
        userTypeUpdate.get().setNote(userType.getNote());
        userTypeUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        userTypeRepo.save(userTypeUpdate.get());
        return userTypeUpdate.get();
    }

    @Override
    public long getEmpCatId(String userType) {
        // TODO Auto-generated method stub
        return userTypeRepo.getEmpCatId(userType);
    }

	@Override
	public UserType save(UserType entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		UserType userType = new UserType();
		if (request.getSession().getAttribute("orgId")!= null) {
			long id = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(id);
			Set<UserType> list =new HashSet<UserType>();
			if (org.isPresent()) {
				list = org.get().getUserType();
				entity.setActive(true);
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				userType = userTypeRepo.save(entity);
				list.add(userType);
				org.get().setUserType(list);
				orgRepo.save(org.get());
			}
			
		} else {
			System.out.println("Org is null");

		}
		return null;
	}

}
