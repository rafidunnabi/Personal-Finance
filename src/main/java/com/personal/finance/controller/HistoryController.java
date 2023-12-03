package com.personal.finance.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.personal.finance.model.Expense;
import com.personal.finance.model.Income;
import com.personal.finance.repository.UserRepository;
import com.personal.finance.service.ExpenseService;
import com.personal.finance.service.IncomeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HistoryController {

    private final UserRepository userRepository;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public HistoryController(UserRepository userRepository, IncomeService incomeService,
            ExpenseService expenseService) {
        this.userRepository = userRepository;
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    @PostMapping("/customHistoryIncome")
    public String customHistoryIncome(@RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String category, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());
        model.addAttribute("income", new Income());
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate parsedStartDate = LocalDate.parse(startDate, inputFormatter);
        LocalDate parsedEndDate = LocalDate.parse(endDate, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy");

        String formattedStartDate = parsedStartDate.format(outputFormatter);
        String formattedEndDate = parsedEndDate.format(outputFormatter);

        model.addAttribute("startDate", formattedStartDate);
        model.addAttribute("endDate", formattedEndDate);

        List<Income> incomeList;

        if (category != null && !category.isEmpty()) {
            incomeList = incomeService.findIncomeByCustomDateAndCategory(parsedStartDate, parsedEndDate, category,
                    userId);
        } else {
            incomeList = incomeService.findIncomeByCustomDate(parsedStartDate, parsedEndDate, userId);
        }
        model.addAttribute("incomeList", incomeList);

        double totalIncome = incomeList.stream().mapToDouble(Income::getAmount).sum();
        model.addAttribute("totalIncome", totalIncome);

        return "seeAllIncomes";
    }

    @PostMapping("/customHistoryExpense")
    public String customHistoryExpense(@RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String category, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());
        model.addAttribute("expense", new Expense());
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate parsedStartDate = LocalDate.parse(startDate, inputFormatter);
        LocalDate parsedEndDate = LocalDate.parse(endDate, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy");

        String formattedStartDate = parsedStartDate.format(outputFormatter);
        String formattedEndDate = parsedEndDate.format(outputFormatter);

        model.addAttribute("startDateExpense", formattedStartDate);
        model.addAttribute("endDateExpense", formattedEndDate);

        List<Expense> expenseList;

        if (category != null && !category.isEmpty()) {
            expenseList = expenseService.findExpenseByCustomDateAndCategory(parsedStartDate, parsedEndDate, category,
                    userId);
        } else {
            expenseList = expenseService.findExpenseByCustomDate(parsedStartDate, parsedEndDate, userId);
        }
        model.addAttribute("expenseList", expenseList);

        double totalExpense = expenseList.stream().mapToDouble(Expense::getAmount).sum();
        model.addAttribute("totalExpense", totalExpense);

        return "seeAllExpenses";
    }
}
