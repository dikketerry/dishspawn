package io.eho.dishspawn.play;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.util.IngredientCategory;
import io.eho.dishspawn.util.RecipeIngredientForm;
import io.eho.dishspawn.util.RecipeIngredientCookingMethod;

public class CreateIngredientsAndRecipeDemo {

    public static void main(String[] args) {

        // create a session factory
        // create a session
        // start hibernate transaction

        // create ingredients
        Ingredient coffeeInstant = new Ingredient("instant coffee",
                                                  IngredientCategory.CONDIMENT);

        Ingredient milk = new Ingredient("milk", IngredientCategory.DAIRY_MILKS);

        Ingredient water = new Ingredient("water", IngredientCategory.LIQUIDS_WATER);

        // create recipe
        Recipe instantCoffee = new Recipe("Instant coffee", "Boil the water " +
                "and stir in the instant coffee and milk.");

        // add recipe ingredients to the recipe
        instantCoffee.addRecipeIngredient(coffeeInstant,
                                          RecipeIngredientForm.POWDER,
                                          RecipeIngredientCookingMethod.STIR, "#000000");
        instantCoffee.addRecipeIngredient(milk, RecipeIngredientForm.FLUID,
                                          RecipeIngredientCookingMethod.STIR, "#EEEEEE");
        instantCoffee.addRecipeIngredient(water, RecipeIngredientForm.FLUID,
                                          RecipeIngredientCookingMethod.BOIL, "A1A1A1");

        // TODO: testing mass / volume
        Ingredient basilicum = new Ingredient("basilicum",
                                              IngredientCategory.HERBS);
        RecipeIngredient ri1 = new RecipeIngredient();
        ri1.setIngredient(basilicum);
//        ri1.setVolume();

        // diagnostic println
        System.out.println(instantCoffee);

        // save ingredients and recipe

        // commit the transaction

        // close session and session factory

    }


}
