package io.eho.dishspawn;

import io.eho.dishspawn.model.*;
import io.eho.dishspawn.model.util.visualproperties.IngredientCategory;
import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientForm;
import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientCookingMethod;
import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientTexture;
import io.eho.dishspawn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DishSpawnApplication implements CommandLineRunner {
	// AFTER INIT: remove 'implements CommandLineRunner'

	public DishSpawnApplication() {}
	// AFTER INIT: remove constructor

	public static void main(String[] args) {
		SpringApplication.run(DishSpawnApplication.class, args);
	}

	/* INIT PROCEDURE - AFTER INIT - remove all below! */
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private IngredientService ingredientService;
	@Autowired
	private RecipeIngredientService recipeIngredientService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private VisualService visualService;
	@Autowired
	private ChefService chefService;

	@Override
	public void run(String... args) throws Exception {

		// INIT 1 create new Chef
		Chef newChef = new Chef();
		newChef.setUserName("masterchef");
		newChef.setEmail("master@cooking.com");
		newChef.setPassword("test123");
		newChef.setAvatarPath("/img/default-avatar.png");

//		System.out.println(newChef);
		chefService.saveChef(newChef); // id 1

		// INIT 2 add ingredients to DB (should be back-end operation only)
		Ingredient egg = new Ingredient("egg", IngredientCategory.EGGS);
		Ingredient mayonaise = new Ingredient("mayonaise",
											  IngredientCategory.CONDIMENT);
		Ingredient chives = new Ingredient("chives", IngredientCategory.HERBS);
		Ingredient curry = new Ingredient("curry", IngredientCategory.SPICES);
		Ingredient mustard = new Ingredient("mustard", IngredientCategory.ADDITIVE);
		Ingredient persil = new Ingredient("persil", IngredientCategory.HERBS);

		ingredientService.saveIngredient(egg); // id 2
		ingredientService.saveIngredient(mayonaise); // id 3
		ingredientService.saveIngredient(chives); // id 4
		ingredientService.saveIngredient(curry); // id 5
		ingredientService.saveIngredient(mustard); // id 6
		ingredientService.saveIngredient(persil); // id 7

		// INIT 3 create new recipe
		Chef chefDB = chefService.findChefById(1l);
		Recipe eggSalad = new Recipe();
		eggSalad.setName("Egg Salad");
		eggSalad.setChef(chefDB);
		List<String> instructions = new ArrayList<>();
		instructions.add("Boil the eggs");
		instructions.add("Mash the boiled eggs in a bowl");
		instructions.add("Add mustard, mayonaise, chives and curry");
		instructions.add("Season to taste");
		instructions.add("Garnish with persil");
		instructions.add("Eat");
		eggSalad.setInstructions(instructions);

//		create recipe ingredients for recipe
		RecipeIngredient r1 = new RecipeIngredient();
		r1.setIngredient(ingredientService.findIngredientById(2l));
		r1.setRecipe(eggSalad);
		r1.setQuantity(1);
		r1.setUnitName("POUND");
		r1.massOrVolumeSetter();
		r1.setVisualImpact(true);
		r1.setForm(RecipeIngredientForm.EGG_LIKE);
		r1.setTexture(RecipeIngredientTexture.CHEWY);
		r1.setCookingMethod(RecipeIngredientCookingMethod.BOIL);
		r1.setColor("EF9A35");

		RecipeIngredient r2 = new RecipeIngredient();
		r2.setIngredient(ingredientService.findIngredientById(3l));
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
		r3.setIngredient(ingredientService.findIngredientById(4l));
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
		r4.setIngredient(ingredientService.findIngredientById(5l));
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
		r5.setIngredient(ingredientService.findIngredientById(6l));
		r5.setRecipe(eggSalad);
		r5.setQuantity(1);
		r5.setUnitName("TABLESPOON");
		r5.massOrVolumeSetter();
		r5.setVisualImpact(false);
// 		no visual properties

		RecipeIngredient r6 = new RecipeIngredient();
		r6.setIngredient(ingredientService.findIngredientById(7l));
		r6.setRecipe(eggSalad);
		r6.setQuantity(0.5);
		r6.setUnitName("CUP");
		r6.massOrVolumeSetter();
		r6.setVisualImpact(true);
		r6.setForm(RecipeIngredientForm.LEAVES);
		r6.setTexture(RecipeIngredientTexture.CRUNCHY);
		r6.setCookingMethod(RecipeIngredientCookingMethod.GARNISH);
		r6.setColor("BB9A9E");

//		no save of recipe-ingredients, cascaded through recipe save
		eggSalad.addRecipeIngredient(r1); // 9
		eggSalad.addRecipeIngredient(r2); // 10
		eggSalad.addRecipeIngredient(r3); // 11
		eggSalad.addRecipeIngredient(r4); // 12
		eggSalad.addRecipeIngredient(r5); // 13
		eggSalad.addRecipeIngredient(r6); // 14
		recipeService.saveRecipe(eggSalad); // id 8 for recipe / then

		// INIT 5: create visual with recipe
		String fileName = "testdish2.jpg";
		Visual visual = new Visual();
		visual.setFileName(fileName);
		visual.setLocation("/img/testspawn/" + fileName);
		visual.setRecipe(recipeService.findById(8l));
		visualService.saveVisual(visual, chefService.findChefById(1l)); // id 15

//		// INIT 6: add a Like to a visual TODO: NOT WORKING
//		Like like = new Like();
//		likeService.saveLike(visualService.findVisualById(15l), chefService.findChefById(1l));
//
	}
}
