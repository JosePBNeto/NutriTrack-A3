package com.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Micronutrient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double amount;
    private String unit;
}
