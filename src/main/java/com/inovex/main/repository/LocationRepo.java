package com.inovex.main.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Location;

@Repository
public interface LocationRepo extends JpaRepository<Location, Long> {

    @Query(value = "SELECT l FROM Location l WHERE l.empId= :employeeId AND" + " DATE(l.userDataTime) = :startDate")
    List<Location> getLocationofAnEmployeeByName(@Param("employeeId") String employeeId,
            @Param("startDate") Date startDate);

    @Query(value = "SELECT tt.* FROM locations tt " + "INNER JOIN (SELECT emp_id, MAX(user_date_time) AS MaxDateTime"
            + "  FROM locations GROUP BY emp_id) groupedtt ON tt.emp_id=groupedtt.emp_id AND tt.user_date_time = groupedtt.MaxDateTime", nativeQuery = true)
    public List<Object> getLocationsBymaxTime();

}
