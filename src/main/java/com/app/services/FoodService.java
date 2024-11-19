package com.app.services;


import com.app.DTOs.FoodDTO;
import com.app.DTOs.MacronutrientsDTO;
import com.app.DTOs.MicronutrientDTO;
import com.app.exceptions.ResourceNotFoundException;
import com.app.model.Food;
import com.app.model.Macronutrients;
import com.app.model.Micronutrient;
import com.app.repositories.FoodRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public Food createFood(FoodDTO foodDTO) {
        validateFoodDTO(foodDTO);
        Food food = convertToEntity(foodDTO);
        return foodRepository.save(food);
    }

    public List<Food> searchFoods(String name) {
        return foodRepository.findByNameContainingIgnoreCase(name);
    }

    public Food updateFood(Long id, FoodDTO foodDTO) {
        validateFoodDTO(foodDTO);

        Food existingFood = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alimento", id));

        updateFoodFromDTO(existingFood, foodDTO);
        return foodRepository.save(existingFood);
    }

    public void deleteFood(Long id) {
        if (!foodRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alimento", id);
        }
        foodRepository.deleteById(id);
    }

    private void validateFoodDTO(FoodDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new ValidationException("O nome do alimento não pode ser nulo ou vazio");
        }

        if (dto.getMacronutrients() == null) {
            throw new ValidationException("Os macronutrientes não podem ser nulos");
        }

        MacronutrientsDTO macro = dto.getMacronutrients();
        if (macro.getProteins() == null || macro.getCarbohydrates() == null || macro.getFats() == null) {
            throw new ValidationException("Todos os valores dos macronutrientes devem ser especificados");
        }
    }

    private Food convertToEntity(FoodDTO dto) {
        Food food = new Food();
        food.setName(dto.getName());
        food.setDescription(dto.getDescription());

        Macronutrients macros = new Macronutrients();
        macros.setProteins(dto.getMacronutrients().getProteins());
        macros.setCarbohydrates(dto.getMacronutrients().getCarbohydrates());
        macros.setFats(dto.getMacronutrients().getFats());
        food.setMacronutrients(macros);

        if (dto.getMicronutrients() != null) {
            List<Micronutrient> micronutrients = dto.getMicronutrients().stream()
                    .map(this::convertMicronutrient)
                    .collect(Collectors.toList());
            food.setMicronutrients(micronutrients);
        }

        return food;
    }

    private Micronutrient convertMicronutrient(MicronutrientDTO dto) {
        Micronutrient micro = new Micronutrient();
        micro.setName(dto.getName());
        micro.setAmount(dto.getAmount());
        micro.setUnit(dto.getUnit());
        return micro;
    }

    private void updateFoodFromDTO(Food food, FoodDTO dto) {
        food.setName(dto.getName());
        food.setDescription(dto.getDescription());

        Macronutrients macros = food.getMacronutrients();
        macros.setProteins(dto.getMacronutrients().getProteins());
        macros.setCarbohydrates(dto.getMacronutrients().getCarbohydrates());
        macros.setFats(dto.getMacronutrients().getFats());

        if (dto.getMicronutrients() != null) {
            List<Micronutrient> micronutrients = dto.getMicronutrients().stream()
                    .map(this::convertMicronutrient)
                    .collect(Collectors.toList());
            food.setMicronutrients(micronutrients);
        }
    }
}
