package com.app.DTOs;

import lombok.Data;

import java.util.Map;

@Data
public class NutrientTotals {
    private MacronutrientTotals macronutrients;
    private Map<String, MicronutrientTotal> micronutrients;
}

