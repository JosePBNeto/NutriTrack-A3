package com.app.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MacronutrientTotals {
    private double totalProteins;
    private double totalCarbohydrates;
    private double totalFats;
    private double totalCalories;
}
