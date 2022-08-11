package io.eho.dishspawn;

import io.eho.dishspawn.model.*;
import io.eho.dishspawn.repository.*;
import io.eho.dishspawn.model.util.visualproperties.IngredientCategory;
import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientForm;
import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientCookingMethod;
import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientTexture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DishSpawnApplication /** implements CommandLineRunner */ {

//	public DishSpawnApplication() {}

	public static void main(String[] args) {
		SpringApplication.run(DishSpawnApplication.class, args);
	}

	// to test, autowired on the field level for now..
//	@Autowired
//	private RecipeRepository recipeRepository;
//	@Autowired
//	private IngredientRepository ingredientRepository;
//	@Autowired
//	private RecipeIngredientRepository recipeIngredientRepository;
//	@Autowired
//	private LikeRepository likeRepository;
//	@Autowired
//	private VisualRepository visualRepository;
//	@Autowired
//	private ChefRepository chefRepository;
//
//	@Override
//	public void run(String... args) throws Exception {
//
//		// register new chef
//		Chef chefToDB = new Chef();
//		chefToDB.setFirstName("erik");
//		chefToDB.setLastName("hollanders");
//		chefToDB.setUserName("masterchef");
//		chefToDB.setPassword("test123");
//		chefToDB.setEmail("erik.hollanders@gmail.com");
//		chefRepository.save(chefToDB); // id 1
//
//		// add ingredients to DB (should be back-end operation only)
//		Ingredient egg = new Ingredient("egg", IngredientCategory.EGGS);
//		Ingredient mayonaise = new Ingredient("mayonaise",
//											  IngredientCategory.CONDIMENT);
//		Ingredient chives = new Ingredient("chives", IngredientCategory.HERBS);
//		Ingredient curry = new Ingredient("curry", IngredientCategory.SPICES);
//		Ingredient mustard = new Ingredient("mustard", IngredientCategory.ADDITIVE);
//		Ingredient persil = new Ingredient("persil", IngredientCategory.HERBS);
//
//		ingredientRepository.save(egg); // id 2
//		ingredientRepository.save(mayonaise); // id 3
//		ingredientRepository.save(chives); // id 4
//		ingredientRepository.save(curry); // id 5
//		ingredientRepository.save(mustard); // id 6
//		ingredientRepository.save(persil); // id 7
//
//		// create new recipe
//		Chef chefFromDB = chefRepository.getReferenceById(1l);
//		Recipe eggSalad = new Recipe("Egg salad", "Boil the eggs, mash them " +
//				"in " +
//				"a bowl, add mustard, mayonaise, chives and curry, season to " +
//				"taste, garnish with persil, eat");
//		eggSalad.setChef(chefFromDB); // todo as the recipe is created by a
//		// logged-in user, the chef for this new recipe should be
//		// automatically set
//
//		// create recipe ingredients for recipe
//		RecipeIngredient r1 = new RecipeIngredient();
//		r1.setIngredient(egg);
//		r1.setRecipe(eggSalad); // this should also probably not be set by
//		// client, as a RecipeIngredient already should belong to a recipe
//		// (new or existing)
//		r1.setQuantity(1);
//		r1.setUnitName("POUND");
//		r1.massOrVolumeSetter(); // this should not be set by client - it should be
//		// calculated prior to saving the recipe ingredient (same for volume)
//		r1.setVisualImpact(true);
//		r1.setForm(RecipeIngredientForm.EGG_LIKE);
//		r1.setTexture(RecipeIngredientTexture.CHEWY);
//		r1.setCookingMethod(RecipeIngredientCookingMethod.BOIL);
//		r1.setColor("EF9A35");
//
//		RecipeIngredient r2 = new RecipeIngredient();
//		r2.setIngredient(mayonaise);
//		r2.setRecipe(eggSalad);
//		r2.setQuantity(0.5);
//		r2.setUnitName("CUP");
//		r2.massOrVolumeSetter();
//		r2.setVisualImpact(true);
//		r2.setForm(RecipeIngredientForm.BLOBS);
//		r2.setTexture(RecipeIngredientTexture.MOIST);
//		r2.setCookingMethod(RecipeIngredientCookingMethod.MIX);
//		r2.setColor("BC11AA");
//
//		RecipeIngredient r3 = new RecipeIngredient();
//		r3.setIngredient(chives);
//		r3.setRecipe(eggSalad);
//		r3.setQuantity(1);
//		r3.setUnitName("CUP");
//		r3.massOrVolumeSetter();
//		r3.setVisualImpact(true);
//		r3.setForm(RecipeIngredientForm.CHOPPED);
//		r3.setTexture(RecipeIngredientTexture.MOIST);
//		r3.setCookingMethod(RecipeIngredientCookingMethod.MIX);
//		r3.setColor("3399BB");
//
//		RecipeIngredient r4 = new RecipeIngredient();
//		r4.setIngredient(curry);
//		r4.setRecipe(eggSalad);
//		r4.setQuantity(2);
//		r4.setUnitName("TEASPOON");
//		r4.massOrVolumeSetter();
//		r4.setVisualImpact(true);
//		r4.setForm(RecipeIngredientForm.POWDER);
//		r4.setTexture(RecipeIngredientTexture.PASTY);
//		r4.setCookingMethod(RecipeIngredientCookingMethod.MIX);
//		r4.setColor("EE9A5D");
//
//		RecipeIngredient r5 = new RecipeIngredient();
//		r5.setIngredient(mustard);
//		r5.setRecipe(eggSalad);
//		r5.setQuantity(1);
//		r5.setUnitName("TABLESPOON");
//		r5.massOrVolumeSetter();
//		r5.setVisualImpact(false);
////		r5.setForm(RecipeIngredientForm.BLOBS);
////		r5.setTexture(RecipeIngredientTexture.PASTY);
////		r5.setCookingMethod(RecipeIngredientCookingMethod.MIX);
////		r5.setColor("EE9A5D");
//
//		RecipeIngredient r6 = new RecipeIngredient();
//		r6.setIngredient(persil);
//		r6.setRecipe(eggSalad);
//		r6.setQuantity(0.5);
//		r6.setUnitName("CUP");
//		r6.massOrVolumeSetter();
//		r6.setVisualImpact(true);
//		r6.setForm(RecipeIngredientForm.LEAVES);
//		r6.setTexture(RecipeIngredientTexture.CRUNCHY);
//		r6.setCookingMethod(RecipeIngredientCookingMethod.GARNISH);
//		r6.setColor("BB9A9E");
//
//		// NO SAVE OF RI INDIVIDUALLY! AS SAVE OF RI IS IMPLIED BY CASCADE IN
//		// RECIPE
////		recipeIngredientRepository.save(r1);
//
//		eggSalad.addRecipeIngredient(r1); // 9
//		eggSalad.addRecipeIngredient(r2); // 10
//		eggSalad.addRecipeIngredient(r3); // 11
//		eggSalad.addRecipeIngredient(r4); // 12
//		eggSalad.addRecipeIngredient(r5); // 13
//		eggSalad.addRecipeIngredient(r6); // 14
//
//		// create visual
//		Visual visual = new Visual();
//		visual.setName("visual1.jpg");
//
//		// attach visual to recipe
//		eggSalad.addVisual(visual); // id 15
//		recipeRepository.save(eggSalad); // id 8 for recipe / then
//		// recipeIngredients 9 to 14 / then visual
//
//		// add a Like to eggSalad
//		Like like = new Like();
//		like.setRecipe(recipeRepository.getReferenceById(8l));
//		likeRepository.save(like); // id 16
//
//	}
}
