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
public class DishSpawnApplication /*implements CommandLineRunner*/ {
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
//		Ingredient groundPork = new Ingredient("ground pork", IngredientCategory.MEAT);
//		ingredientService.saveIngredient(groundPork);

		// INIT 3 create new recipe
//		Chef chefDB = chefService.findChefById(18l);
//		Recipe aNoodleSoup = new Recipe();
//		aNoodleSoup.setName("Pork Noodle Soup with Broccoli Rabe and Fennel");
//		aNoodleSoup.setChef(chefDB);
//		List<String> instructions = new ArrayList<>();
//		instructions.add("Heat one tablespoon olive oil in a large pot over medium-high heat. Add pork and season with salt and pepper. Cook, resisting the urge to break it up too much at first. As it browns, break it up into small pieces; some of the pork will get very small (these will get very brown and crispy) and some will stay larger in sausage-like clumps (these will stay tender and juicer). Once the pork is about 80% browned to your liking, about 8–10 minutes, add the garlic. Continue cooking until the pork is well browned throughout and the garlic is softened and starting to brown around the edges, another 4–5 minutes.");
//		aNoodleSoup.setInstructions(instructions);

		// create recipe ingredients for recipe
//		RecipeIngredient groundPork = new RecipeIngredient();
//		groundPork.setIngredient(ingredientService.findIngredientById(103l));
//		groundPork.setRecipe(aNoodleSoup);
//		groundPork.setQuantity(1);
//		groundPork.setUnitName("POUND");
//		groundPork.massOrVolumeSetter();
//		groundPork.setVisualImpact(true);
//		groundPork.setForm(RecipeIngredientForm.WEDGES);
//		groundPork.setTexture(RecipeIngredientTexture.FIRM);
//		groundPork.setCookingMethod(RecipeIngredientCookingMethod.ADD);
//		groundPork.setColor("#ca321d");

		// no save of recipe-ingredients, cascaded through recipe save
//		aNoodleSoup.addRecipeIngredient(groundPork);

		// INIT 5: create visual with recipe
//		String fileName = "testdish7.jpg";
//		Visual visual = new Visual();
//		visual.setFileName(fileName);
//		visual.setFileLocation("/img/testspawn/" + fileName);
//		visual.setRecipe(recipeService.findRecipeById(111l));
//		visualService.saveVisual(visual, chefService.findChefById(18l));

//		// INIT 6: add a Like to a visual TODO: NOT WORKING
//		Like like = new Like();
//		likeService.saveLike(visualService.findVisualById(15l), chefService.findChefById(1l));
//
//	}
}
