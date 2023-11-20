package com.personal.finance.controller;

import com.personal.finance.model.Income;
import com.personal.finance.model.User;
import com.personal.finance.repository.UserRepository;
import com.personal.finance.service.IncomeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class IncomeController {

    private final IncomeService incomeService;
    private final UserRepository userRepository;

    public IncomeController(IncomeService incomeService, UserRepository userRepository) {
        this.incomeService = incomeService;
        this.userRepository = userRepository;
    }

    @GetMapping("/addIncome")
    public String showAddIncomeForm(Model model) {
        model.addAttribute("income", new Income());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        List<Income> recentIncomes = incomeService.getRecentIncomes(userId);

        model.addAttribute("recentIncomes", recentIncomes);
        return "addIncome";
    }
    @PostMapping("/addIncome")
    public String addIncomeRecord(
            @ModelAttribute("income") Income income,
            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        if (userId != null) {
            LocalDate localDate = LocalDate.parse(income.getDate());

            incomeService.addIncomeRecord(income.getAmount(), income.getCategory(), localDate, income.getDescription(), userId);
            model.addAttribute("userId", userId);
        } else {
        }
        return "redirect:/addIncome";
    }

    @GetMapping("/recentIncomes")
    public String showRecentIncomes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        List<Income> recentIncomes = incomeService.getRecentIncomes(userId);

        model.addAttribute("recentIncomes", recentIncomes);

        return "redirect:/addIncome";
    }
}
