package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Expense;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.ExpenseRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.ExpenseService;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepo expenseRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<Expense> findById(long id) {
        // TODO Auto-generated method stub
        return expenseRepo.findById(id);
    }

    @Override
    public List<Expense> findAll() {
        // TODO Auto-generated method stub
        return expenseRepo.findAll();
    }

    @Override
    public void saveAll(List<Expense> epns) {
        expenseRepo.saveAll(epns);

    }

    @Override
    public void updateExpense(Expense exp, Long id, HttpServletRequest request) {
        if (!id.equals(exp.getId())) {
            throw new NotFoundException("Car not found");
        }
        Optional<Expense> expn = expenseRepo.findById(id);
        expn.get().setExpenseType(exp.getExpenseType());
        expn.get().setAmount(exp.getAmount());
        expn.get().setApprovedAmount(exp.getAmount());
        expn.get().setUpdatedAt(new Date());
        expn.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        expn.get().setApprovedBy((long) request.getSession().getAttribute("userId"));
        expn.get().setAttachment(exp.getAttachment());
        expn.get().setNote(exp.getNote());
        expn.get().setExpenseDate(exp.getExpenseDate());
        expenseRepo.save(expn.get());
    }

    @Override
    public Long deleteExpense(Long id, HttpServletRequest request) {

        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Expense> expn = expenseRepo.findById(id);
            if (expn.isPresent()) {
                expenseRepo.deleteFromOrg(orgId, id);
                expenseRepo.delete(expn.get());
                return id;
            }
            throw new NotFoundException();

        }
        return id;
    }

    @Override
    public List<Object> findAllExpenseByDate(long carId, String fromDate, String toDate) {
        // TODO Auto-generated method stub
        return expenseRepo.findAllExpenseByDate(carId, fromDate, toDate);
    }

    @Override
    public Expense save(Expense exp, long orgId) {
        Expense exp1 = new Expense();
        Set<Expense> expList = new HashSet<>();
        try {
            Optional<Organization> org = orgRepo.findById(orgId);
            if (org.isPresent()) {
                expList = org.get().getExpense();
                exp.setActive(true);
                exp.setCreatedAt(new Date());
                exp.setUpdatedAt(new Date());
                exp.setCreatedBy(0);
                exp.setApprovedBy(0l);
                exp.setStatus(0);
                exp.setApprovedAmount(exp.getApprovedAmount());//getAmount());
                exp.setOrgId(orgId);
                exp1 = expenseRepo.save(exp);
                expList.add(exp1);
                org.get().setExpense(expList);
                orgRepo.save(org.get());
            }

        } catch (Exception e) {

        }

        return exp1;

    }

    @Override
    public Expense saveExp(Expense exp, HttpServletRequest request) {
        // TODO Auto-generated method stub
        exp.setStatus(1);
        exp.setCreatedAt(new Date());
        exp.setUpdatedAt(new Date());
        exp.setCreatedBy((long) request.getSession().getAttribute("userId"));
        exp.setApprovedBy((long) request.getSession().getAttribute("userId"));
        exp.setActive(true);
        return expenseRepo.save(exp);
    }

    @Override
    public List<Expense> getExpenseByEmpId(String expenseBy) {
        // TODO Auto-generated method stub
        return expenseRepo.getExpenseByEmpId(expenseBy);
    }

    @Override
    public List<Expense> getExpRptByExpTypeAndDateRange(String expenseType, Date startDate, Date endDate) {
        // TODO Auto-generated method stub
        return expenseRepo.getExpenseReportByExpenseTypeAndDateRange(expenseType, startDate, endDate);
    }

    @Override
    public List<Expense> getApprovedExpRptByDateRange(Date startDate, Date endDate) {
        // TODO Auto-generated method stub
        return expenseRepo.getApprovedExpRptByDateRange(startDate, endDate);
    }

    @Override
    public List<Expense> getPendingExpRptByDateRange(Date startDate, Date endDate) {
        // TODO Auto-generated method stub
        return expenseRepo.getPendingExpRptByDateRange(startDate, endDate);
    }

    @Override
    public Expense approveStatus(Long id) {
        Optional<Expense> expenseStatusApproved = expenseRepo.findById(id);
        expenseStatusApproved.get().setStatus(1);
        expenseRepo.save(expenseStatusApproved.get());
        return expenseStatusApproved.get();
    }

    @Override
    public Expense getExpense(Long id) {
        // TODO Auto-generated method stub
        Optional<Expense> expense = expenseRepo.findById(id);
        if (expense.isPresent())
            return expense.get();
        throw new NotFoundException();
    }

    @Override
    public Expense update(Expense exp, long id, HttpServletRequest request) {
        if (exp.getId() != id) {
            throw new NotFoundException();
        }
        Optional<Expense> expn = expenseRepo.findById(id);
        if (expn.isPresent()) {
            expn.get().setExpenseType(exp.getExpenseType());
            expn.get().setAmount(exp.getAmount());
            expn.get().setNote(exp.getNote());
            expn.get().setExpenseDate(exp.getExpenseDate());
            expn.get().setExpenseBy(exp.getExpenseBy());
            expn.get().setApprovedAmount(exp.getApprovedAmount());
            expn.get().setStatus(1);
            expn.get().setUpdatedAt(new Date());
            expn.get().setApprovedBy((long) request.getSession().getAttribute("userId"));
            expn.get().setAttachment(exp.getAttachment());
            return expenseRepo.save(expn.get());
        } else {
            throw new NullPointerException();
        }

    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        expenseRepo.deleteById(id);

    }

    @Override
    public Optional<String> getTotalExpense() {
        // TODO Auto-generated method stub
        return expenseRepo.getTotalExpense();
    }

    @Override
    public Optional<String> getTotalExpenseToday() {
        // TODO Auto-generated method stub
        return expenseRepo.getTotalExpenseToday();
    }

    @Override
    public Optional<String> getTotalExpenseMonth() {
        // TODO Auto-generated method stub
        return expenseRepo.getTotalExpenseMonth();
    }

    @Override
    public List<Expense> findAllPending() {
        // TODO Auto-generated method stub
        return expenseRepo.findAllPending();
    }

    @Override
    public List<Expense> findAllApprovedExpense() {
        // TODO Auto-generated method stub
        return expenseRepo.findAllApprovedExpense();
    }

    @Override
    public Expense save(Expense entity, HttpServletRequest request) {
        Expense exp1 = new Expense();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<Expense> expList = new HashSet<Expense>();
            if (org.isPresent()) {
                expList = org.get().getExpense();
                entity.setActive(true);
                entity.setCreatedAt(new Date());
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                entity.setApprovedBy((long) request.getSession().getAttribute("userId"));
                // entity.setStatus(1);
                entity.setApprovedAmount(entity.getApprovedAmount());
                entity = expenseRepo.save(entity);
                expList.add(entity);
                org.get().setExpense(expList);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("org is null");
        }

        return exp1;
    }

    @Override
    public void deleteExpenseFromOrg(long orgId, long id) {
        // TODO Auto-generated method stub
        expenseRepo.deleteFromOrg(orgId, id);
        expenseRepo.deleteById(id);
    }
}
