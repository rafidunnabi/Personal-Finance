package com.personal.finance.repository;

import com.personal.finance.model.Income;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository {

    void saveIncome(double amount, String category, String date, String description);

    void addIncome(double amount, String category, LocalDate date, String description, Integer userId);

    List<Income> getRecentIncomes(Integer userId);
}
