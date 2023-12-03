package com.personal.finance.repository;

import com.personal.finance.model.Expense;
import com.personal.finance.model.Income;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
            expense.setId(rs.getInt("id"));
            expense.setAmount(rs.getDouble("amount"));
            expense.setDescription(rs.getString("description"));
            expense.setDate(rs.getString("expense_date")); // You might need to convert this to LocalDate
            expense.setCategory(rs.getString("expense_category_name"));
            return expense;
        }, userId);
    }

    @Override
    public List<Expense> getExpenseByCustomDate(LocalDate startDate, LocalDate endDate, Integer user_Id) {
        String sql = "SELECT e.amount, ec.expense_category_name as category, e.description, e.expense_date as date " +
                "FROM expense e " +
                "JOIN expense_categories ec ON e.expense_category_id = ec.id " +
                "WHERE e.expense_date BETWEEN ? AND ? AND e.user_id = ?";
        return jdbcTemplate.query(sql, new Object[] { startDate, endDate, user_Id },
                new BeanPropertyRowMapper<>(Expense.class));
    }

    @Override
    public List<Expense> getExpenseByCustomDateAndCategory(LocalDate startDate, LocalDate endDate, String category,
                                                         Integer user_Id) {
        String sql = "SELECT e.id, e.amount, ec.expense_category_name as category, e.description, e.expense_date as date "
                +
                "FROM expense e " +
                "JOIN expense_categories ec ON e.expense_category_id = ec.id " +
                "WHERE e.expense_date BETWEEN ? AND ? AND ec.expense_category_name = ? AND e.user_id = ? ";

        return jdbcTemplate.query(sql, new Object[] { startDate, endDate, category, user_Id },
                new BeanPropertyRowMapper<>(Expense.class));
    }

    @Override
    public void editExpense(Integer id, Double amount, String category, LocalDate localDate, String description, Integer userId) {
        String sql = "UPDATE expense SET amount = ?, expense_category_id = (SELECT id FROM expense_categories WHERE expense_category_name = ?), " +
                "description = ?, expense_date = ?, user_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, category, description, localDate, userId, id);
    }

    @Override
    public void deleteExpense(Integer id, Integer userId) {
        String sql = "DELETE FROM expense WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public List<Expense> searchExpenseByCategory(String selectedCategory, Integer userId) {
        String sql = "SELECT e.id, e.amount, ec.expense_category_name as category, e.description, e.expense_date as date "
                +
                "FROM expense e " +
                "JOIN expense_categories ec ON e.expense_category_id = ec.id " +
                "WHERE ec.expense_category_name = ? AND e.user_id = ? ";

        return jdbcTemplate.query(sql, new Object[] { selectedCategory,userId },
                new BeanPropertyRowMapper<>(Expense.class));
    }
}
