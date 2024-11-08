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

