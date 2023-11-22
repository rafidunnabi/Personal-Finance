package com.personal.finance.service;

import com.personal.finance.model.Expense;
import com.personal.finance.model.Income;
import com.personal.finance.repository.ExpenseRepository;
import com.personal.finance.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void saveExpense(double amount, String category, String date, String description) {
        expenseRepository.saveExpense(amount, category, date, description);
    }

    public void addExpenseRecord(double amount, String category, LocalDate date, String description, Integer userId) {
        expenseRepository.addExpense(amount, category, date, description, userId);
    }

    public List<Expense> getRecentExpenses(Integer userId) {
        return expenseRepository.getRecentExpenses(userId);
    }
    public List<Expense> findExpenseByCustomDate(LocalDate startDate, LocalDate endDate, Integer user_id) {
        return expenseRepository.getExpenseByCustomDate(startDate, endDate, user_id);
    }

    public List<Expense> findExpenseByCustomDateAndCategory(LocalDate startDate, LocalDate endDate, String category,
                                                          Integer user_id) {
        return expenseRepository.getExpenseByCustomDateAndCategory(startDate, endDate, category, user_id);
    }
}
