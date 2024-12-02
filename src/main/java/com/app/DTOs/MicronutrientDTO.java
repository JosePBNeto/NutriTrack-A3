package com.app.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public
class MicronutrientDTO {
    private String name;
    private Double amount;
    private String unit;
}
