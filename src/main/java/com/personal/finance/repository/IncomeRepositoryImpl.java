package com.personal.finance.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
