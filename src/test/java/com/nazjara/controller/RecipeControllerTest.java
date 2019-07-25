package com.nazjara.controller;

import com.nazjara.model.Recipe;
import com.nazjara.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private Recipe recipe;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
       mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

       when(recipeService.findById(anyLong())).thenReturn(recipe);
       when(recipe.getImage()).thenReturn(new byte[]{});
    }

    @Test
    public void testGetRecipe() throws Exception {
       mockMvc.perform(get("/recipe/show/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/show"))
               .andExpect(model().attributeExists("recipe"))
               .andExpect(model().attributeExists("image"));
    }
}