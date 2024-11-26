package com.app;

import com.app.DTOs.FoodDTO;
import com.app.DTOs.MacronutrientsDTO;
import com.app.DTOs.MicronutrientDTO;
import com.app.exceptions.ResourceNotFoundException;
import com.app.model.Food;
import com.app.model.Macronutrients;
import com.app.model.Micronutrient;
import com.app.repositories.FoodRepository;
import com.app.services.FoodService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
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
        MicronutrientDTO vitaminaC = new MicronutrientDTO();
        vitaminaC.setName("Vitamina C");
        vitaminaC.setAmount(85.0);
        vitaminaC.setUnit("mg");

        micronutrientsDTO = Arrays.asList(vitaminaC);

        // Configurar FoodDTO
        foodDTO = new FoodDTO();
        foodDTO.setName("Peito de Frango");
        foodDTO.setDescription("Peito de frango grelhado");
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
        micronutrient.setName("Vitamina C");
        micronutrient.setAmount(85.0);
        micronutrient.setUnit("mg");

        micronutrients = Arrays.asList(micronutrient);

        // Configurar Food
        food = new Food();
        food.setId(1L);
        food.setName("Peito de Frango");
        food.setDescription("Peito de frango grelhado");
        food.setMacronutrients(macronutrients);
        food.setMicronutrients(micronutrients);
    }

    @Test
    void createFood_Success() {
        // Arrange
        when(foodRepository.save(any(Food.class))).thenReturn(food);

        // Act
        Food createdFood = foodService.createFood(foodDTO);

        // Assert
        assertNotNull(createdFood);
        assertEquals("Peito de Frango", createdFood.getName());
        assertEquals(20.0, createdFood.getMacronutrients().getProteins());
        assertEquals(1, createdFood.getMicronutrients().size());

        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    void createFood_WithNullName_ThrowsValidationException() {
        // Arrange
        foodDTO.setName(null);

        // Act & Assert
        assertThrows(ValidationException.class, () -> {
            foodService.createFood(foodDTO);
        });

        verify(foodRepository, never()).save(any(Food.class));
    }

    @Test
    void searchFoods_Success() {
        // Arrange
        String termoBusca = "Frango";
        List<Food> alimentosEsperados = Arrays.asList(food);
        when(foodRepository.findByNameContainingIgnoreCase(termoBusca))
                .thenReturn(alimentosEsperados);

        // Act
        List<Food> alimentosEncontrados = foodService.searchFoods(termoBusca);

        // Assert
        assertNotNull(alimentosEncontrados);
        assertEquals(1, alimentosEncontrados.size());
        assertEquals("Peito de Frango", alimentosEncontrados.get(0).getName());

        verify(foodRepository, times(1))
                .findByNameContainingIgnoreCase(termoBusca);
    }

    @Test
    void updateFood_Success() {
        // Arrange
        Long idAlimento = 1L;
        when(foodRepository.findById(idAlimento)).thenReturn(Optional.of(food));
        when(foodRepository.save(any(Food.class))).thenReturn(food);

        foodDTO.setName("Peito de Frango Atualizado");

        // Act
        Food alimentoAtualizado = foodService.updateFood(idAlimento, foodDTO);

        // Assert
        assertNotNull(alimentoAtualizado);
        assertEquals("Peito de Frango Atualizado", alimentoAtualizado.getName());

        verify(foodRepository, times(1)).findById(idAlimento);
        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    void updateFood_NotFound_ThrowsResourceNotFoundException() {
        // Arrange
        Long foodId = 999L;
        when(foodRepository.findById(foodId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            foodService.updateFood(foodId, foodDTO);
        });

        verify(foodRepository, times(1)).findById(foodId);
        verify(foodRepository, never()).save(any(Food.class));
    }

    @Test
    void deleteFood_Success() {
        // Arrange
        Long idAlimento = 1L;
        when(foodRepository.existsById(idAlimento)).thenReturn(true);
        doNothing().when(foodRepository).deleteById(idAlimento);

        // Act
        foodService.deleteFood(idAlimento);

        // Assert
        verify(foodRepository, times(1)).existsById(idAlimento);
        verify(foodRepository, times(1)).deleteById(idAlimento);
    }

    @Test
    void deleteFood_NotFound_ThrowsResourceNotFoundException() {
        // Arrange
        Long foodId = 999L;
        when(foodRepository.existsById(foodId)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            foodService.deleteFood(foodId);
        });

        verify(foodRepository, times(1)).existsById(foodId);
        verify(foodRepository, never()).deleteById(foodId);
    }
}


