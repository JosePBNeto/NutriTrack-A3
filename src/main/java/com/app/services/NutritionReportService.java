package com.app.services;

import com.app.DTOs.NutrientTotals;
import com.app.model.DailyIntake;
import com.app.model.Food;
import com.app.repositories.DailyIntakeRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class NutritionReportService {
    @Autowired
    private DailyIntakeRepository dailyIntakeRepository;

    @Autowired
    private NutritionCalculator nutritionCalculator;

    public NutrientTotals generateDailyReport(LocalDate date) {
        List<DailyIntake> intakes = dailyIntakeRepository.findByDateBetween(date, date);

        if (intakes.isEmpty()) {
            throw new ValidationException("No food intake recorded for date: " + date);
        }

        // Extrair todos os alimentos consumidos no dia
        List<Food> consumedFoods = intakes.stream()
                .flatMap(intake -> intake.getFoods().stream())
                .toList();

        return nutritionCalculator.calculateTotals(consumedFoods);
    }

    public NutrientTotals generatePeriodReport(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date cannot be after end date");
        }

        List<DailyIntake> intakes = dailyIntakeRepository.findByDateBetween(startDate, endDate);

        if (intakes.isEmpty()) {
            throw new ValidationException(
                    String.format("No food intake recorded between %s and %s", startDate, endDate));
        }

        List<Food> consumedFoods = intakes.stream()
                .flatMap(intake -> intake.getFoods().stream())
                .toList();

        return nutritionCalculator.calculateTotals(consumedFoods);
    }
}
