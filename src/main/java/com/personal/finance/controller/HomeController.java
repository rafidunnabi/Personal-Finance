package com.personal.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping
    String getPeople(Model model) {
        return "homePage";
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
