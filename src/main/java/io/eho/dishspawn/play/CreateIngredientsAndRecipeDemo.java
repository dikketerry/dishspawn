package io.eho.dishspawn.play;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.util.IngredientCategory;
import io.eho.dishspawn.util.IngredientForm;
import io.eho.dishspawn.util.IngredientPrepType;

public class CreateIngredientsAndRecipeDemo {

    public static void main(String[] args) {

        // create a session factory
        // create a session
        // start hibernate transaction

        // create ingredients
        Ingredient coffeeInstant = new Ingredient("instant coffee",
                                                  IngredientCategory.CONDIMENT);

        Ingredient milk = new Ingredient("milk", IngredientCategory.BEVERAGE);

        Ingredient water = new Ingredient("water", IngredientCategory.BEVERAGE);

        // create recipe
        Recipe instantCoffee = new Recipe("Instant coffee", "Boil the water " +
                "and stir in the instant coffee and milk.");

        // add recipe ingredients to the recipe
        instantCoffee.addRecipeIngredient(coffeeInstant,
                                          IngredientForm.POWDER,
                                          IngredientPrepType.STIR, "#000000");
        instantCoffee.addRecipeIngredient(milk, IngredientForm.EXOTIC,
                                          IngredientPrepType.STIR, "#EEEEEE");
        instantCoffee.addRecipeIngredient(water, IngredientForm.EXOTIC,
                                          IngredientPrepType.BOIL, "A1A1A1");

        // diagnostic println
        System.out.println(instantCoffee);

        // save ingredients and recipe

        // commit the transaction

        // close session and session factory

    }


}