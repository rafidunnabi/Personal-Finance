package com.personal.finance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
