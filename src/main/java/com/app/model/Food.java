package com.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Macronutrients macronutrients;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Micronutrient> micronutrients;

}
