package com.inovex.main.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.ExpenseType;

public interface ExpenseTypeService {

    Set<ExpenseType> findByOrg(Long orgId);

    Optional<ExpenseType> findById(Long id);

    List<ExpenseType> findAll();

   // List<ExpenseType> findAllByExpenseUser(String expenseUser);

    ExpenseType getExpenseType(Long id);

    void deleteById(Long id,HttpServletRequest request);

    ExpenseType update(ExpenseType category, long id, HttpServletRequest request);
    
    ExpenseType save(ExpenseType entity, HttpServletRequest request);
}
