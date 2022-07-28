package io.eho.dishspawn.play;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.util.IngredientCategory;
import io.eho.dishspawn.util.IngredientForm;
import io.eho.dishspawn.util.IngredientPrepType;

import java.util.HashSet;
import java.util.Set;

public class RecipePlay2 {

    public static void main(String[] args) {

        Ingredient coffeeInstant = new Ingredient(); // convention: for
        // recipeIngredients var naming, first name the type, then the (potential)
        // form it occurs in
        coffeeInstant.setName("instant coffee");
        coffeeInstant.setCategory(IngredientCategory.CONDIMENT);

        Ingredient milk = new Ingredient();
        milk.setName("milk");
        milk.setCategory(IngredientCategory.BEVERAGE);

        Ingredient water = new Ingredient();
        water.setName("water");
        water.setCategory(IngredientCategory.BEVERAGE);

        Recipe instantCoffee = new Recipe();

        // ingredient 1 for recipe
        RecipeIngredient i1 = new RecipeIngredient();
        i1.setIngredient(coffeeInstant);
        i1.setForm(IngredientForm.POWDER);
        i1.setPrepType(IngredientPrepType.STIR);
        i1.setColor("#000000");

        // ingredient 2 for recipe
        RecipeIngredient i2 = new RecipeIngredient();
        i2.setIngredient(milk);
        i2.setForm(IngredientForm.EXOTIC); // TODO: need to add LIQUID
        i2.setPrepType(IngredientPrepType.STIR);
        i2.setColor("#EEEEEE");

        // ingredient 3 for recipe
        RecipeIngredient i3 = new RecipeIngredient();
        i3.setIngredient(water);
        i3.setForm(IngredientForm.EXOTIC);
        i3.setPrepType(IngredientPrepType.BOIL);
        // no color

        // construct recipe
        instantCoffee.setName("Instant coffee");
        instantCoffee.setInstructions("boil the water and stir in the instant" +
                                              " coffee and milk");
        Set<RecipeIngredient> recipeIngredients = new HashSet<>();
        recipeIngredients.add(i1);
        recipeIngredients.add(i2);
        recipeIngredients.add(i3);
        instantCoffee.setRecipeIngredients(recipeIngredients);

        System.out.println(instantCoffee);

        // find recipes by ingredient name
        String ingredientName = "Instant coffee";
//        List<Recipe> recipes =
//                new ArrayList<>(findAllByRecipeIngredientContaining);


    }
}
