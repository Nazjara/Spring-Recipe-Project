package com.nazjara.controller;

import com.nazjara.command.IngredientCommand;
import com.nazjara.command.UnitOfMeasureCommand;
import com.nazjara.service.IngredientService;
import com.nazjara.service.RecipeService;
import com.nazjara.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String getAllForRecipeById(@PathVariable String id, Model model) {
        model.addAttribute("ingredients", recipeService.findRecipeCommandById(Long.valueOf(id)).getIngredients());
        model.addAttribute("recipeId", id);

        return "ingredient/list";
    }

    @GetMapping("/ingredient/{id}")
    public String getById(@PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findIngredientCommandById(Long.valueOf(id)));

        return "ingredient/show";
    }

    @GetMapping("/ingredient/{id}/update")
    public String update(@PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findIngredientCommandById(Long.valueOf(id)));
        model.addAttribute("uomList", unitOfMeasureService.findAll());

        return "ingredient/ingredientform";
    }

    @GetMapping("recipe/{id}/ingredient/new")
    public String create(@PathVariable String id, Model model) {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(id));
        ingredientCommand.setUnitOfMeasureCommand(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.findAll());

        return "ingredient/ingredientform";
    }

    @PostMapping("ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        return "redirect:/ingredient/" + ingredientService.saveIngredientCommand(ingredientCommand).getId();
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteById(@PathVariable String id, @PathVariable String recipeId) {
        ingredientService.deleteById(Long.valueOf(id));
        return String.format("redirect:/recipe/%s/ingredients", recipeId);
    }
}