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
                + "id SERIAL PRIMARY KEY,"
                + "user_name VARCHAR(255) NOT NULL,"
                + "email VARCHAR(255) NOT NULL,"
                + "password VARCHAR(255) NOT NULL"
                + ")";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void createIncomeTable() {
        String sql = "CREATE TABLE IF NOT EXISTS income ("
                + "id SERIAL PRIMARY KEY,"
                + "user_id INT REFERENCES users(id),"
                + "amount DOUBLE PRECISION NOT NULL,"
                + "income_category_id INT REFERENCES income_categories(id),"
                + "description TEXT,"
                + "income_date DATE NOT NULL"
                + ")";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void createIncomeCategoriesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS income_categories ("
                + "id SERIAL PRIMARY KEY,"
                + "income_category_name VARCHAR(255) NOT NULL"
                + ")";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void createExpenseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS expense ("
                + "id SERIAL PRIMARY KEY,"
                + "user_id INT REFERENCES users(id),"
                + "amount DOUBLE PRECISION NOT NULL,"
                + "expense_category_id INT REFERENCES expense_categories(id),"
                + "description TEXT,"
                + "expense_date DATE NOT NULL"
                + ")";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void createExpenseCategoriesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS expense_categories ("
                + "id SERIAL PRIMARY KEY,"
                + "expense_category_name VARCHAR(255) NOT NULL"
                + ")";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void createBudgetCategoriesTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS budget_categories ("
                + "id SERIAL PRIMARY KEY,"
                + "category_name VARCHAR(255) NOT NULL"
                + ")";

        jdbcTemplate.execute(createTableQuery);
    }

    @Override
    public void createBudgetTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS budget ("
                + "id SERIAL PRIMARY KEY,"
                + "user_id INT,"
                + "amount DOUBLE PRECISION NOT NULL,"
                + "budget_category_id INT,"
                + "description VARCHAR(255),"
                + "start_date DATE,"
                + "end_date DATE,"
                + "spent_amount DOUBLE PRECISION DEFAULT 0,"
                + "remaining_amount DOUBLE PRECISION,"
                + "FOREIGN KEY (user_id) REFERENCES users(id),"
                + "FOREIGN KEY (budget_category_id) REFERENCES budget_categories(id)"
                + ")";

        jdbcTemplate.execute(createTableQuery);
    }
}
