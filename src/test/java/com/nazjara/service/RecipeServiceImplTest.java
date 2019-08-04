package com.nazjara.service;

import com.nazjara.command.RecipeCommand;
import com.nazjara.converter.RecipeCommandToRecipe;
import com.nazjara.converter.RecipeToRecipeCommand;
import com.nazjara.exception.NotFoundException;
import com.nazjara.model.Recipe;
import com.nazjara.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceImplTest {

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    private Recipe recipe;

    @Mock
    private RecipeCommand recipeCommand;

    @Before
    public void setUp() {
        when(recipeRepository.findAll()).thenReturn(Set.of(recipe));
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        doNothing().when(recipeRepository).deleteById(anyLong());
        when(recipeCommandToRecipe.convert(recipeCommand)).thenReturn(recipe);
        when(recipeToRecipeCommand.convert(recipe)).thenReturn(recipeCommand);
    }

    @Test
    public void testGetRecipes() {
        Iterable<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.iterator().next(), recipe);

        verify(recipeRepository).findAll();
    }

    @Test
    public void testGetRecipeById() {
        Recipe newRecipe = recipeService.findById(1L);

        assertSame(newRecipe, recipe);

        verify(recipeRepository).findById(1L);
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdNotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        recipeService.findById(1L);
    }

    @Test
    public void testSaveRecipeCommand() {
        assertSame(recipeCommand, recipeService.saveRecipeCommand(recipeCommand));

        verify(recipeCommandToRecipe).convert(recipeCommand);
        verify(recipeRepository).save(recipe);
        verify(recipeToRecipeCommand).convert(recipe);
    }

    @Test
    public void getRecipeCommandByIdTest() {
        RecipeCommand commandById = recipeService.findRecipeCommandById(1L);

        assertNotNull("Null recipe returned", commandById);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }


    @Test
    public void testDeleteById() throws Exception {
        recipeService.deleteById(2L);

        verify(recipeRepository).deleteById(anyLong());
    }
}