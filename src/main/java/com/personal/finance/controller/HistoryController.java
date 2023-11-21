package com.personal.finance.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HistoryController {
    @PostMapping("/customHistory")
    public String customHistory(@RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String category, Model model) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate parsedStartDate = LocalDate.parse(startDate, inputFormatter);
        LocalDate parsedEndDate = LocalDate.parse(endDate, inputFormatter);

        // Format the parsed dates using the desired format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy");

        String formattedStartDate = parsedStartDate.format(outputFormatter);
        String formattedEndDate = parsedEndDate.format(outputFormatter);

        // Add the formatted date strings to the model
        model.addAttribute("startDate", formattedStartDate);
        model.addAttribute("endDate", formattedEndDate);

        System.out.println("Formatted Start Date: " + formattedStartDate);
        System.out.println("Formatted End Date: " + formattedEndDate);
        return "customHistory";
    }
}
