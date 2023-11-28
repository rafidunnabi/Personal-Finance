package com.personal.finance.service;

import com.personal.finance.repository.CreateTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTablesService {
    public CreateTableRepository createTableRepository;

    @Autowired
    public CreateTablesService(CreateTableRepository createTableRepository) {
        this.createTableRepository = createTableRepository;
    }

    public void initializeDatabase() {
        createTableRepository.createUsersTable();
        createTableRepository.createIncomeCategoriesTable();
        createTableRepository.createIncomeTable();
        createTableRepository.createExpenseCategoriesTable();
        createTableRepository.createExpenseTable();
        createTableRepository.createBudgetCategoriesTable();
    }
}
