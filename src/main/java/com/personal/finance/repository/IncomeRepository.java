package com.personal.finance.repository;

import com.personal.finance.model.Income;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository {

    void saveIncome(double amount, String category, String date, String description);

    void addIncome(double amount, String category, LocalDate date, String description, Integer userId);

    List<Income> getRecentIncomes(Integer userId);

    List<Income> getIncomeByCustomDate(LocalDate startDate, LocalDate endDate, Integer user_Id);

    List<Income> getIncomeByCustomDateAndCategory(LocalDate startDate, LocalDate endDate, String category,
            Integer user_Id);

    void editIncome(Integer id, Double amount, String category, LocalDate localDate, String description, Integer userId);

    void deleteIncome(Integer id, Integer userId);

    List<Income> searchIncomeByCategory(String selectedCategory, Integer userId);
}
