package com.personal.finance.repository;

import com.personal.finance.model.Income;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class IncomeRepositoryImpl implements IncomeRepository {

    private final JdbcTemplate jdbcTemplate;

    public IncomeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveIncome(double amount, String category, String date, String description) {
        String sql = "INSERT INTO income (amount, category, date, description) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, amount, category, date, description);
    }

    @Override
    public void addIncome(double amount, String category, LocalDate date, String description, Integer userId) {
        String sql = "INSERT INTO income (amount, income_category_id, description, income_date, user_id) " +
                "VALUES (?, (SELECT id FROM income_categories WHERE income_category_name = ?), ?, ?, ?)";
        jdbcTemplate.update(sql, amount, category, description, date, userId);
    }

    @Override
    public List<Income> getRecentIncomes(Integer userId) {
        String sql = "SELECT * " +
                "FROM income i " +
                "INNER JOIN income_categories ic ON i.income_category_id = ic.id " +
                "WHERE i.user_id = ? " +
                "ORDER BY i.income_date DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Income income = new Income();
            income.setAmount(rs.getDouble("amount"));
            income.setDescription(rs.getString("description"));
            income.setDate(rs.getString("income_date")); // You might need to convert this to LocalDate
            income.setCategory(rs.getString("income_category_name"));
            return income;
        }, userId);
    }
}
