package com.inovex.main.repository;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findRoleByName(String name);

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_role where organizations_role.organization_id = ?1 and organizations_role.role_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long roleId);

}
