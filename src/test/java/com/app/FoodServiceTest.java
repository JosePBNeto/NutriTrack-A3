package com.app;

import com.app.DTOs.FoodDTO;
import com.app.DTOs.MacronutrientsDTO;
import com.app.DTOs.MicronutrientDTO;
import com.app.model.Food;
import com.app.model.Macronutrients;
import com.app.model.Micronutrient;
import com.app.repositories.FoodRepository;
import com.app.services.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoodServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private FoodService foodService;

    private FoodDTO foodDTO;
    private Food food;
    private MacronutrientsDTO macronutrientsDTO;
    private Macronutrients macronutrients;
    private List<MicronutrientDTO> micronutrientsDTO;
    private List<Micronutrient> micronutrients;

    @BeforeEach
    void setUp() {
        // Configurar MacronutrientsDTO
        macronutrientsDTO = new MacronutrientsDTO();
        macronutrientsDTO.setProteins(20.0);
        macronutrientsDTO.setCarbohydrates(30.0);
        macronutrientsDTO.setFats(10.0);

        // Configurar MicronutrientDTO
        MicronutrientDTO vitaminC = new MicronutrientDTO();
        vitaminC.setName("Vitamin C");
        vitaminC.setAmount(85.0);
        vitaminC.setUnit("mg");

        micronutrientsDTO = Arrays.asList(vitaminC);

        // Configurar FoodDTO
        foodDTO = new FoodDTO();
        foodDTO.setName("Chicken Breast");
        foodDTO.setDescription("Grilled chicken breast");
        foodDTO.setMacronutrients(macronutrientsDTO);
        foodDTO.setMicronutrients(micronutrientsDTO);

        // Configurar Macronutrients
        macronutrients = new Macronutrients();
        macronutrients.setId(1L);
        macronutrients.setProteins(20.0);
        macronutrients.setCarbohydrates(30.0);
        macronutrients.setFats(10.0);

        // Configurar Micronutrient
        Micronutrient micronutrient = new Micronutrient();
        micronutrient.setId(1L);
        micronutrient.setName("Vitamin C");
        micronutrient.setAmount(85.0);
        micronutrient.setUnit("mg");

        micronutrients = Arrays.asList(micronutrient);


        food = new Food();
        food.setId(1L);
        food.setName("Chicken Breast");
        food.setDescription("Grilled chicken breast");
        food.setMacronutrients(macronutrients);
        food.setMicronutrients(micronutrients);
    }

    @Test
    void createFood_Success() {

        when(foodRepository.save(any(Food.class))).thenReturn(food);

        Food createdFood = foodService.createFood(foodDTO);

        assertNotNull(createdFood);
        assertEquals("Chicken Breast", createdFood.getName());
        assertEquals(20.0, createdFood.getMacronutrients().getProteins());
        assertEquals(1, createdFood.getMicronutrients().size());

        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    void searchFoods_Success() {

        String searchTerm = "Chicken";
        List<Food> expectedFoods = Arrays.asList(food);
        when(foodRepository.findByNameContainingIgnoreCase(searchTerm))
                .thenReturn(expectedFoods);


        List<Food> foundFoods = foodService.searchFoods(searchTerm);


        assertNotNull(foundFoods);
        assertEquals(1, foundFoods.size());
        assertEquals("Chicken Breast", foundFoods.get(0).getName());

        verify(foodRepository, times(1))
                .findByNameContainingIgnoreCase(searchTerm);
    }

    @Test
    void updateFood_Success() {
        // Arrange
        Long foodId = 1L;
        when(foodRepository.findById(foodId)).thenReturn(Optional.of(food));
        when(foodRepository.save(any(Food.class))).thenReturn(food);

        foodDTO.setName("Updated Chicken Breast");


        Food updatedFood = foodService.updateFood(foodId, foodDTO);


        assertNotNull(updatedFood);
        assertEquals("Updated Chicken Breast", updatedFood.getName());

        verify(foodRepository, times(1)).findById(foodId);
        verify(foodRepository, times(1)).save(any(Food.class));
    }


}
