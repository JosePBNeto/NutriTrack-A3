package com.app.services;


import com.app.DTOs.FoodDTO;
import com.app.model.Food;
import com.app.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public Food createFood(FoodDTO foodDTO) {
        Food food = convertToEntity(foodDTO);
        return foodRepository.save(food);
    }

    public List<Food> searchFoods(String name) {
        return foodRepository.findByNameContainingIgnoreCase(name);
    }

    public Food updateFood(Long id, FoodDTO foodDTO) {
        Food existingFood = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        updateFoodFromDTO(existingFood, foodDTO);
        return foodRepository.save(existingFood);
    }

    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }

    private Food convertToEntity(FoodDTO dto) {
        Food food = new Food();
        food.setName(dto.getName());
        food.setDescription(dto.getDescription());
        // Implementar conversão de macro e micronutrientes
        return food;
    }

    private void updateFoodFromDTO(Food food, FoodDTO dto) {
        food.setName(dto.getName());
        food.setDescription(dto.getDescription());
        // Implementar atualização de macro e micronutrientes
    }
}
