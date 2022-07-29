package io.eho.dishspawn.model;


import io.eho.dishspawn.util.IngredientForm;
import io.eho.dishspawn.util.IngredientPrepType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
//@ToString
@Entity
@Table(name = "recipe")
public class Recipe {

    @CreationTimestamp
    @Column(name="timestamp_created")
    private Timestamp timestampCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RECIPE_ID")
    private long id;

    @Column(name = "RECIPE_NAME")
    private String name;

    @Column(name = "RECIPE_INSTRUCTIONS")
    private String instructions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    // constructor
    public Recipe(String name, String instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Set<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(Set<RecipeIngredient> ingredients) {
        this.recipeIngredients = ingredients;
    }

//    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
//        this.recipeIngredients.add(recipeIngredient);
//    }

    public void addRecipeIngredient(Ingredient ingredient,
                                    IngredientForm ingredientForm,
                                    IngredientPrepType ingredientPrepType,
                                    String color) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setForm(ingredientForm);
        recipeIngredient.setPrepType(ingredientPrepType);
        recipeIngredient.setColor(color);
        this.recipeIngredients.add(recipeIngredient);
    }

    @Override
    public String toString() {

        // create StringBuilder to build up string and allowing a loop
        StringBuilder sb = new StringBuilder();

        sb.append("Recipe name: ");
        sb.append(name);
        sb.append("\n");
        sb.append("Ingredients needed: ");
        sb.append("\n");
        for (RecipeIngredient ri : recipeIngredients) {
            sb.append(ri);
            sb.append("\n");
        }
        sb.append("Instructions: ");
        sb.append(instructions);

        return sb.toString();
    }
}
