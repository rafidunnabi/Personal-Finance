package com.personal.finance.controller;

import com.personal.finance.model.Expense;
import com.personal.finance.model.Income;
import com.personal.finance.repository.UserRepository;
import com.personal.finance.service.ExpenseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/editExpense")
    public String editIncome(@ModelAttribute("expense") Expense expense,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());
        if (userId != null) {
            LocalDate localDate = LocalDate.parse(expense.getDate());
            System.out.println(expense.getId());
            expenseService.editExpenseRecord(expense.getId(), expense.getAmount(), expense.getCategory(), localDate,
                    expense.getDescription(), userId);
            model.addAttribute("userId", userId);
        } else {
        }
        return "redirect:/addExpense";
    }

    @PostMapping("/deleteExpense")
    public String deleteIncome(@ModelAttribute("expense") Expense expense,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        if (userId != null) {
            expenseService.deleteExpenseRecord(expense.getId(), userId);
            model.addAttribute("userId", userId);
        } else {
            // Handle the case where the user ID is null
        }

        return "redirect:/addExpense";
    }


    @GetMapping("/seeAllExpenses")
    public String seeAllExpenses(Model model) {
        model.addAttribute("expense", new Expense());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        List<Expense> recentExpenses = expenseService.getRecentExpenses(userId);
        model.addAttribute("recentExpenses", recentExpenses);

        Double totalExpense = recentExpenses.stream().mapToDouble(Expense::getAmount).sum();
        model.addAttribute("totalExpense", totalExpense);
        return "seeAllExpenses";
    }

    @PostMapping("/deleteSeeAllExpenses")
    public String deleteSeeAllExpenses(@ModelAttribute("expense") Expense expense,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        if (userId != null) {
            expenseService.deleteExpenseRecord(expense.getId(), userId);
            model.addAttribute("userId", userId);
        } else {
            // Handle the case where the user ID is null
        }

        return "redirect:/seeAllExpenses";
    }

    @PostMapping("/searchExpenseByCategory")
    public String searchExpenseByCategory(@RequestParam("selectedCategoryExpense") String selectedCategory, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());
        model.addAttribute("expense", new Expense());
        if (userId != null) {
            if(selectedCategory.equals("All"))
            {
                return "redirect:/seeAllExpenses";
            }
            List<Expense> expenseByCategoryList = expenseService.searchExpenseByCategory(selectedCategory, userId);
            model.addAttribute("expenseList", expenseByCategoryList);
            model.addAttribute("isCategoryClickedExpense", true);
            double totalExpense = expenseByCategoryList.stream().mapToDouble(Expense::getAmount).sum();
            model.addAttribute("totalExpense", totalExpense);

        } else {
        }
        return "seeAllExpenses";
    }



    @PostMapping("/editExpenseSearchByDate")
    public String editExpenseSearchByDate(@ModelAttribute("expense") Expense expense,
                                         Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());
        if (userId != null) {
            LocalDate localDate = LocalDate.parse(expense.getDate());

            expenseService.editExpenseRecord(expense.getId(), expense.getAmount(), expense.getCategory(), localDate,
                    expense.getDescription(), userId);
        } else {
        }
        return "redirect:/seeAllExpenses";
    }
}
