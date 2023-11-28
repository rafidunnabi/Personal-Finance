package com.personal.finance.repository;

import com.personal.finance.model.Budget;
import com.personal.finance.model.Income;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class BudgetRepositoryImpl implements BudgetRepository{

    private final JdbcTemplate jdbcTemplate;

    public BudgetRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Budget> getAllBudget(Integer user_id) {
        String sql = "SELECT * " +
                "FROM budget i " +
                "INNER JOIN budget_categories ic ON i.budget_category_id = ic.id " +
                "WHERE i.user_id = ? " +
                "ORDER BY i.start_date DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Budget budget = new Budget();
            budget.setId(rs.getInt("id"));
            budget.setAmount(rs.getDouble("amount"));
            budget.setDescription(rs.getString("description"));
            budget.setStartDate(rs.getString("start_date"));
            budget.setEndDate(rs.getString("end_date"));
            budget.setCategory(rs.getString("category_name"));
            budget.setSpentAmount(rs.getDouble("spent_amount"));
            budget.setRemainingAmount(rs.getDouble("remaining_amount"));
            return budget;
        }, user_id);
    }

    @Override
    public void addBudget(double amount, String category, LocalDate startDate, LocalDate endDate, String description, Integer userId) {
        String sql = "INSERT INTO budget (amount, budget_category_id, description, start_date, end_date, user_id) " +
                "VALUES (?, (SELECT id FROM budget_categories WHERE category_name = ?), ?, ?, ?, ?)";
        jdbcTemplate.update(sql, amount, category, description, startDate, endDate, userId);
    }
}
