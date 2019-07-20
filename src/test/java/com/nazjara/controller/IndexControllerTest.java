package com.nazjara.controller;

import com.nazjara.model.Recipe;
import com.nazjara.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {

    @InjectMocks
    public IndexController indexController;

    @Mock
    public RecipeService recipeService;

    @Mock
    public Model model;

    @Mock
    public Set<Recipe> set;

    @Before
    public void setUp() {
        when(recipeService.getRecipes()).thenReturn(set);
        when(model.addAttribute(anyString(), any())).thenReturn(model);
    }

    @Test
    public void testGetIndexPage() {
        String view = indexController.getIndexPage(model);

        ArgumentCaptor<Set> captor = ArgumentCaptor.forClass(Set.class);

        verify(recipeService).getRecipes();
        verify(model).addAttribute(eq("recipes"), captor.capture());

        assertEquals("index", view);
        assertSame(captor.getValue(), set);
    }
}