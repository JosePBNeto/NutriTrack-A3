package com.app.services;

import com.app.model.DailyIntake;
import com.app.repositories.DailyIntakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class NutritionReportService {
    @Autowired
    private DailyIntakeRepository dailyIntakeRepository;

    public Map<String, Double> generateDailyReport(LocalDate date) {
        List<DailyIntake> intakes = dailyIntakeRepository.findByDateBetween(date, date);
        // Implementar cálculo de nutrientes totais
        return null; // Retornar mapa com totais de nutrientes
    }
}
