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
            income.setId(rs.getInt("id"));
            income.setAmount(rs.getDouble("amount"));
            income.setDescription(rs.getString("description"));
            income.setDate(rs.getString("income_date")); // You might need to convert this to LocalDate
            income.setCategory(rs.getString("income_category_name"));
            return income;
        }, userId);
    }

    @Override
    public List<Income> getIncomeByCustomDate(LocalDate startDate, LocalDate endDate, Integer user_Id) {
        String sql = "SELECT i.amount, ic.income_category_name as category, i.description, i.income_date as date " +
                "FROM income i " +
                "JOIN income_categories ic ON i.income_category_id = ic.id " +
                "WHERE i.income_date BETWEEN ? AND ? AND i.user_id = ?";
        return jdbcTemplate.query(sql, new Object[] { startDate, endDate, user_Id },
                new BeanPropertyRowMapper<>(Income.class));
    }

    @Override
    public List<Income> getIncomeByCustomDateAndCategory(LocalDate startDate, LocalDate endDate, String category,
            Integer user_Id) {
        String sql = "SELECT i.id, i.amount, ic.income_category_name as category, i.description, i.income_date as date "
                +
                "FROM income i " +
                "JOIN income_categories ic ON i.income_category_id = ic.id " +
                "WHERE i.income_date BETWEEN ? AND ? AND ic.income_category_name = ? AND i.user_id = ? ";

        return jdbcTemplate.query(sql, new Object[] { startDate, endDate, category, user_Id },
                new BeanPropertyRowMapper<>(Income.class));
    }

    @Override
    public void editIncome(Integer id, Double amount, String category, LocalDate localDate, String description, Integer userId) {
        String sql = "UPDATE income SET amount = ?, income_category_id = (SELECT id FROM income_categories WHERE income_category_name = ?), " +
                "description = ?, income_date = ?, user_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, category, description, localDate, userId, id);
    }

    @Override
    public void deleteIncome(Integer id, Integer userId) {
        String sql = "DELETE FROM income WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }

}
