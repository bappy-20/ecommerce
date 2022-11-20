package com.inovex.main.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("delete from User l where l.email = :email")
    public void deleteUser(@Param("email") String email);

    List<User> findByUserType(String userType);

    @Transactional
    @Modifying
    @Query(value = "delete FROM distributor_users where users_id = ?1", nativeQuery = true)
    public int deleteFromDistUser(long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_users where organization_id = ?1 and users_id = ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long userId);

    @Query("SELECT l FROM User l where l.username = :username AND l.password = :password")
    Optional<User> checkUserExistOrNot(@Param("username") String username, @Param("password") String password);

//    @Query(value = "delete FROM workforce.user_mkts where users_id = ?1", nativeQuery = true)
//    public void deleteUserFromMarkets(long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from user_mkts where users_id = ?1 ", nativeQuery = true)
    public void deleteUserMarket(long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from user_areas where users_id = ?1", nativeQuery = true)
    public void deleteUserFromUserAreas(long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from user_regions where users_id = ?1", nativeQuery = true)
    public void deleteUserFromUserRegions(long userId);

    @Transactional
    @Modifying
    @Query(value = "delete FROM user_role where user_id = ?1 ", nativeQuery = true)
    public void deleteUserFromUserRoles(long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from user_territories where users_id = ?1", nativeQuery = true)
    public void deleteUserFromUserTerritories(long userId);

    @Query(value = "SELECT id,full_name,username FROM user where  user_type = 'DE' LIMIT ?1,?2", nativeQuery = true)
    public ArrayList<Object> getPagination(int start, int length);

    @Query(value = "SELECT id,full_name,username FROM user where  user_type = 'DE' and id like 1% LIMIT ?1,?2", nativeQuery = true)
    public ArrayList<Object> getPaginationWithSerachParam(String query, int start, int length);

    @Query(value = "SELECT id,full_name,username FROM user where user_type = 'DE' and id like 1%", nativeQuery = true)
    public Long countBySearchParam(String searchParam);

    Optional<User> findUserByFullName(String fullName);

    @Query(value = "SELECT mkts_id FROM user_mkts where users_id = ?1", nativeQuery = true)
    public List<Long> findAllMarketByUserId(long userId);

    @Query(value = "SELECT areas_id FROM user_areas where users_id = ?1", nativeQuery = true)
    public Long findAllAreaByUserId(long userId);

    @Query(value = "SELECT territories_id FROM user_territories where users_id = ?1", nativeQuery = true)
    public Long findAllTerritoryByUserId(long userId);

    @Query(value = "SELECT regions_id FROM user_regions  where users_id = ?1", nativeQuery = true)
    public Long findAllRegionsByUserId(long userId);

    @Query(value = "SELECT distributors_id FROM distributor_users where users_id = ?1", nativeQuery = true)
    public Long findDistributorNameByUserId(long userId);

    @Query(value = "SELECT mkts_id FROM user_mkts where users_id = ?1", nativeQuery = true)
    public List<Long> findAllTerritoriesAssignedWithMarketByUserId(long userId);

}
