package com.personal.finance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private Double amount;
    private String category;
    private String description;
    private String date;
}
