package com.app.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public
class MacronutrientsDTO {
    private Double proteins;
    private Double carbohydrates;
    private Double fats;
}
