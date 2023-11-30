package com.personal.finance.repository;

import com.personal.finance.model.Budget;
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

    @Override
    public List<Budget> getBudgetsByDateRangeAndUser(List<Budget> budgets, Integer userId) {

        String sql = "SELECT " +
                "    b.id AS budget_id, " +
                "    b.user_id, " +
                "    b.amount AS budget_amount, " +
                "    bc.category_name AS budget_category, " +
                "    b.description AS budget_description, " +
                "    b.start_date AS budget_start_date, " +
                "    b.end_date AS budget_end_date, " +
                "    COALESCE(SUM(e.amount), 0) AS spent_amount, " +
                "    CASE " +
                "        WHEN SUM(e.amount) IS NULL THEN b.amount " +
                "        ELSE b.amount - COALESCE(SUM(e.amount), 0) " +
                "    END AS remaining_amount " +
                "FROM " +
                "    budget b " +
                "LEFT JOIN " +
                "    expense e ON e.expense_date BETWEEN b.start_date AND b.end_date " +
                "LEFT JOIN " +
                "    budget_categories bc ON b.budget_category_id = bc.id " +
                "LEFT JOIN " +
                "    expense_categories ec ON e.expense_category_id = ec.id " +
                "WHERE " +
                "    b.user_id = ? " +
                "    AND b.start_date <= ?::date AND b.end_date >= ?::date " +
                "    AND ec.expense_category_name = ? " +
                "GROUP BY " +
                "    b.id, b.user_id, b.amount, bc.category_name, b.description, b.start_date, b.end_date";


        for (Budget budget : budgets) {
            List<Budget> updatedBudgets = jdbcTemplate.query(
                    sql,
                    new Object[]{userId, budget.getStartDate(), budget.getEndDate(), budget.getCategory()},
                    (rs, rowNum) -> {
                        Budget updatedBudget = new Budget();
                        updatedBudget.setId(rs.getInt("budget_id"));
                        updatedBudget.setUser_id(rs.getInt("user_id"));
                        updatedBudget.setAmount(rs.getDouble("budget_amount"));
                        updatedBudget.setCategory(rs.getString("budget_category"));
                        updatedBudget.setDescription(rs.getString("budget_description"));
                        updatedBudget.setStartDate(rs.getString("budget_start_date"));
                        updatedBudget.setEndDate(rs.getString("budget_end_date"));
                        updatedBudget.setSpentAmount(rs.getDouble("spent_amount"));
                        updatedBudget.setRemainingAmount(rs.getDouble("remaining_amount"));
                        if (updatedBudget.getSpentAmount() == null) {
                            updatedBudget.setSpentAmount(0.0);
                        }

                        updatedBudget.setRemainingAmount(updatedBudget.getAmount() - updatedBudget.getSpentAmount());
                        return updatedBudget;
                    }
            );

            // Update the spentAmount and remainingAmount for the current budget
            if (!updatedBudgets.isEmpty()) {
                Budget updatedBudget = updatedBudgets.get(0);
                updatedBudgets.clear();
                budget.setSpentAmount(updatedBudget.getSpentAmount());
                budget.setRemainingAmount(updatedBudget.getRemainingAmount());
            }
            else{
                budget.setSpentAmount(0.0);
                budget.setRemainingAmount(budget.getAmount());
            }
        }

        return budgets;
    }

    @Override
    public void deleteBudgetById(Integer id, Integer userId) {
        String sql = "DELETE FROM budget WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public void editBudget(Double amount, String category, LocalDate localStartDate, LocalDate localEndDate, String description, Integer userId, Integer id) {
        System.out.println("Amount : " + amount);
        System.out.println("Category : " + category);
        System.out.println("StartDate : " + localStartDate);
        System.out.println("End Date : " + localEndDate);
        System.out.println("Description : " + description);
        System.out.println("UserId : " + userId);
        System.out.println("Id : " + id);
        String sql = "UPDATE budget SET amount = ?, budget_category_id = (SELECT id FROM budget_categories WHERE category_name = ?), " +
                "start_date = ?, end_date = ?, description = ?, user_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, category, localStartDate, localEndDate, description, userId, id);
    }
}
