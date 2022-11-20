package com.inovex.main.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.inovex.main.entity.Expense;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {

    @Query("SELECT l FROM Expense l where l.expenseBy = :expenseBy")
    public List<Expense> getExpenseByEmpId(@Param("expenseBy") String expenseBy);

    @Query("SELECT l FROM Expense l where l.status=0")
    public List<Expense> findAllPending();

    @Query("SELECT l FROM Expense l where l.status=1")
    public List<Expense> findAllApprovedExpense();

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_expense where organizations_expense.organization_id = ?1 and organizations_expense.expense_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long exptId);

    @Query(value = "SELECT expense.expense_type,expense.note,expense.car_id,CAST(expense.created_at as date),expense.amount FROM expense "
            + "where (select CAST(expense.created_at as date))>=?2"
            + " and (select CAST(expense.created_at as date)) <=?3  AND car_id=?1", nativeQuery = true)
    public List<Object> findAllExpenseByDate(long carId, String fromDate, String toDate);

    @Query(value = "SELECT e FROM Expense e WHERE e.expenseType= :expenseType AND DATE(e.createdAt) >= :startDate AND DATE(e.createdAt) <= :endDate")
    List<Expense> getExpenseReportByExpenseTypeAndDateRange(@Param("expenseType") String expenseType,
            @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT e FROM Expense e WHERE e.status=1 AND DATE(e.createdAt) >= :startDate AND DATE(e.createdAt) <= :endDate")
    List<Expense> getApprovedExpRptByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT e FROM Expense e WHERE e.status=0 AND DATE(e.createdAt) >= :startDate AND DATE(e.createdAt) <= :endDate")
    List<Expense> getPendingExpRptByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select sum(approved_amount) from expense", nativeQuery = true)
    Optional<String> getTotalExpense();

    @Query(value = "select sum(approved_amount) from expense where  cast(created_at as date)=curdate()", nativeQuery = true)
    Optional<String> getTotalExpenseToday();

    @Query(value = "select sum(approved_amount) from expense where month(cast(created_at as date))=month(curdate()) and year(cast(created_at as date))=year(curdate())", nativeQuery = true)
    Optional<String> getTotalExpenseMonth();
    
}
