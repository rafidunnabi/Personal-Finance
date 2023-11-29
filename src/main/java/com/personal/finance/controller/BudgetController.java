package com.personal.finance.controller;

import com.personal.finance.model.Budget;
import com.personal.finance.repository.UserRepository;
import com.personal.finance.service.BudgetService;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class BudgetController {

    private final UserRepository userRepository;
    private final BudgetService budgetService;

    public BudgetController(UserRepository userRepository, BudgetService budgetService) {
        this.userRepository = userRepository;
        this.budgetService = budgetService;
    }

    @GetMapping("/budget")
    public String showRecentBudgets(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        List<Budget> recentBudgets = budgetService.getRecentBudgets(userId);
//        for (Budget budget : recentBudgets) {
//            LocalDate localStartDate = LocalDate.parse(budget.getStartDate());
//            budget.setStartDate(localStartDate.format(DateTimeFormatter.ofPattern("d MMM, yyyy")));
//
//            LocalDate localEndDate = LocalDate.parse(budget.getEndDate());
//            budget.setEndDate(localEndDate.format(DateTimeFormatter.ofPattern("d MMM, yyyy")));
//        }

        List<Budget> updatedBudgets = budgetService.getBudgetsAndUpdateAmounts(recentBudgets, userId);

        model.addAttribute("budget", new Budget());
        model.addAttribute("recentBudgets", updatedBudgets);

        return "budget";
    }

    @PostMapping("/addBudget")
    public String addBudgetRecord(
            @ModelAttribute("budget") Budget budget,
            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        if (userId != null) {
            LocalDate localStartDate = LocalDate.parse(budget.getStartDate());
            LocalDate localEndDate = LocalDate.parse(budget.getEndDate());
            budgetService.addBudgetRecord(budget.getAmount(), budget.getCategory(), localStartDate, localEndDate,
                    budget.getDescription(),
                    userId);
            model.addAttribute("userId", userId);
        } else {
        }
        return "redirect:/budget";
    }
}
