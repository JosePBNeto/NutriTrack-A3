package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Micronutrient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double amount;
    private String unit;

    public Micronutrient(String name, Double amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

}
