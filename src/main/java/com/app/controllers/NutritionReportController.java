package com.app.controllers;

import com.app.DTOs.NutrientTotals;
import com.app.services.NutritionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class NutritionReportController {
    @Autowired
    private NutritionReportService reportService;

    @GetMapping("/daily")
    public ResponseEntity<NutrientTotals> getDailyReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportService.generateDailyReport(date));
    }

    @GetMapping("/period")
    public ResponseEntity<NutrientTotals> getPeriodReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.generatePeriodReport(startDate, endDate));
    }
}