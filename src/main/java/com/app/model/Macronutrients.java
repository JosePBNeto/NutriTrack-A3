package com.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "macronutrients")
public class Macronutrients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double proteins;
    private Double carbohydrates;
    private Double fats;
}
