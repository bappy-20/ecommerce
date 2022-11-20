package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.UserType;

public interface UserTypeService {
    Optional<UserType> findById(Long id);

    List<UserType> findAll();

    UserType getUserType(Long id);
    
    void deleteById(long id, HttpServletRequest request);

    UserType update(UserType userType, long id, HttpServletRequest request);

    public long getEmpCatId(String userType);
    
    UserType save(UserType entity, HttpServletRequest request);

}
