package com.personal.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.*;


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
            this.incomeData.add((Double) entry.get("total_income"));
        }

        // Convert expense data
        for (Map<String, Object> entry : expenseData) {
            Date date = (Date) entry.get("expense_date");
            this.expenseData.add((Double) entry.get("total_expense"));
        }

        // Fill in missing dates with zero amounts for expenses
        fillMissingDates(incomeData, expenseData);
    }

    private void fillMissingDates(List<Map<String, Object>> incomeData, List<Map<String, Object>> expenseData) {

        for (Map<String, Object> entry : incomeData) {
            Date date = (Date) entry.get("income_date");
            String formattedDate = formatDate(date);

            if (!labels.contains(formattedDate)) {
                labels.add(formattedDate);
                this.expenseData.add(0.0);
                this.incomeData.add((Double) entry.get("total_income")); // Create a zero expense entry
            }
        }


        for (Map<String, Object> entry : expenseData) {
            Date date = (Date) entry.get("expense_date");
            String formattedDate = formatDate(date);

            if (!labels.contains(formattedDate)) {
                labels.add(formattedDate);
                this.incomeData.add(0.0);
                this.expenseData.add((Double) entry.get("total_expense"));
            }
        }
    }
    private Map<String, Object> createZeroExpenseEntry(Date date) {
        Map<String, Object> zeroExpenseEntry = new HashMap<>();
        zeroExpenseEntry.put("expense_date", date);
        zeroExpenseEntry.put("total_expense", 0.0);
        return zeroExpenseEntry;
    }
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM");
        return dateFormat.format(date);
    }
}
