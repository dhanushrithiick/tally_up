package com.backend.backend.controller;

import com.backend.backend.model.Expense;
import com.backend.backend.model.SplitRequest;
import com.backend.backend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "http://localhost:3000")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/split")
    public String splitExpense(@RequestBody SplitRequest request) {
        expenseService.splitExpense(request);
        return "Expense split successfully!";
    }

    @GetMapping("/owedBy/{userId}")
    public List<Expense> getOwedBy(@PathVariable int userId) {
        return expenseService.getExpensesOwedBy(userId);
    }

    @GetMapping("/paidBy/{userId}")
    public List<Expense> getPaidBy(@PathVariable int userId) {
        return expenseService.getExpensesPaidBy(userId);
    }
}
