package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Macronutrients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double proteins;
    private Double carbohydrates;
    private Double fats;

    public Macronutrients(double v, double v1, double v2) {
    }
}
