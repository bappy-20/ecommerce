package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.UserType;

@Repository
public interface UserTypeRepo extends JpaRepository<UserType, Long> {

    @Query("SELECT l.id FROM UserType l where l.userType = :userType")
    public long getEmpCatId(@Param("userType") String userType);
    
    @Transactional
	@Modifying
	@Query(value = "delete from organizations_user_type where organization_id =?1 and user_type_id = ?2 ", nativeQuery = true)
	public int deleteFromOrg(long orgId, long userId);
}
