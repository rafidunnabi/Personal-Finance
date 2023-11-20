package com.personal.finance.repository;

import java.time.LocalDate;

public interface IncomeRepository {

    void saveIncome(double amount, String category, String date, String description);

    void addIncome(double amount, String category, LocalDate date, String description, Integer userId);
}
