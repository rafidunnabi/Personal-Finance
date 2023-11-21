package com.personal.finance.repository;

import com.personal.finance.model.Expense;
import com.personal.finance.model.Income;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository{

    private final JdbcTemplate jdbcTemplate;

    public ExpenseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveExpense(double amount, String category, String date, String description) {
        String sql = "INSERT INTO expense (amount, category, date, description) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, amount, category, date, description);
    }

    @Override
    public void addExpense(double amount, String category, LocalDate date, String description, Integer userId) {
        String sql = "INSERT INTO expense (amount, expense_category_id, description, expense_date, user_id) " +
                "VALUES (?, (SELECT id FROM expense_categories WHERE expense_category_name = ?), ?, ?, ?)";
        jdbcTemplate.update(sql, amount, category, description, date, userId);
    }

    @Override
    public List<Expense> getRecentExpenses(Integer userId) {
        String sql = "SELECT * " +
                "FROM expense i " +
                "INNER JOIN expense_categories ic ON i.expense_category_id = ic.id " +
                "WHERE i.user_id = ? " +
                "ORDER BY i.expense_date DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Expense expense = new Expense();
            expense.setAmount(rs.getDouble("amount"));
            expense.setDescription(rs.getString("description"));
            expense.setDate(rs.getString("expense_date")); // You might need to convert this to LocalDate
            expense.setCategory(rs.getString("expense_category_name"));
            return expense;
        }, userId);
    }
}
