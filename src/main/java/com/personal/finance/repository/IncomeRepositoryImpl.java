package com.personal.finance.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

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
}
