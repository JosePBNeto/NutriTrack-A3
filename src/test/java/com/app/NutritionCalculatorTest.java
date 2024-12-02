package com.app;

import com.app.DTOs.MacronutrientTotals;
import com.app.DTOs.MicronutrientTotal;
import com.app.DTOs.NutrientTotals;
import com.app.model.Food;
import com.app.model.Macronutrients;
import com.app.model.Micronutrient;
import com.app.services.NutritionCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NutritionCalculatorTest {
    private NutritionCalculator nutritionCalculator;

    @BeforeEach
    void setUp() {
        nutritionCalculator = new NutritionCalculator();
    }


    @Test
    void calculateTotals_EmptyFoodList_ShouldReturnZeroTotals() {

        NutrientTotals totals = nutritionCalculator.calculateTotals(Collections.emptyList());


        MacronutrientTotals macroTotals = totals.getMacronutrients();
        assertEquals(0.0, macroTotals.getTotalProteins());
        assertEquals(0.0, macroTotals.getTotalCarbohydrates());
        assertEquals(0.0, macroTotals.getTotalFats());
        assertEquals(0.0, macroTotals.getTotalCalories());


        assertTrue(totals.getMicronutrients().isEmpty());
    }
}
