package io.eho.dishspawn;

import io.eho.dishspawn.model.Chef;
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

		Chef chef = new Chef();
		chef.setFirstName("erik");
		chef.setLastName("hollanders");
		chef.setUserName("masterchef");
		chef.setPassword("test123");
		chef.setEmail("erik.hollanders@gmail.com");

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
		eggSalad.addChef(chef);

		RecipeIngredient r1 = new RecipeIngredient();
		r1.setIngredient(egg);
		r1.setRecipe(eggSalad); // this should also probably not be set by
		// client, as a RecipeIngredient already should belong to a recipe
		// (new or existing)
		r1.setQuantity(1);
		r1.setUnitName("POUND");
		r1.massOrVolumeSetter(); // this should not be set by client - it should be
		// calculated prior to saving the recipe ingredient (same for volume)
		r1.setVisualImpact(true);
		r1.setForm(RecipeIngredientForm.EGG_LIKE);
		r1.setTexture(RecipeIngredientTexture.CHEWY);
		r1.setCookingMethod(RecipeIngredientCookingMethod.BOIL);
		r1.setColor("EF9A35");

		RecipeIngredient r2 = new RecipeIngredient();
		r2.setIngredient(mayonaise);
		r2.setRecipe(eggSalad);
		r2.setQuantity(0.5);
		r2.setUnitName("CUP");
		r2.massOrVolumeSetter();
		r2.setVisualImpact(true);
		r2.setForm(RecipeIngredientForm.BLOBS);
		r2.setTexture(RecipeIngredientTexture.MOIST);
		r2.setCookingMethod(RecipeIngredientCookingMethod.MIX);
		r2.setColor("BC11AA");

		RecipeIngredient r3 = new RecipeIngredient();
		r3.setIngredient(chives);
		r3.setRecipe(eggSalad);
		r3.setQuantity(1);
		r3.setUnitName("CUP");
		r3.massOrVolumeSetter();
		r3.setVisualImpact(true);
		r3.setForm(RecipeIngredientForm.CHOPPED);
		r3.setTexture(RecipeIngredientTexture.MOIST);
		r3.setCookingMethod(RecipeIngredientCookingMethod.MIX);
		r3.setColor("3399BB");

		RecipeIngredient r4 = new RecipeIngredient();
		r4.setIngredient(curry);
		r4.setRecipe(eggSalad);
		r4.setQuantity(2);
		r4.setUnitName("TEASPOON");
		r4.massOrVolumeSetter();
		r4.setVisualImpact(true);
		r4.setForm(RecipeIngredientForm.POWDER);
		r4.setTexture(RecipeIngredientTexture.PASTY);
		r4.setCookingMethod(RecipeIngredientCookingMethod.MIX);
		r4.setColor("EE9A5D");

		RecipeIngredient r5 = new RecipeIngredient();
		r5.setIngredient(mustard);
		r5.setRecipe(eggSalad);
		r5.setQuantity(1);
		r5.setUnitName("TABLESPOON");
		r5.massOrVolumeSetter();
		r5.setVisualImpact(false);
//		r5.setForm(RecipeIngredientForm.BLOBS);
//		r5.setTexture(RecipeIngredientTexture.PASTY);
//		r5.setCookingMethod(RecipeIngredientCookingMethod.MIX);
//		r5.setColor("EE9A5D");

		RecipeIngredient r6 = new RecipeIngredient();
		r6.setIngredient(persil);
		r6.setRecipe(eggSalad);
		r6.setQuantity(0.5);
		r6.setUnitName("CUP");
		r6.massOrVolumeSetter();
		r6.setVisualImpact(true);
		r6.setForm(RecipeIngredientForm.LEAVES);
		r6.setTexture(RecipeIngredientTexture.CRUNCHY);
		r6.setCookingMethod(RecipeIngredientCookingMethod.GARNISH);
		r6.setColor("BB9A9E");

		// NO SAVE OF RI INDIVIDUALLY! AS SAVE OF RI IS IMPLIED BY CASCADE IN
		// RECIPE
//		recipeIngredientRepository.save(r1);

		eggSalad.addRecipeIngredient(r1);
		eggSalad.addRecipeIngredient(r2);
		eggSalad.addRecipeIngredient(r3);
		eggSalad.addRecipeIngredient(r4);
		eggSalad.addRecipeIngredient(r5);
		eggSalad.addRecipeIngredient(r6);
		recipeRepository.save(eggSalad);
	}
}
