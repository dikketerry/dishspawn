package io.eho.dishspawn.model;

import io.eho.dishspawn.util.IngredientForm;
import io.eho.dishspawn.util.IngredientPrepType;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

// temp solution, in-between class Ingredient - Recipe - need to look into
// MultiMap or Map<K, List<V>> and avoid in-between class, as it will result
// in unnecessary DB entries

@NoArgsConstructor
@ToString
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

    // TODO: generate ID based on combination of ingredient id & recipe id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RECIPE_INGREDIENT_ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="INGREDIENT_ID")
    private Ingredient ingredient;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="RECIPE_ID")
    private Recipe recipe;

    // additional values join table recipe-ingredient
    @Column(name="RECIPE_INGREDIENT_MASS")
    private double mass;

    @Column(name="RECIPE_INGREDIENT_VOLUME")
    private double volume;

    @Column(name="RECIPE_INGREDIENT_COLOR")
    private String color;

    @Column(name="RECIPE_INGREDIENT_FORM")
    private IngredientForm form;

    @Column(name = "RECIPE_INGREDIENT_PREPTYPE")
    private IngredientPrepType prepType;

    public Long getId() {
        return id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public IngredientForm getForm() {
        return form;
    }

    public void setForm(IngredientForm form) {
        this.form = form;
    }

    public IngredientPrepType getPrepType() {
        return prepType;
    }

    public void setPrepType(IngredientPrepType prepType) {
        this.prepType = prepType;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
