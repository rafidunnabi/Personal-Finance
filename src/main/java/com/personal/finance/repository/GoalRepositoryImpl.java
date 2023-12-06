package com.personal.finance.repository;

import com.personal.finance.model.Budget;
import com.personal.finance.model.Goal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoalRepositoryImpl implements GoalRepository{
    private final JdbcTemplate jdbcTemplate;

    public GoalRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Goal> getRecentGoals(Integer userId) {
        String sql = "SELECT * " +
                "FROM goal g " +
                "INNER JOIN goal_categories gc ON g.goal_category_id = gc.id " +
                "WHERE g.user_id = ? " +
                "ORDER BY g.updated_at DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Goal goal = new Goal();
            goal.setId(rs.getInt("id"));
            goal.setTitle(rs.getString("title"));
            goal.setTargetAmount(rs.getDouble("target_amount"));
            goal.setCurrentAmount(rs.getDouble("current_amount"));
            goal.setTargetDate(rs.getString("target_date"));
            goal.setStatus(rs.getString("status"));
            goal.setCreatedAt(rs.getTimestamp("created_at"));
            goal.setUpdatedAt(rs.getTimestamp("updated_at"));
            return goal;
        }, userId);
    }
}
