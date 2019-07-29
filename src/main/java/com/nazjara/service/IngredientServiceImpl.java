package com.nazjara.service;

import com.nazjara.command.IngredientCommand;
import com.nazjara.converter.IngredientCommandToIngredient;
import com.nazjara.converter.IngredientToIngredientCommand;
import com.nazjara.model.Ingredient;
import com.nazjara.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);

        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ingredientToIngredientCommand.convert(savedIngredient);
    }

    @Override
    @Transactional
    public IngredientCommand findIngredientCommandById(Long id) {
        return ingredientToIngredientCommand.convert(ingredientRepository.findById(id).orElse(null));
    }

    @Override
    public void deleteById(Long id) {
        ingredientRepository.deleteById(id);
    }
}
