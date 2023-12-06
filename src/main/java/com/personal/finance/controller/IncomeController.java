package com.personal.finance.controller;

import com.personal.finance.model.Income;
import com.personal.finance.model.User;
import com.personal.finance.repository.UserRepository;
import com.personal.finance.service.IncomeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        double totalIncome = recentIncomes.stream().mapToDouble(Income::getAmount).sum();
        model.addAttribute("totalIncome", totalIncome);

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

            incomeService.addIncomeRecord(income.getAmount(), income.getCategory(), localDate, income.getDescription(),
                    userId);
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

    @PostMapping("/editIncome")
    public String editIncome(@ModelAttribute("income") Income income,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());
        if (userId != null) {
            LocalDate localDate = LocalDate.parse(income.getDate());
            System.out.println(income.getId());
            incomeService.editIncomeRecord(income.getId(), income.getAmount(), income.getCategory(), localDate,
                    income.getDescription(), userId);
            model.addAttribute("userId", userId);
        } else {
        }
        return "redirect:/addIncome";
    }

    @PostMapping("/deleteIncome")
    public String deleteIncome(@ModelAttribute("income") Income income,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());
        System.out.println("Delete controller e ashche.");
        System.out.println(income.getId());
        if (userId != null) {
            incomeService.deleteIncomeRecord(income.getId(), userId);
            model.addAttribute("userId", userId);
        } else {

        }

        return "redirect:/addIncome";
    }

    @GetMapping("/seeAllIncomes")
    public String seeAllIncomes(Model model) {
        model.addAttribute("income", new Income());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        List<Income> recentIncomes = incomeService.getRecentIncomes(userId);
        model.addAttribute("recentIncomes", recentIncomes);
        double totalIncome = recentIncomes.stream().mapToDouble(Income::getAmount).sum();
        model.addAttribute("totalIncome", totalIncome);

        return "seeAllIncomes";
    }

    @PostMapping("/deleteSeeAllIncomes")
    public String deleteSeeAllIncome(@ModelAttribute("income") Income income,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());

        if (userId != null) {
            incomeService.deleteIncomeRecord(income.getId(), userId);
            model.addAttribute("userId", userId);
        } else {
        }

        return "redirect:/seeAllIncomes";
    }

    @PostMapping("/searchIncomeByCategory")
    public String searchIncomeByCategory(@RequestParam("selectedCategory") String selectedCategory, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());
        model.addAttribute("income", new Income());
        if (userId != null) {

            if(selectedCategory.equals("All"))
            {
                return "redirect:/seeAllIncomes";
            }
            List<Income> incomeByCategoryList = incomeService.searchIncomeByCategory(selectedCategory, userId);
            model.addAttribute("incomeList", incomeByCategoryList);
            model.addAttribute("isCategoryClicked", true);
            double totalIncome = incomeByCategoryList.stream().mapToDouble(Income::getAmount).sum();
            model.addAttribute("totalIncome", totalIncome);
        }
        return "seeAllIncomes";
    }

    @PostMapping("/editIncomeSearchByDate")
    public String editIncomeSearchByDate(@ModelAttribute("income") Income income,
                             Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());
        if (userId != null) {
            LocalDate localDate = LocalDate.parse(income.getDate());
            System.out.println(income.getId());
            incomeService.editIncomeRecord(income.getId(), income.getAmount(), income.getCategory(), localDate,
                    income.getDescription(), userId);
            model.addAttribute("userId", userId);
        } else {
        }
        return "redirect:/seeAllIncomes";
    }

}
