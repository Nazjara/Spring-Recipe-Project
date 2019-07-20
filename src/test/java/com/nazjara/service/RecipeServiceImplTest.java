package com.nazjara.service;

import com.nazjara.model.Recipe;
import com.nazjara.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceImplTest {

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private Recipe recipe;

    @Before
    public void setUp() {
        when(recipeRepository.findAll()).thenReturn(Set.of(recipe));
    }

    @Test
    public void testGetRecipes() {
        Iterable<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.iterator().next(), recipe);

        verify(recipeRepository).findAll();
    }
}