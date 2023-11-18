package com.personal.finance.repository;

public interface IncomeRepository {

    void saveIncome(double amount, String category, String date, String description);
}
