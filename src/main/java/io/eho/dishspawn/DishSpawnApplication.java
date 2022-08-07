package io.eho.dishspawn;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.repository.IngredientRepository;
import io.eho.dishspawn.repository.RecipeIngredientRepository;
import io.eho.dishspawn.repository.RecipeRepository;
import io.eho.dishspawn.util.IngredientCategory;
import io.eho.dishspawn.util.RecipeIngredientForm;
import io.eho.dishspawn.util.RecipeIngredientCookingMethod;
import io.eho.dishspawn.util.RecipeIngredientTexture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DishSpawnApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DishSpawnApplication.class, args);
	}

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;

	@Override
	public void run(String... args) throws Exception {
		Ingredient egg = new Ingredient("egg", IngredientCategory.EGGS);
		Ingredient mayonaise = new Ingredient("mayonaise",
											  IngredientCategory.CONDIMENT);
		Ingredient chives = new Ingredient("chives", IngredientCategory.HERBS);
		Ingredient curry = new Ingredient("curry", IngredientCategory.SPICES);
		Ingredient mustard = new Ingredient("mustard", IngredientCategory.ADDITIVE);
		Ingredient persil = new Ingredient("persil", IngredientCategory.HERBS);

		ingredientRepository.save(egg);
		ingredientRepository.save(mayonaise);
		ingredientRepository.save(chives);
		ingredientRepository.save(curry);
		ingredientRepository.save(mustard);
		ingredientRepository.save(persil);

		Recipe eggSalad = new Recipe("Egg salad", "Boil the eggs, mash them " +
				"in " +
				"a bowl, add mustard, mayonaise, chives and curry, season to " +
				"taste, garnish with persil, eat");

		RecipeIngredient r1 = new RecipeIngredient();
		r1.setIngredient(egg);
		r1.setRecipe(eggSalad);
		r1.setQuantityUnit(1);
		r1.setUnitName("POUND");
		r1.setMass();
		r1.setVisualImpact(true);

		r1.setForm(RecipeIngredientForm.EGG_LIKE);
		r1.setRecipeIngredientTexture(RecipeIngredientTexture.CHEWY);
		r1.setPrepType(RecipeIngredientCookingMethod.BOIL);
		r1.setPrepType(RecipeIngredientCookingMethod.BOIL);
		r1.setColor("EF9A35");

		// NO SAVE OF RI INDIVIDUALLY! AS SAVE OF RI IS IMPLIED BY CASCADE IN
		// RECIPE
//		recipeIngredientRepository.save(r1);

		eggSalad.addRecipeIngredient(r1);
		recipeRepository.save(eggSalad);
	}
}
