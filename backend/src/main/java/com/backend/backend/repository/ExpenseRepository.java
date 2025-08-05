package com.backend.backend.repository;

import com.backend.backend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByPayeeId(int payeeId);
    List<Expense> findByPayerId(int payerId);
}
