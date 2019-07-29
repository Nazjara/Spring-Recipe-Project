package com.nazjara.controller;

import com.nazjara.command.RecipeCommand;
import com.nazjara.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private RecipeCommand recipeCommand;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
       mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

       when(recipeService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);
       when(recipeCommand.getImage()).thenReturn(getClass().getClassLoader().getResourceAsStream("GrilledChickenTacos.jpg").readAllBytes());
       when(recipeCommand.getId()).thenReturn(2L);
       when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);
       when(recipeService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);
    }

    @Test
    public void testGetRecipe() throws Exception {
       mockMvc.perform(get("/recipe/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/show"))
               .andExpect(model().attributeExists("recipe"))
               .andExpect(model().attributeExists("image"));

       verify(recipeService).findRecipeCommandById(1L);
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2"));

        verify(recipeService).saveRecipeCommand(any(RecipeCommand.class));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).findRecipeCommandById(1L);
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService).deleteById(eq(1L));
    }
}