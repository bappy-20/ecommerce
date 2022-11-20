package com.inovex.main.service;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Role;

/**
 * RoleService interface
 *
 * @author rabiul
 *
 */
public interface RoleService {

    Role save(Role role, long orgId);

    Role update(Role role, long id);

    Set<Role> getAllRole(HttpServletRequest request) throws Exception;

    Optional<Role> findByRoleId(long id);

    void deleteById(HttpServletRequest request, long id);
}
