package com.personal.finance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goal {
    private Integer id;
    private Integer userId;
    private String title;
    private Double targetAmount;
    private Double currentAmount;
    private String targetDate;
    private Integer goalCategoryId;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
