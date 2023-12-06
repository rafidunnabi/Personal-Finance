package com.personal.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoalController {

    @GetMapping("/goal")
    public String goal(){
        return "goal";
    }
}
