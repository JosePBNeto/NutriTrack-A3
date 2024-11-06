package com.app.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class FoodDTO {
    private String name;
    private String description;
    private MacronutrientsDTO macronutrients;
    private List<MicronutrientDTO> micronutrients;
}

@Data
class MacronutrientsDTO {
    private Double proteins;
    private Double carbohydrates;
    private Double fats;
}

@Data
class MicronutrientDTO {
    private String name;
    private Double amount;
    private String unit;
}