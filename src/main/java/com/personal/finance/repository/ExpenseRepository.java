package com.personal.finance.repository;

import com.personal.finance.model.Expense;
import com.personal.finance.model.Income;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository {

    void saveExpense(double amount, String category, String date, String description);

    void addExpense(double amount, String category, LocalDate date, String description, Integer userId);

    List<Expense> getRecentExpenses(Integer userId);

    List<Expense> getExpenseByCustomDate(LocalDate startDate, LocalDate endDate, Integer user_Id);

    List<Expense> getExpenseByCustomDateAndCategory(LocalDate startDate, LocalDate endDate, String category,
                                                  Integer user_Id);
}
