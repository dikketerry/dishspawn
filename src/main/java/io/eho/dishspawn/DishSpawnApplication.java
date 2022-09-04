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
public class DishSpawnApplication /* implements CommandLineRunner */ {
	// AFTER INIT: remove 'implements CommandLineRunner'

//	public DishSpawnApplication() {}
	// AFTER INIT: remove constructor

	public static void main(String[] args) {
		SpringApplication.run(DishSpawnApplication.class, args);
	}

	/* INIT PROCEDURE - AFTER INIT - remove all below! */
//	@Autowired
//	private RecipeService recipeService;
//	@Autowired
//	private IngredientService ingredientService;
//	@Autowired
//	private RecipeIngredientService recipeIngredientService;
//	@Autowired
//	private LikeService likeService;
//	@Autowired
//	private VisualService visualService;
//	@Autowired
//	private ChefService chefService;
//
//	@Override
//	public void run(String... args) throws Exception {

		// INIT 1 create new Chef
//		Chef newChef = new Chef();
//		newChef.setUserName("don valentino");
//		newChef.setEmail("valentino@pasta.it");
//		newChef.setPassword("test123");
//		newChef.setFirstName("Don");
//		newChef.setLastName("Valentino");
//		newChef.setAvatarPath("/img/default-avatar.png");
//
//		System.out.println(newChef);
//		chefService.saveChef(newChef);

		// INIT 2 add ingredients to DB (should be back-end operation only)
//		Ingredient greekYoghurt = new Ingredient("Greek yoghurt", IngredientCategory.DAIRY_CREAM);
//		ingredientService.saveIngredient(greekYoghurt);

		// INIT 3 create new recipe
//		Chef chefDB = chefService.findChefById(18l);
//		Recipe matzoBrei = new Recipe();
//		matzoBrei.setName("Matzo Brei");
//		matzoBrei.setChef(chefDB);
//		List<String> instructions = new ArrayList<>();
//		instructions.add("Heat oil in a medium/large non-stick skillet (regular stainless steel skillet works fine, too). Add onion and season with salt and pepper. Cook, tossing occasionally until the onions are completely tender with a deep, dark brown color and fried and frizzled edges, 8–10 minutes. Taste them along the way and make sure they’re salty and peppery enough.");
//		matzoBrei.setInstructions(instructions);
//
//		// create recipe ingredients for recipe
//		RecipeIngredient matzo = new RecipeIngredient();
//		matzo.setIngredient(ingredientService.findIngredientById(128l));
//		matzo.setRecipe(matzoBrei);
//		matzo.setQuantity(4);
//		matzo.setUnitName("PIECE");
//		matzo.massOrVolumeSetter();
//		matzo.setVisualImpact(true);
//		matzo.setForm(RecipeIngredientForm.SHEETS);
//		matzo.setTexture(RecipeIngredientTexture.CRUNCHY);
//		matzo.setCookingMethod(RecipeIngredientCookingMethod.BAKE);
//		matzo.setColor("#f8ebb2");
//
//		// no save of recipe-ingredients, cascaded through recipe save
//		matzoBrei.addRecipeIngredient(matzo);
//		recipeService.saveRecipe(matzoBrei);

		// INIT 5: create visual with recipe
//		String fileName = "testdish8.png";
//		Visual visual = new Visual();
//		visual.setFileName(fileName);
//		visual.setFileLocation("/img/testspawn/" + fileName);
//		visual.setRecipe(recipeService.findRecipeById(134l));
//		visualService.saveVisual(visual, chefService.findChefById(18l));

//		// INIT 6: add a Like to a visual TODO: NOT WORKING
//		Like like = new Like();
//		likeService.saveLike(visualService.findVisualById(15l), chefService.findChefById(1l));
//
//	}
}
