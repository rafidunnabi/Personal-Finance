package com.personal.finance.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CreateTableRepositoryImpl implements CreateTableRepository {
    private final JdbcTemplate jdbcTemplate;

    public CreateTableRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "user_id SERIAL PRIMARY KEY,"
                + "user_name VARCHAR(255) NOT NULL,"
                + "email VARCHAR(255) NOT NULL,"
                + "password VARCHAR(255) NOT NULL"
                + ")";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void createIncomeTable() {
        String sql = "CREATE TABLE IF NOT EXISTS income ("
                + "income_id SERIAL PRIMARY KEY,"
                + "user_id INT REFERENCES users(user_id),"
                + "amount DOUBLE PRECISION NOT NULL,"
                + "income_category_id INT REFERENCES income_categories(income_category_id),"
                + "description TEXT,"
                + "income_date DATE NOT NULL"
                + ")";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void createIncomeCategoriesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS income_categories ("
                + "income_category_id SERIAL PRIMARY KEY,"
                + "income_category_name VARCHAR(255) NOT NULL"
                + ")";
        jdbcTemplate.execute(sql);
    }
}
