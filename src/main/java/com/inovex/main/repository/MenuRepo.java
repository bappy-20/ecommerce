package com.inovex.main.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Menu;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Long> {

    Optional<Menu> findByRoleName(String roleName);

    /*
     * @Transactional
     * 
     * @Modifying
     * 
     * @Query(value = "delete from regions_areas where regions_areas.areas_id= ?1",
     * nativeQuery = true) public int deleteRef(long id);
     */

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_menu where organizations_menu.organization_id = ?1 and organizations_menu.menu_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long menuId);

}
