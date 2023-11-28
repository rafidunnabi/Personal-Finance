package com.personal.finance.service;

import com.personal.finance.model.Budget;
import com.personal.finance.model.Income;
import com.personal.finance.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }
    public List<Budget> getRecentBudgets(Integer userId) {
        return budgetRepository.getAllBudget(userId);
    }
    public void addBudgetRecord(double amount, String category, LocalDate startDate, LocalDate endDate, String description, Integer userId) {
        budgetRepository.addBudget(amount, category, startDate, endDate, description, userId);
    }
}
