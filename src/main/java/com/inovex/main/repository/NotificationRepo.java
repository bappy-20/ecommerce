package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {
	@Transactional
	@Modifying
	@Query(value = "delete from organizations_notifications where organization_id = ?1 and notifications_id = ?2", nativeQuery = true)
	public int deleteFromOrg(long orgId, long notId);
}
