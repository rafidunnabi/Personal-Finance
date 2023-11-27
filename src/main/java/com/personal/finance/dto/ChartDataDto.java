package com.personal.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartDataDto {
    private List<String> labels;
    private List<Double> incomeData;
    private List<Double> expenseData;

    public ChartDataDto(List<Map<String, Object>> incomeData, List<Map<String, Object>> expenseData) {
        this.labels = new ArrayList<>();
        this.incomeData = new ArrayList<>();
        this.expenseData = new ArrayList<>();

        // Convert income data
        for (Map<String, Object> entry : incomeData) {
            Date date = (Date) entry.get("income_date");
            this.labels.add(formatDate(date));
            this.incomeData.add((Double) entry.get("amount"));
        }

        // Convert expense data
        for (Map<String, Object> entry : expenseData) {
            Date date = (Date) entry.get("expense_date");
            this.expenseData.add((Double) entry.get("amount"));
        }

        // Fill in missing dates with zero amounts for expenses
        fillMissingDates(expenseData);
    }

    private void fillMissingDates(List<Map<String, Object>> expenseData) {
        for (Map<String, Object> entry : expenseData) {
            Date date = (Date) entry.get("expense_date");
            String formattedDate = formatDate(date);

            if (!labels.contains(formattedDate)) {
                labels.add(formattedDate);
                incomeData.add(0.0);
                this.expenseData.add((Double) entry.get("amount"));
            }
        }
    }
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM");
        return dateFormat.format(date);
    }
}
