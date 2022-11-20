package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.Role;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.RoleRepository;
import com.inovex.main.service.RoleService;

/**
 * RoleServiceImpl class
 *
 * @author Rabiul
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private OrganizationRepository orgRepository;

    @Override
    public Set<Role> getAllRole(HttpServletRequest request) throws Exception {
        Set<Role> list = new HashSet<Role>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepository.findById(id);
            if (org.isPresent()) {
                list = org.get().getRole();
            }
        }
        return list;
    }

    @Override
    public Optional<Role> findByRoleId(long id) {

        return roleRepository.findById(id);
    }

    @Override
    public Role save(Role role, long id) {
        Role rl = new Role();

        Set<Role> list = new HashSet<Role>();
        Optional<Organization> org = orgRepository.findById(id);
        if (org.isPresent()) {
            list = org.get().getRole();
            role.setCreatedAt(new Date());
            role.setUpdatedAt(new Date());
            role.setCreatedBy(1l);
            role.setActive(true);
            role.setUpdatedBy(1l);
            rl = roleRepository.save(role);
            list.add(rl);
            org.get().setRole(list);
            orgRepository.save(org.get());
        }

        return rl;
    }

    @Override
    public Role update(Role role, long id) {
        Optional<Role> role1 = roleRepository.findById(id);
        Role rl = new Role();
        if (role1.isPresent()) {
            role1.get().setUpdatedBy(1l);
            role1.get().setUpdatedAt(new Date());
            role1.get().setName(role.getName());
            role1.get().setDescription(role.getDescription());
            rl = roleRepository.save(role1.get());
        }
        return rl;
    }

    @Override
    public void deleteById(HttpServletRequest request, long id) {
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            roleRepository.deleteFromOrg(orgId, id);
            roleRepository.deleteById(id);
        }
    }
}
