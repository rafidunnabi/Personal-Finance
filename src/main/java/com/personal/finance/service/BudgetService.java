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

    public List<Budget> getBudgetsAndUpdateAmounts(List<Budget> budgets, Integer userId) {

        if (!budgets.isEmpty()) {
            Budget firstBudget = budgets.get(0);
            return budgetRepository.getBudgetsByDateRangeAndUser(budgets, userId);
        } else {
            return budgets;
        }
    }

    public void deleteBudgetById(Integer id, Integer userId) {
        budgetRepository.deleteBudgetById(id, userId);
    }

    public void editBudget( Double amount, String category, LocalDate localStartDate, LocalDate localEndDate, String description, Integer userId, Integer id) {
        budgetRepository.editBudget(amount, category, localStartDate, localEndDate, description, userId, id);
    }
}
