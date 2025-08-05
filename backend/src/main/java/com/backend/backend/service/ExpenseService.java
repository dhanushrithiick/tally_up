package com.backend.backend.service;

import com.backend.backend.model.Expense;
import com.backend.backend.model.SplitRequest;
import com.backend.backend.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public void splitExpense(SplitRequest request) {
        List<Integer> allParticipants = request.getPayeeIds();
        int payerId = request.getPayerId();

        // Calculate per person share
        double perPerson = request.getTotalAmount() / allParticipants.size();

        // Each user (excluding the payer) owes the payer their share
        for (int payeeId : allParticipants) {
            if (payeeId == payerId) continue; // skip yourself

            Expense expense = new Expense();
            expense.setPayerId(payerId);
            expense.setPayeeId(payeeId);
            expense.setAmount(perPerson);
            expense.setDescription(request.getDescription());
            expenseRepository.save(expense);
        }
    }


    public List<Expense> getExpensesOwedBy(int userId) {
        return expenseRepository.findByPayeeId(userId);
    }

    public List<Expense> getExpensesPaidBy(int userId) {
        return expenseRepository.findByPayerId(userId);
    }
}
