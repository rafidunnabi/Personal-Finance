package com.personal.finance.controller;

import com.fasterxml.jackson.databind.type.ClassStack;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.personal.finance.dto.ChartDataDto;
import com.personal.finance.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                userId, formattedFirstDay, formattedLastDay);

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
                userId, formattedFirstDay, formattedLastDay);
        ChartDataDto chartData = new ChartDataDto(incomeData, expenseData);
        // Add the data to the model
        model.addAttribute("chartData", chartData);

        //Monthly Update Bar Chart
        LocalDate firstDayOfCurrentMonthBar = LocalDate.now().withDayOfMonth(1);
        LocalDate firstDayOfPreviousMonthBar = firstDayOfCurrentMonthBar.minusMonths(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String firstDayFormatted = firstDayOfPreviousMonthBar.format(formatter);
        String lastDayFormatted = firstDayOfCurrentMonthBar.minusDays(1).format(formatter);

        String firstDayCurrentFormatted = firstDayOfCurrentMonthBar.format(formatter);
        String lastDayCurrentFormatted = firstDayOfCurrentMonthBar.withDayOfMonth(firstDayOfCurrentMonthBar.lengthOfMonth()).format(formatter);

        List<Map<String, Object>> incomeDataMonthlyBar = jdbcTemplate.queryForList(
                "SELECT SUM(amount) as total FROM income i " +
                        "WHERE i.income_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND user_id = ? ",
                firstDayFormatted, lastDayFormatted, userId
        );
        List<Map<String, Object>> expenseDataMonthlyBar = jdbcTemplate.queryForList(
                "SELECT SUM(amount) as total FROM expense e " +
                        "WHERE e.expense_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND user_id = ? ",
                firstDayFormatted, lastDayFormatted, userId
        );

        List<Map<String, Object>> incomeDataMonthlyBarCurrent = jdbcTemplate.queryForList(
                "SELECT SUM(amount) as total FROM income i " +
                        "WHERE i.income_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND user_id = ? ",
                firstDayCurrentFormatted, lastDayCurrentFormatted, userId
        );
        List<Map<String, Object>> expenseDataMonthlyBarCurrent = jdbcTemplate.queryForList(
                "SELECT SUM(amount) as total FROM expense e " +
                        "WHERE e.expense_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND user_id = ? ",
                firstDayCurrentFormatted, lastDayCurrentFormatted, userId
        );

        model.addAttribute("incomeDataMonthlyBar", incomeDataMonthlyBar);
        model.addAttribute("expenseDataMonthlyBar", expenseDataMonthlyBar);
        model.addAttribute("incomeDataCurrentMonth", incomeDataMonthlyBarCurrent);
        model.addAttribute("expenseDataCurrentMonth", expenseDataMonthlyBarCurrent);
        model.addAttribute("BarTitleMonthlyBar", firstDayOfPreviousMonthBar.getMonth().toString());
        model.addAttribute("BarTitleCurrentMonth", firstDayOfCurrentMonthBar.getMonth().toString());

        LocalDate firstDayOfCurrentYearBar = LocalDate.now().withDayOfYear(1);
        LocalDate firstDayOfPreviousYearBar = firstDayOfCurrentYearBar.minusYears(1);

        DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String firstDayFormattedYear = firstDayOfPreviousYearBar.format(formatterYear);
        String lastDayFormattedYear = firstDayOfCurrentYearBar.minusDays(1).format(formatterYear);

        String firstDayCurrentFormattedYear = firstDayOfCurrentYearBar.format(formatterYear);
        String lastDayCurrentFormattedYear = firstDayOfCurrentYearBar.withDayOfYear(firstDayOfCurrentYearBar.lengthOfYear()).format(formatterYear);

        List<Map<String, Object>> incomeDataYearlyBar = jdbcTemplate.queryForList(
                "SELECT SUM(amount) as total FROM income i " +
                        "WHERE i.income_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND user_id = ? ",
                firstDayFormattedYear, lastDayFormattedYear, userId
        );
        List<Map<String, Object>> expenseDataYearlyBar = jdbcTemplate.queryForList(
                "SELECT SUM(amount) as total FROM expense e " +
                        "WHERE e.expense_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND user_id = ? ",
                firstDayFormattedYear, lastDayFormattedYear, userId
        );

        List<Map<String, Object>> incomeDataYearlyBarCurrent = jdbcTemplate.queryForList(
                "SELECT SUM(amount) as total FROM income i " +
                        "WHERE i.income_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND user_id = ? ",
                firstDayCurrentFormattedYear, lastDayCurrentFormattedYear, userId
        );
        List<Map<String, Object>> expenseDataYearlyBarCurrent = jdbcTemplate.queryForList(
                "SELECT SUM(amount) as total FROM expense e " +
                        "WHERE e.expense_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND user_id = ? ",
                firstDayCurrentFormattedYear, lastDayCurrentFormattedYear, userId
        );

        model.addAttribute("incomeDataYearlyBar", incomeDataYearlyBar);
        model.addAttribute("expenseDataYearlyBar", expenseDataYearlyBar);
        model.addAttribute("incomeDataCurrentYear", incomeDataYearlyBarCurrent);
        model.addAttribute("expenseDataCurrentYear", expenseDataYearlyBarCurrent);
        model.addAttribute("BarTitleYearlyBar", firstDayOfPreviousYearBar.getYear());
        model.addAttribute("BarTitleCurrentYear", firstDayOfCurrentYearBar.getYear());

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

    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "aboutUs";
    }
}
