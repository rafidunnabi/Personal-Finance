package com.personal.finance.configuration;

import com.personal.finance.service.CreateTablesService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    private final CreateTablesService createTablesService;

    public DatabaseConfig(CreateTablesService createTablesService) {
        this.createTablesService = createTablesService;
    }


    @PostConstruct
    public void initializeDatabase() {
        createTablesService.initializeDatabase();
    }
}
