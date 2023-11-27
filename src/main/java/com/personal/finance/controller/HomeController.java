package com.personal.finance.controller;

import com.fasterxml.jackson.databind.type.ClassStack;
import com.personal.finance.dto.ChartDataDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.personal.finance.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;

    public HomeController(UserRepository userRepository, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    String getPeople(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = userRepository.findUserNameByEmail(authentication.getName());
        Integer userId = userRepository.findUserIdByEmail(authentication.getName());
        System.out.println(userName);
        model.addAttribute("username", userName);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfPreviousMonth = calendar.getTime();


        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDayOfPreviousMonth = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedFirstDay = sdf.format(firstDayOfPreviousMonth);
        String formattedLastDay = sdf.format(lastDayOfPreviousMonth);

        List<Map<String, Object>> incomeData = jdbcTemplate.queryForList(
                "SELECT income_date, total_income " +
                        "FROM ( " +
                        "SELECT income_date, SUM(amount) AS total_income " +
                        "FROM income " +
                        "WHERE user_id = ? " +
                        "GROUP BY income_date " +
                        ") AS incomes " +
                         "WHERE income_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) " +
                        "ORDER BY income_date",
                userId, formattedFirstDay, formattedLastDay
        );

        // Retrieve expense data from the database for the previous month
        List<Map<String, Object>> expenseData = jdbcTemplate.queryForList(
                "SELECT expense_date, total_expense " +
                        "FROM ( " +
                        "SELECT expense_date, SUM(amount) AS total_expense " +
                        "FROM expense " +
                        "WHERE user_id = ? " +
                        "GROUP BY expense_date " +
                        ") AS expenses " +
                        "WHERE expense_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) " +
                        "ORDER BY expense_date",
                userId, formattedFirstDay, formattedLastDay
        );
        System.out.println(incomeData);
        System.out.println(expenseData);
        ChartDataDto chartData = new ChartDataDto(incomeData, expenseData);

        // Add the data to the model
        model.addAttribute("chartData", chartData);
        System.out.println(chartData);
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
