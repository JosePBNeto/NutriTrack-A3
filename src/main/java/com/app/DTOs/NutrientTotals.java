package com.app.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutrientTotals {
    private MacronutrientTotals macronutrients;
    private Map<String, MicronutrientTotal> micronutrients;
}

