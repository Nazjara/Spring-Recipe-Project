package com.nazjara.service;

import com.nazjara.model.Recipe;

public interface RecipeService {
    Iterable<Recipe> getRecipes();
    Recipe findById(Long id);
}
