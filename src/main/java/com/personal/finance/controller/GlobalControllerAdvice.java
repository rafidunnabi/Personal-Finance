package com.personal.finance.controller;

import com.personal.finance.model.Expense;
import com.personal.finance.model.Income;
import com.personal.finance.repository.UserRepository;
import com.personal.finance.service.ExpenseService;
import com.personal.finance.service.IncomeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserRepository userRepository;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public GlobalControllerAdvice(UserRepository userRepository, IncomeService incomeService, ExpenseService expenseService) {
        this.userRepository = userRepository;
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = userRepository.findUserNameByEmail(authentication.getName());
        model.addAttribute("username", userName);
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());
        List<Income> recentIncomes = incomeService.getRecentIncomes(userId);
        double totalIncome = recentIncomes.stream().mapToDouble(Income::getAmount).sum();
        model.addAttribute("totalIncomeGlobal", totalIncome);

        List<Expense> recentExpenses = expenseService.getRecentExpenses(userId);
        Double totalExpense = recentExpenses.stream().mapToDouble(Expense::getAmount).sum();
        model.addAttribute("totalExpenseGlobal", totalExpense);
    }
}
