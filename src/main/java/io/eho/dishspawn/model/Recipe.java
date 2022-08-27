package io.eho.dishspawn.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    private Long id;

    @Column(name = "recipe_name")
    private String name;

    @ElementCollection
    @CollectionTable(name="recipe_instructions", joinColumns = @JoinColumn(name="recipe_id"))
    @Column(name = "instructions")
    @Size(max = 4000)
    private Set<String> instructions; // Set implementation: LinkedHashSet -
    // values to be unique AND ordered

    // fetchtype to EAGER - this resolved a 'failed to lazily initiate ..'
    // exception - another solution could be to look into @Transactional
    // annotation in service methods
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "recipe", cascade =
            CascadeType.ALL)
    private Set<RecipeIngredient> recipeIngredients;

    // user property - should be possible to save on its own
    // fetchtype to EAGER - this resolved a 'failed to lazily initiate ..'
    // exception - another solution could be to look into @Transactional
    // annotation in service methods
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "chef_id")
    private Chef chef;

    // property for the graphic - to be saved / collected as part of recipe
    @OneToMany(mappedBy = "recipe", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Visual> visuals;

    // constructor with args
    public Recipe(String name, Set<String> instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    // convenience method recipe ingredient
    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        if (recipeIngredients == null) {
            recipeIngredients = new HashSet<>();
        }
        this.recipeIngredients.add(recipeIngredient);
    }

    // convenience method visual
    public void addVisual(Visual visual) {
        if (this.visuals == null) {
            visuals = new HashSet<>();
        }
        this.visuals.add(visual);
    }

    @Override
    public String toString() {

        // create StringBuilder to build up string and allowing a loop
        StringBuilder sb = new StringBuilder();

        sb.append("Recipe name: ");
        sb.append(this.name);
        sb.append("\n");
        sb.append("Ingredients needed: ");
        sb.append("\n");
        for (RecipeIngredient ri : this.recipeIngredients) {
            sb.append(ri);
            sb.append("\n");
        }
        sb.append("Instructions: ");
        sb.append(this.instructions);
        sb.append("\n");
        sb.append("Made by: ");
        sb.append(this.chef.getUserName());

        return sb.toString();
    }

    // equals - needed to check if a recipe with same name is different than
    // existing recipe (update recipe, and to facilitate multiple chefs can
    // have the same recipe name)

}
