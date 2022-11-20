package com.inovex.main.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.Attendance;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

    @Query(value = "SELECT a FROM Attendance a WHERE DATE(a.createdAt) >= :startDate AND DATE(a.createdAt) <= :endDate AND "
            + " a.employeeId=:empId")
    List<Attendance> getAllBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
            @Param("empId") String empId);

    @Query(value = "SELECT a FROM Attendance a WHERE DATE(a.createdAt)= current_date AND " + " a.employeeId=:empId")
    Optional<Attendance> findByEmpIdAndLogDate(@Param("empId") String empId);

    @Query("select e.status,count(*), e.employeeId from Attendance e where year(e.createdAt) = year(current_date) and "
            + " month(e.createdAt) = month(current_date) and e.employeeId=:employeeId group by e.status")
    List<Object> findAllOfCurrentMonth(@Param("employeeId") String employeeId);

    @Query(value = "SELECT a FROM Attendance a WHERE DATE(a.createdAt) = :startDate ")
    List<Attendance> getAttendanceDayWise(@Param("startDate") Date startDate);

    @Query(value = "SELECT a FROM Attendance a WHERE DATE(a.createdAt) >= :startDate AND DATE(a.createdAt) <= :endDate ")
    List<Attendance> getAttendanceBetweenTwodates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT employee_id AS employeeId, count(*) as total,"
            + "sum(case when status='Present' then 1 else 0 end) present,"
            + "sum(case when status='Absent' then 1 else 0 end) absent,"
            + "sum(case when status='Leave' then 1 else 0 end) inleave,"
            + "sum(case when status='Early-Out' then 1 else 0 end) earlyOut,"
            + "sum(case when status='Late-In' then 1 else 0 end) latIn "
            + "from attendance,organizations_attendances where (cast(created_at  as date)) >=?1"
            + " and (cast(created_at as date)) <=?2 "
            + " and organizations_attendances.attendances_id=attendance.id and"
            + " organizations_attendances.organization_id=?3 group by "
            + "employee_id order by employee_id asc", nativeQuery = true)
    public List<Object> getAllEmployeeAttendanceSummeryByDate(@Param("startDate") Date startDate,
            @Param("endDate") Date endDate, @Param("orgId") long orgId);

    @Query(value = "SELECT  count(*) as total," + "sum(case when status='Present' then 1 else 0 end) present,"
            + "sum(case when status='On-Leave' then 1 else 0 end) inleave,"
            + "sum(case when status='Early-Out' then 1 else 0 end) earlyOut,"
            + "sum(case when status='Late-In' then 1 else 0 end) latIn "
            + "from attendance where (cast(created_at  as date)) =?1 ", nativeQuery = true)
    public List<Object> getAttendanceSummeryByADate(String startDate);

    @Query(value = "SELECT a FROM Attendance a WHERE a.employeeId= :employeeId AND DATE(a.createdAt) >= :startDate AND DATE(a.createdAt) <= :endDate")
    List<Attendance> getAttendanceofAnEmployee(@Param("employeeId") String employeeId,
            @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT a FROM Attendance a WHERE a.employeeId= :employeeId AND DATE(a.createdAt) >= :startDate AND DATE(a.createdAt) <= :endDate")
    List<Attendance> getAttendanceofAnEmployeeByName(@Param("employeeId") String employeeId,
            @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT a FROM Attendance a WHERE DATE(a.createdAt)= current_date")
    List<Attendance> findAllByCurDate();

    @Query(value = "SELECT a FROM Attendance a WHERE DATE(a.createdAt)= :startDate AND " + " a.employeeId=:empId")
    Optional<Attendance> findExistOrNot(@Param("startDate") Date startDate, @Param("empId") String empId);
}
