package com.inovex.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.ExpenseType;

@Repository
public interface ExpenseTypeRepo extends JpaRepository<ExpenseType, Long> {

   // List<ExpenseType> findAllByExpenseUser(String expenseUser);
	
    @Transactional
    @Modifying
    @Query(value = "delete from organizations_expense_types where organization_id =?1 and expense_types_id =?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long expTypeId);
}
