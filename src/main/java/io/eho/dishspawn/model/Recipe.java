package io.eho.dishspawn.model;


import io.eho.dishspawn.util.RecipeIngredientForm;
import io.eho.dishspawn.util.RecipeIngredientCookingMethod;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor @Getter @Setter
@Entity
@Table(name = "recipe")
public class Recipe {

    @CreationTimestamp
    @Column(name="timestamp_created")
    @Setter(AccessLevel.NONE)
    private Timestamp timestampCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name="recipe_id")
    private long id;

    @Column(name = "recipe_name")
    private String name;

    @Column(name = "recipe_instructions")
    private String instructions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<RecipeIngredient> recipeIngredients;

    // user property
    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="recipe_chef",
            joinColumns=@JoinColumn(name="recipe_id"),
            inverseJoinColumns = @JoinColumn(name="chef_id"))
    private Set<Chef> chefs = new HashSet<>();

    // constructor with args
    public Recipe(String name, String instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        if (recipeIngredients == null) {
            recipeIngredients = new HashSet<>();
        }
        this.recipeIngredients.add(recipeIngredient);
    }

    public void addChef(Chef chef) {
        if (chefs == null) {
            chefs = new HashSet<>();
        }
        this.chefs.add(chef);
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
        for (Chef c : chefs) {
            sb.append(c);
            sb.append("\n");
        }

        return sb.toString();
    }

    // equals - needed to check if a recipe with same name is different than
    // existing recipe (update recipe, and to facilitate multiple chefs can
    // have the same recipe name)

}
