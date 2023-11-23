package com.personal.finance.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.personal.finance.repository.UserRepository;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    String getPeople(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = userRepository.findUserNameByEmail(authentication.getName());
        System.out.println(userName);
        model.addAttribute("username", userName);
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
