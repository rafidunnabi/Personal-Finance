package com.personal.finance.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface CreateTableRepository {
    void createUsersTable();
    void createIncomeTable();
    void createIncomeCategoriesTable();

    void createExpenseTable();
    void createExpenseCategoriesTable();

    void createBudgetCategoriesTable();

    void createBudgetTable();

    void createGoalTable();

    void createGoalCategoriesTable();

    void createContributionTable();
}
