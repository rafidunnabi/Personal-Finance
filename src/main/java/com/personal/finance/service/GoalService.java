package com.personal.finance.service;

import com.personal.finance.model.Goal;
import com.personal.finance.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    private GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> getRecentGoals(Integer userId){
        return goalRepository.getRecentGoals(userId);
    }
}
