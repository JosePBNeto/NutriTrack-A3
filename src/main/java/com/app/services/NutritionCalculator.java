package com.app.services;

import com.app.DTOs.MacronutrientTotals;
import com.app.DTOs.MicronutrientTotal;
import com.app.DTOs.NutrientTotals;
import com.app.model.Food;
import com.app.model.Micronutrient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NutritionCalculator {
    private static final double PROTEIN_CALORIES_PER_GRAM = 4.0;
    private static final double CARB_CALORIES_PER_GRAM = 4.0;
    private static final double FAT_CALORIES_PER_GRAM = 9.0;

    public NutrientTotals calculateTotals(List<Food> foods) {
        NutrientTotals totals = new NutrientTotals();
        MacronutrientTotals macroTotals = new MacronutrientTotals();
        Map<String, MicronutrientTotal> microTotals = new HashMap<>();

        for (Food food : foods) {
            // Calcular macronutrientes
            macroTotals.setTotalProteins(macroTotals.getTotalProteins() +
                    food.getMacronutrients().getProteins());
            macroTotals.setTotalCarbohydrates(macroTotals.getTotalCarbohydrates() +
                    food.getMacronutrients().getCarbohydrates());
            macroTotals.setTotalFats(macroTotals.getTotalFats() +
                    food.getMacronutrients().getFats());

            // Calcular micronutrientes
            for (Micronutrient micro : food.getMicronutrients()) {
                microTotals.compute(micro.getName(), (key, existingTotal) -> {
                    if (existingTotal == null) {
                        existingTotal = new MicronutrientTotal();
                        existingTotal.setUnit(micro.getUnit());
                    }
                    existingTotal.setAmount(existingTotal.getAmount() + micro.getAmount());
                    return existingTotal;
                });
            }
        }

        // Calcular calorias totais
        double totalCalories = (macroTotals.getTotalProteins() * PROTEIN_CALORIES_PER_GRAM) +
                (macroTotals.getTotalCarbohydrates() * CARB_CALORIES_PER_GRAM) +
                (macroTotals.getTotalFats() * FAT_CALORIES_PER_GRAM);
        macroTotals.setTotalCalories(totalCalories);

        totals.setMacronutrients(macroTotals);
        totals.setMicronutrients(microTotals);

        return totals;
    }
}
