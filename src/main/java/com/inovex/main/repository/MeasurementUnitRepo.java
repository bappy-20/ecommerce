package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.MeasurementUnit;

@Repository
public interface MeasurementUnitRepo extends JpaRepository<MeasurementUnit, Long> {
	@Transactional
	@Modifying
	@Query(value = "delete from organizations_measurement_units where organization_id =?1 and measurement_units_id =?2",nativeQuery = true)
	public int deleteFromOrg(long orgId, long measId);
}
