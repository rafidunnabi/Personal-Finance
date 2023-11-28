package com.personal.finance.repository;

import com.personal.finance.model.Budget;

import java.time.LocalDate;
import java.util.List;

public interface BudgetRepository {
    List<Budget> getAllBudget(Integer user_id);

    void addBudget(double amount, String category, LocalDate startDate, LocalDate endDate, String description, Integer userId);
}
