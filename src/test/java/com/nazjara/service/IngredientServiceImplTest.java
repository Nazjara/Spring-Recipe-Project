package com.nazjara.service;

import com.nazjara.command.IngredientCommand;
import com.nazjara.converter.IngredientCommandToIngredient;
import com.nazjara.converter.IngredientToIngredientCommand;
import com.nazjara.model.Ingredient;
import com.nazjara.repository.IngredientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceImplTest {

    @Mock
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientCommand ingredientCommand;

    @Mock
    private Ingredient ingredient;

    private IngredientService ingredientService;

    @Before
    public void setUp() {
        ingredientService = new IngredientServiceImpl(ingredientRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);

        when(ingredientCommandToIngredient.convert(ingredientCommand)).thenReturn(ingredient);
        when(ingredientToIngredientCommand.convert(ingredient)).thenReturn(ingredientCommand);
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
    }

    @Test
    public void testFindIngredientCommandById() throws Exception {
        assertSame(ingredientCommand, ingredientService.findIngredientCommandById(1L));

        verify(ingredientToIngredientCommand).convert(ingredient);
        verify(ingredientRepository).findById(1L);
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
       assertSame(ingredientCommand, ingredientService.saveIngredientCommand(ingredientCommand));

        verify(ingredientToIngredientCommand).convert(ingredient);
        verify(ingredientRepository).save(ingredient);
        verify(ingredientCommandToIngredient).convert(ingredientCommand);
    }

    @Test
    public void testDeleteById() throws Exception {
        ingredientService.deleteById(1L);

        verify(ingredientRepository).deleteById(1L);
    }
}