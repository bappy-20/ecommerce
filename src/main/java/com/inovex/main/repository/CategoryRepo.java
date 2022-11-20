package com.inovex.main.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    
    @Transactional
    @Modifying
    @Query(value = "delete from organizations_categories where organizations_categories.organization_id =?1  and categories_id =?2", nativeQuery = true)
    public int deleteFromOrg(long orgid, long id);
}
