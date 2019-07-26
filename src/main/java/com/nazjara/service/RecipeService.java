package com.nazjara.service;

import com.nazjara.command.RecipeCommand;
import com.nazjara.model.Recipe;

public interface RecipeService {
    Iterable<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
}
