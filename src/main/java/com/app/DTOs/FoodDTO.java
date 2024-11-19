package com.app.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class FoodDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private String description;

    @Valid
    private MacronutrientsDTO macronutrients;

    @Valid
    private List<@Valid MicronutrientDTO> micronutrients;
}

