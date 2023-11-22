package com.personal.finance.controller;

import com.personal.finance.model.Expense;
import com.personal.finance.repository.UserRepository;
import com.personal.finance.service.ExpenseService;
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
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserRepository userRepository;

    public ExpenseController(ExpenseService expenseService, UserRepository userRepository) {
        this.expenseService = expenseService;
        this.userRepository = userRepository;
    }

    @GetMapping("/addExpense")
    public String showAddExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        List<Expense> recentExpenses = expenseService.getRecentExpenses(userId);
        model.addAttribute("recentExpenses", recentExpenses);

        Double totalExpense = recentExpenses.stream().mapToDouble(Expense::getAmount).sum();
        model.addAttribute("totalExpense", totalExpense);
        return "addExpense";
    }

    @PostMapping("/addExpense")
    public String addExpenseRecord(
            @ModelAttribute("expense") Expense expense,
            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        if (userId != null) {
            LocalDate localDate = LocalDate.parse(expense.getDate());

            expenseService.addExpenseRecord(expense.getAmount(), expense.getCategory(), localDate,
                    expense.getDescription(), userId);
            model.addAttribute("userId", userId);
        } else {
        }
        return "redirect:/addExpense";
    }

    @GetMapping("/recentExpenses")
    public String showRecentExpenses(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        List<Expense> recentExpenses = expenseService.getRecentExpenses(userId);

        model.addAttribute("recentExpenses", recentExpenses);

        return "redirect:/addExpense";
    }
}
