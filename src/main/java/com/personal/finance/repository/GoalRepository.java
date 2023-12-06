package com.personal.finance.repository;

import com.personal.finance.model.Goal;

import java.util.List;

public interface GoalRepository {
    List<Goal> getRecentGoals(Integer userId);
}
