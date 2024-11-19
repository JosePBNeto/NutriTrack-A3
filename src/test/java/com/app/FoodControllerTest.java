package com.app;

import com.app.DTOs.FoodDTO;
import com.app.DTOs.MacronutrientsDTO;
import com.app.DTOs.MicronutrientDTO;
import com.app.controllers.FoodController;
import com.app.model.Food;
import com.app.services.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class FoodControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private FoodService foodService;

    @InjectMocks
    private FoodController foodController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();
        objectMapper = new ObjectMapper();
    }

    private FoodDTO createSampleFoodDTO() {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setName("Peito de Frango");
        foodDTO.setDescription("Peito de frango grelhado");

        MacronutrientsDTO macronutrients = new MacronutrientsDTO();
        macronutrients.setProteins(31.0);
        macronutrients.setCarbohydrates(0.0);
        macronutrients.setFats(3.6);
        foodDTO.setMacronutrients(macronutrients);

        List<MicronutrientDTO> micronutrients = Arrays.asList(
                createMicronutrient("Vitamina B6", 0.5),
                createMicronutrient("Vitamina B12", 0.3)
        );
        foodDTO.setMicronutrients(micronutrients);

        return foodDTO;
    }

    private MicronutrientDTO createMicronutrient(String name, double amount) {
        MicronutrientDTO micronutrient = new MicronutrientDTO();
        micronutrient.setName(name);
        micronutrient.setAmount(amount);
        return micronutrient;
    }

    @Test
    public void testCreateFood_Success() throws Exception {
        // Arrange
        FoodDTO foodDTO = createSampleFoodDTO();

        Food createdFood = new Food();
        createdFood.setId(1L);
        createdFood.setName(foodDTO.getName());
        createdFood.setDescription(foodDTO.getDescription());

        when(foodService.createFood(any(FoodDTO.class))).thenReturn(createdFood);

        // Act & Assert
        mockMvc.perform(post("/api/foods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foodDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Peito de Frango"))
                .andExpect(jsonPath("$.description").value("Peito de frango grelhado"));
    }

    @Test
    public void testSearchFoods_Success() throws Exception {
        // Arrange
        List<Food> foods = Arrays.asList(
                createFood(1L, "Peito de Frango", "Peito de frango grelhado"),
                createFood(2L, "Salada de Frango", "Salada de frango fresca")
        );

        when(foodService.searchFoods(anyString())).thenReturn(foods);

        // Act & Assert
        mockMvc.perform(get("/api/foods/search")
                        .param("name", "Frango"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Peito de Frango"))
                .andExpect(jsonPath("$[1].name").value("Salada de Frango"));
    }

    @Test
    public void testUpdateFood_Success() throws Exception {
        // Arrange
        Long foodId = 1L;
        FoodDTO foodDTO = createSampleFoodDTO();
        foodDTO.setName("Peito de Frango Atualizado");
        foodDTO.setDescription("Peito de frango grelhado perfeitamente");

        Food updatedFood = new Food();
        updatedFood.setId(foodId);
        updatedFood.setName(foodDTO.getName());
        updatedFood.setDescription(foodDTO.getDescription());

        when(foodService.updateFood(anyLong(), any(FoodDTO.class))).thenReturn(updatedFood);

        // Act & Assert
        mockMvc.perform(put("/api/foods/{id}", foodId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foodDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(foodId))
                .andExpect(jsonPath("$.name").value("Peito de Frango Atualizado"))
                .andExpect(jsonPath("$.description").value("Peito de frango grelhado perfeitamente"));
    }

    @Test
    public void testDeleteFood_Success() throws Exception {
        // Arrange
        Long foodId = 1L;
        doNothing().when(foodService).deleteFood(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/api/foods/{id}", foodId))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateFood_InvalidInput() throws Exception {
        // Arrange
        FoodDTO invalidFoodDTO = new FoodDTO();
        // Deixe o nome em branco para simular entrada inválida

        // Act & Assert
        mockMvc.perform(post("/api/foods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFoodDTO)))
                .andExpect(status().isBadRequest());
    }

    // Método auxiliar para criar Food para testes
    private Food createFood(Long id, String name, String description) {
        Food food = new Food();
        food.setId(id);
        food.setName(name);
        food.setDescription(description);
        return food;
    }
}
