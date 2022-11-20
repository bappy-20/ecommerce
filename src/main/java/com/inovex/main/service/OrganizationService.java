package com.inovex.main.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.User;

public interface OrganizationService {

    public List<Organization> getAllOrganizations();

    public Organization getOrganization(Long id);

    public Organization createOrganization(Organization organization);

    public Organization updateOrganization(Organization organization, Long id);

    public Long deleteOrganization(Long id);

    Organization getOrgProfile(Organization organization, HttpServletRequest request);

    public Optional<Organization> findById(long distId);
    
    public Long getOrganizationByUser(User user);
    
    List<Boolean> getUserRight(Set<Organization> organaization, HttpServletRequest request, String rolemanagement);
   
    
}
