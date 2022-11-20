package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Distributor;

@Repository
public interface DistributorRepo extends JpaRepository<Distributor, Long> {
    @Query(value = "SELECT distributor.id FROM distributor inner join  distributor_employess on distributor_employess.distributor_id = distributor.id where distributor_employess.employess_id=?1", nativeQuery = true)
    long findDistByUserId(long employeeId);

    @Query(value = "SELECT * FROM distributor inner join  distributor_users on distributor_users.distributor_id = distributor.id where distributor_users.users_id=?1", nativeQuery = true)
    Distributor findDistBySystemUserId(long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_distributors where organization_id = ?1 and distributors_id = ?2 ", nativeQuery = true)
    public int deleteFromOrg(long orgId, Long distId);

    @Query(value = "SELECT distributors_id FROM distributor_users where users_id = ?1", nativeQuery = true)
    public long getDistributorIdByUserId(long userId);
}
