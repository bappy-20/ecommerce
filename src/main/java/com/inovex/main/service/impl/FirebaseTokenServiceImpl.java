package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.FirebaseToken;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.FirebaseTokenRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.FirebaseTokenService;

@Service
@Transactional
public class FirebaseTokenServiceImpl implements FirebaseTokenService {

    @Autowired
    FirebaseTokenRepo firebaseTokenRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public List<FirebaseToken> findAll() {
        // TODO Auto-generated method stub
        return firebaseTokenRepo.findAll();
    }

    @Override
    public Optional<FirebaseToken> findById(long id) {
        // TODO Auto-generated method stub
        return firebaseTokenRepo.findById(id);
    }

    @Override
    public Optional<FirebaseToken> findByEmployeeId(String employeeId) {
        // TODO Auto-generated method stub
        return firebaseTokenRepo.findByEmployeeId(employeeId);
    }

    @Override
    public FirebaseToken update(FirebaseToken token, long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FirebaseToken save(FirebaseToken entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        FirebaseToken firebaseToken = new FirebaseToken();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<FirebaseToken> list = new HashSet<FirebaseToken>();
            if (org.isPresent()) {
                list = org.get().getFirebaseTokens();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                firebaseToken = firebaseTokenRepo.save(entity);
                list.add(firebaseToken);
                org.get().setFirebaseTokens(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

    @Override
    public FirebaseToken save(FirebaseToken token, long orgId) {
        // TODO Auto-generated method stub
        FirebaseToken firebaseToken = new FirebaseToken();
        Optional<Organization> org = orgRepo.findById(orgId);
        Set<FirebaseToken> list = new HashSet<FirebaseToken>();
        if (org.isPresent()) {
            list = org.get().getFirebaseTokens();
            token.setCreatedAt(new Date());
            token.setActive(true);
            token.setUpdatedAt(new Date());
            token.setCreatedBy(0l);
            firebaseToken = firebaseTokenRepo.save(token);
            list.add(firebaseToken);
            org.get().setFirebaseTokens(list);
            orgRepo.save(org.get());
        }
        return firebaseToken;
    }

}
