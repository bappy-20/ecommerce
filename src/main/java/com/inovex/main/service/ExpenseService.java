package com.inovex.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Expense;

public interface ExpenseService {

    public Optional<Expense> findById(long id);

    public List<Expense> findAll();

    public void saveAll(List<Expense> epns);

    public void updateExpense(Expense exp, Long id, HttpServletRequest request);

    public Long deleteExpense(Long id, HttpServletRequest request);

    public List<Object> findAllExpenseByDate(long carId, String fromDate, String toDate);

    public Expense save(Expense exp, long orgId2);

    public Expense saveExp(Expense exp, HttpServletRequest request);

    public List<Expense> getExpenseByEmpId(String expenseBy);

    List<Expense> getExpRptByExpTypeAndDateRange(String expenseType, Date startDate, Date endDate);

    List<Expense> getApprovedExpRptByDateRange(Date startDate, Date endDate);

    List<Expense> getPendingExpRptByDateRange(Date startDate, Date endDate);

    Expense approveStatus(Long id);

    Expense getExpense(Long id);

    Expense update(Expense expense, long id, HttpServletRequest request);

    void deleteById(Long id);

    Optional<String> getTotalExpense();

    Optional<String> getTotalExpenseToday();

    Optional<String> getTotalExpenseMonth();

    public List<Expense> findAllPending();

    public List<Expense> findAllApprovedExpense();

    Expense save(Expense entity, HttpServletRequest request);

    void deleteExpenseFromOrg(long orgId, long id);
}
