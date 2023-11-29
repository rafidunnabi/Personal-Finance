package com.personal.finance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Budget {
    private Integer id;
    private Integer user_id;
    private Double amount;
    private String category;
    private String description;
    private String startDate;
    private String endDate;
    private Double spentAmount;
    private Double remainingAmount;
}
