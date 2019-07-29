package com.nazjara.controller;

import com.nazjara.command.IngredientCommand;
import com.nazjara.command.RecipeCommand;
import com.nazjara.service.IngredientService;
import com.nazjara.service.RecipeService;
import com.nazjara.service.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @Mock
    private RecipeService recipeService;

    @Mock
    private RecipeCommand recipeCommand;

    @Mock
    private IngredientCommand ingredientCommand;

    private IngredientController controller;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(recipeService.findRecipeCommandById(1L)).thenReturn(recipeCommand);
        when(recipeCommand.getIngredients()).thenReturn(new HashSet<>());
        when(ingredientService.findIngredientCommandById(2L)).thenReturn(ingredientCommand);
        when(unitOfMeasureService.findAll()).thenReturn(new HashSet<>());
        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);
        when(ingredientCommand.getId()).thenReturn(2L);
    }

    @Test
    public void testGetAllForRecipeById() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredient/list"))
                .andExpect(model().attributeExists("recipeId"))
                .andExpect(model().attributeExists("ingredients"));

        verify(recipeService).findRecipeCommandById(1L);
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(get("/ingredient/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService).findIngredientCommandById(2L);
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(unitOfMeasureService).findAll();
    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(get("/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(ingredientService).findIngredientCommandById(2L);
        verify(unitOfMeasureService).findAll();
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        mockMvc.perform(post("/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ingredient/2"));

        verify(ingredientService).saveIngredientCommand(any(IngredientCommand.class));
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        mockMvc.perform(get("/recipe/2/ingredient/3/delete")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

        verify(ingredientService).deleteById(3L);
    }
}
