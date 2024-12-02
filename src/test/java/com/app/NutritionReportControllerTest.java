package com.app;

import com.app.DTOs.MacronutrientTotals;
import com.app.DTOs.MicronutrientTotal;
import com.app.DTOs.NutrientTotals;
import com.app.controllers.NutritionReportController;
import com.app.services.NutritionReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NutritionReportController.class)
class NutritionReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NutritionReportService reportService;

    @Test
    void getDailyReport_ShouldReturnNutrientTotals() throws Exception {

        NutrientTotals mockResponse = new NutrientTotals();
        MacronutrientTotals macroTotals = new MacronutrientTotals();
        macroTotals.setTotalProteins(50.0);
        macroTotals.setTotalCarbohydrates(100.0);
        macroTotals.setTotalFats(30.0);
        macroTotals.setTotalCalories(850.0);
        mockResponse.setMacronutrients(macroTotals);
        mockResponse.setMicronutrients(Map.of("Vitamin C", new MicronutrientTotal(90, "mg")));

        LocalDate date = LocalDate.of(2024, 12, 1);
        when(reportService.generateDailyReport(date)).thenReturn(mockResponse);


        mockMvc.perform(get("/api/reports/daily")
                        .param("date", date.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.macronutrients.totalProteins").value(50.0))
                .andExpect(jsonPath("$.macronutrients.totalCarbohydrates").value(100.0))
                .andExpect(jsonPath("$.macronutrients.totalFats").value(30.0))
                .andExpect(jsonPath("$.macronutrients.totalCalories").value(850.0))
                .andExpect(jsonPath("$.micronutrients['Vitamin C'].amount").value(90.0))
                .andExpect(jsonPath("$.micronutrients['Vitamin C'].unit").value("mg"));


        verify(reportService, times(1)).generateDailyReport(date);
    }

    @Test
    void getPeriodReport_ShouldReturnNutrientTotals() throws Exception {

        NutrientTotals mockResponse = new NutrientTotals();
        MacronutrientTotals macroTotals = new MacronutrientTotals();
        macroTotals.setTotalProteins(150.0);
        macroTotals.setTotalCarbohydrates(300.0);
        macroTotals.setTotalFats(90.0);
        macroTotals.setTotalCalories(2550.0);
        mockResponse.setMacronutrients(macroTotals);
        mockResponse.setMicronutrients(Map.of("Iron", new MicronutrientTotal(18.0, "mg")));

        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        when(reportService.generatePeriodReport(startDate, endDate)).thenReturn(mockResponse);


        mockMvc.perform(get("/api/reports/period")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.macronutrients.totalProteins").value(150.0))
                .andExpect(jsonPath("$.macronutrients.totalCarbohydrates").value(300.0))
                .andExpect(jsonPath("$.macronutrients.totalFats").value(90.0))
                .andExpect(jsonPath("$.macronutrients.totalCalories").value(2550.0))
                .andExpect(jsonPath("$.micronutrients['Iron'].amount").value(18.0))
                .andExpect(jsonPath("$.micronutrients['Iron'].unit").value("mg"));

        // Verify the service was called
        verify(reportService, times(1)).generatePeriodReport(startDate, endDate);
    }
}
