package com.app.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private String description;

    @Valid
    private MacronutrientsDTO macronutrients;

    @Valid
    private List<@Valid MicronutrientDTO> micronutrients;
}

