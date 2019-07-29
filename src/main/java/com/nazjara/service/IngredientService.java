package com.nazjara.service;

import com.nazjara.command.IngredientCommand;

public interface IngredientService {
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    IngredientCommand findIngredientCommandById(Long id);
    void deleteById(Long id);
}
