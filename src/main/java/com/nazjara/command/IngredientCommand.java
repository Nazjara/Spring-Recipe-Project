package com.nazjara.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private String amount;
    private UnitOfMeasureCommand unitOfMeasure;
}