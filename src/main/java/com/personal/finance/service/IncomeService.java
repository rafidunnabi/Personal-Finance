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

    public List<Income> findIncomeByCustomDate(LocalDate startDate, LocalDate endDate, Integer user_id) {
        return incomeRepository.getIncomeByCustomDate(startDate, endDate, user_id);
    }

    public List<Income> findIncomeByCustomDateAndCategory(LocalDate startDate, LocalDate endDate, String category,
            Integer user_id) {
        return incomeRepository.getIncomeByCustomDateAndCategory(startDate, endDate, category, user_id);
    }

    public void editIncomeRecord(Integer id, Double amount, String category, LocalDate localDate, String description, Integer userId) {
        incomeRepository.editIncome(id, amount, category, localDate, description, userId);
    }

    public void deleteIncomeRecord(Integer id, Integer userId) {
        incomeRepository.deleteIncome(id, userId);
    }

    public List<Income> searchIncomeByCategory(String selectedCategory, Integer userId) {
        return incomeRepository.searchIncomeByCategory(selectedCategory,userId);
    }
}
