package com.app.controllers;

import com.app.services.NutritionReportService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Map<String, Double>> getDailyReport(@RequestParam LocalDate date) {
        return ResponseEntity.ok(reportService.generateDailyReport(date));
    }
}
