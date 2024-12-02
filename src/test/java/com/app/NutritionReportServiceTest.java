package com.app;

import com.app.DTOs.NutrientTotals;
import com.app.model.DailyIntake;
import com.app.model.Food;
import com.app.repositories.DailyIntakeRepository;
import com.app.services.NutritionCalculator;
import com.app.services.NutritionReportService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NutritionReportServiceTest {

    @InjectMocks
    private NutritionReportService nutritionReportService;

    @Mock
    private DailyIntakeRepository dailyIntakeRepository;

    @Mock
    private NutritionCalculator nutritionCalculator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateDailyReport_ValidData_ShouldReturnNutrientTotals() {
        LocalDate date = LocalDate.of(2024, 12, 1);
        DailyIntake dailyIntake = mock(DailyIntake.class);
        Food food = mock(Food.class);
        NutrientTotals expectedTotals = mock(NutrientTotals.class);

        when(dailyIntake.getFoods()).thenReturn(List.of(food));
        when(dailyIntakeRepository.findByDateBetween(date, date)).thenReturn(List.of(dailyIntake));
        when(nutritionCalculator.calculateTotals(List.of(food))).thenReturn(expectedTotals);

        NutrientTotals result = nutritionReportService.generateDailyReport(date);

        assertEquals(expectedTotals, result);
        verify(dailyIntakeRepository).findByDateBetween(date, date);
        verify(nutritionCalculator).calculateTotals(List.of(food));
    }

    @Test
    void generateDailyReport_NoData_ShouldThrowValidationException() {
        LocalDate date = LocalDate.of(2024, 12, 1);
        when(dailyIntakeRepository.findByDateBetween(date, date)).thenReturn(Collections.emptyList());

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> nutritionReportService.generateDailyReport(date)
        );

        assertEquals("Sem registros na data: " + date, exception.getMessage());
        verify(dailyIntakeRepository).findByDateBetween(date, date);
    }

    @Test
    void generatePeriodReport_ValidData_ShouldReturnNutrientTotals() {
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        DailyIntake dailyIntake = mock(DailyIntake.class);
        Food food = mock(Food.class);
        NutrientTotals expectedTotals = mock(NutrientTotals.class);

        when(dailyIntake.getFoods()).thenReturn(List.of(food));
        when(dailyIntakeRepository.findByDateBetween(startDate, endDate)).thenReturn(List.of(dailyIntake));
        when(nutritionCalculator.calculateTotals(List.of(food))).thenReturn(expectedTotals);

        NutrientTotals result = nutritionReportService.generatePeriodReport(startDate, endDate);

        assertEquals(expectedTotals, result);
        verify(dailyIntakeRepository).findByDateBetween(startDate, endDate);
        verify(nutritionCalculator).calculateTotals(List.of(food));
    }

    @Test
    void generatePeriodReport_InvalidDates_ShouldThrowValidationException() {
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> nutritionReportService.generatePeriodReport(startDate, endDate)
        );

        assertEquals("Data inicial náo pode ser depois da data final", exception.getMessage());
    }

    @Test
    void generatePeriodReport_NoData_ShouldThrowValidationException() {
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        when(dailyIntakeRepository.findByDateBetween(startDate, endDate)).thenReturn(Collections.emptyList());

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> nutritionReportService.generatePeriodReport(startDate, endDate)
        );

        assertEquals(
                String.format("Sem registro de consumo entre %s e %s", startDate, endDate),
                exception.getMessage()
        );
        verify(dailyIntakeRepository).findByDateBetween(startDate, endDate);
    }
}
