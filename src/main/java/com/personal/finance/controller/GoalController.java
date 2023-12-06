package com.personal.finance.controller;

import com.personal.finance.model.Goal;
import com.personal.finance.repository.UserRepository;
import com.personal.finance.service.GoalService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GoalController {

    private final UserRepository userRepository;
    private final GoalService goalService;

    public GoalController(UserRepository userRepository, GoalService goalService) {
        this.userRepository = userRepository;
        this.goalService = goalService;
    }

    @GetMapping("/goal")
    public String goal(Model model){
        Integer userId = getAuthenticatedUserId();
        List<Goal> recentGoals = goalService.getRecentGoals(userId);
        model.addAttribute("recentGoals", recentGoals);
        return "goal";
    }
    public Integer getAuthenticatedUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserIdByEmail(authentication.getName());
    }
}
