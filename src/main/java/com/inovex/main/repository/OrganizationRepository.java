package com.inovex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(value = "SELECT * FROM organizations inner join  organizations_users on organizations_users.organization_id = organizations.id where organizations_users.users_id=?1", nativeQuery = true)
    Organization findOrganizationByUserId(long userId);
}
