package com.personal.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping
    String getPeople(Model model) {
        model.addAttribute("something", "this is coming from the controller.");
        return "homePage";
    }
    @GetMapping("/addExpense")
    public String addExpensePage() {
        return "addExpense";
    }
    @GetMapping("/addIncome")
    public String addIncomePage() {
        return "addIncome";
    }
    @GetMapping("/chooseDate")
    public String chooseDatePage() {
        return "chooseDate";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
