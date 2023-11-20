package com.personal.finance.service;

import com.personal.finance.model.Income;
import com.personal.finance.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public void saveIncome(double amount, String category, String date, String description) {
        incomeRepository.saveIncome(amount, category, date, description);
    }

    public void addIncomeRecord(double amount, String category, LocalDate date, String description, Integer userId) {
        incomeRepository.addIncome(amount, category, date, description, userId);
    }

    public List<Income> getRecentIncomes(Integer userId) {
        return incomeRepository.getRecentIncomes(userId);
    }
}
