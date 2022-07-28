package io.eho.dishspawn.model;

import io.eho.dishspawn.util.IngredientCategory;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@ToString
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="INGREDIENT_ID")
    private long id;

    @Column(name="INGREDIENT_NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="INGREDIENT_CATEGORY")
    private IngredientCategory category;

    @OneToMany(mappedBy = "ingredient")
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    // constructor
    public Ingredient(String name, IngredientCategory category) {
        this.name = Objects.requireNonNull(name);
        this.category = Objects.requireNonNull(category);
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

    public IngredientCategory getCategory() {
        return category;
    }

    public void setCategory(IngredientCategory category) {
        this.category = category;
    }

    public Set<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        this.recipeIngredients.add(recipeIngredient);
    }

}

