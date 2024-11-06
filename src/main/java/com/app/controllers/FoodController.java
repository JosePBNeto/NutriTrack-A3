package com.app.controllers;

import com.app.DTOs.FoodDTO;
import com.app.model.Food;
import com.app.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody FoodDTO foodDTO) {
        return ResponseEntity.ok(foodService.createFood(foodDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFoods(@RequestParam String name) {
        return ResponseEntity.ok(foodService.searchFoods(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody FoodDTO foodDTO) {
        return ResponseEntity.ok(foodService.updateFood(id, foodDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.ok().build();
    }
}
