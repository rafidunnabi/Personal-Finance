package com.personal.finance.service;

import com.personal.finance.repository.IncomeRepository;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public void saveIncome(double amount, String category, String date, String description) {
        incomeRepository.saveIncome(amount, category, date, description);
    }
}
