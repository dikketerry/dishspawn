package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// because JpaRepository is extended, there is no need to annotate this repo
// with @Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    public List<Recipe> findAllByOrderByTimestampCreatedDesc();
    List<Recipe> findAllByNameContainingOrderByNameAsc(String searchKey);


    // select the sets of recipeingredients where the set contains ingredient a, ingredient b and ingredient c
    // call the recipe property per set found

    /*
    List<User> findByEmailAddressAndLastname(String emailAddress, String lastname);
    translates into
    select u from User u where u.emailAddress = ?1 and u.lastname = ?2
     */

//    List<Recipe> findAllRecipesWithIngredientName(String ingredientName);

    // findAllRecipesByIngredientName (String ingredientName)
    // 1. collect the ingredient from Ingredient table
    // 2. check ingredient in RecipeIngredient table
    // 3. if match, return recipe from RecipeIngredient table

    /*
    findAllRecipesWithIngredientList
    1 i have up to 3 ingredients: List<Ingredient>. let's say i1, i5 & i27
    2 now i need to find the recipes which contain ALL ingredients
    3 a recipe is linked to an ingredient via RecipeIngredient
    4 so, i need to find the recipes which have a set of recipe ingredients which contain all ingredients

    RETURN LIST RECIPE-INGREDIENTS WHICH CONTAIN i1 AND i5 AND i27
    FROM LIST, GET RECIPE NAMES
    LOOP THROUGH RECIPE NAMES, SHOW
     */

}

