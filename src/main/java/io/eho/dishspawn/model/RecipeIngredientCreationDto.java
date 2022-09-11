package io.eho.dishspawn.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//TODO: DTO class - needed for new recipe user input - read about bind list with Thymeleaf
@Getter @Setter
public class RecipeIngredientCreationDto {

    private List<RecipeIngredient> recipeIngredientList;

    public RecipeIngredientCreationDto() {
    }

    public RecipeIngredientCreationDto(List<RecipeIngredient> recipeIngredientList) {
        this.recipeIngredientList = recipeIngredientList;
    }

    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        this.recipeIngredientList.add(recipeIngredient);
    }


}
