package io.eho.dishspawn.model;

import io.eho.dishspawn.util.RecipeIngredientForm;
import io.eho.dishspawn.util.RecipeIngredientCookingMethod;
import io.eho.dishspawn.util.RecipeIngredientTexture;
import io.eho.dishspawn.util.unitconversion.MassConverter;
import io.eho.dishspawn.util.unitconversion.VolumeConverter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Timestamp;

// temp solution, in-between class Ingredient - Recipe - need to look into
// MultiMap or Map<K, List<V>> and avoid in-between class, as it will result
// in unnecessary DB entries

@NoArgsConstructor
@ToString
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

//    @Autowired
//    private MassConverter massConverter;
//
//    @Autowired
//    private VolumeConverter volumeConverter;

    @CreationTimestamp
    @Column(name="timestamp_created")
    private Timestamp timestampCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="RECIPE_INGREDIENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="INGREDIENT_ID")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name="RECIPE_ID")
    private Recipe recipe;

    @Column(name="VISUAL_IMPACT")
    private boolean visualImpact = true;                        // default value

    // additional values join table recipe-ingredient
    @Column(name="RECIPE_INGREDIENT_MASS")
    private double mass;

    @Column(name="RECIPE_INGREDIENT_VOLUME")
    private double volume;

    @Column(name="RECIPE_INGREDIENT_COLOR")
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name="RECIPE_INGREDIENT_FORM")
    private RecipeIngredientForm form;

    @Enumerated(EnumType.STRING)
    @Column(name="RECIPE_INGREDIENT_TEXTURE")
    private RecipeIngredientTexture recipeIngredientTexture;

    @Enumerated(EnumType.STRING)
    @Column(name = "RECIPE_INGREDIENT_COOKING_METHOD")
    private RecipeIngredientCookingMethod prepType;

    // the unit name and quantity the recipe_ingredient are provided with
    // ( 3 teaspoon, 1.5 tablespoon, etc.) are stored in DB to enable
    // returning these when giving when a recipe is returned from the DB
    @Column(name="UNIT_NAME")
    private String unitName;

    @Column(name="QUANTITY")
    private double quantity;

    public Long getId() {
        return id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public boolean isVisualImpact() {
        return visualImpact;
    }

    public void setVisualImpact(boolean visualImpact) {
        this.visualImpact = visualImpact;
    }

    public double getMass() {
        return mass;
    }

    public void setMass() {
        MassConverter massConverter = new MassConverter();
        MassConverter.MassUnit massUnit =
                massConverter.parseStringToUnit(this.unitName);
        this.mass = massConverter.convert(this.quantity, massUnit, MassConverter.MassUnit.GRAM);
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

    public RecipeIngredientForm getForm() {
        return form;
    }

    public RecipeIngredientTexture getRecipeIngredientTexture() {
        return recipeIngredientTexture;
    }

    public void setRecipeIngredientTexture(RecipeIngredientTexture recipeIngredientTexture) {
        this.recipeIngredientTexture = recipeIngredientTexture;
    }

    public void setForm(RecipeIngredientForm form) {
        this.form = form;
    }

    public RecipeIngredientCookingMethod getPrepType() {
        return prepType;
    }

    public void setPrepType(RecipeIngredientCookingMethod prepType) {
        this.prepType = prepType;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public double getQuantityUnit() {
        return quantity;
    }

    public void setQuantityUnit(double quantity) {
        this.quantity = quantity;
    }
}
