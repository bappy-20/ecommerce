package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.FirebaseToken;

public interface FirebaseTokenService {

    public List<FirebaseToken> findAll();

    public Optional<FirebaseToken> findById(long id);

    Optional<FirebaseToken> findByEmployeeId(String employeeId);

    FirebaseToken update(FirebaseToken token, long id);

    FirebaseToken save(FirebaseToken token, HttpServletRequest request);

    FirebaseToken save(FirebaseToken entity, long orgId);
}
