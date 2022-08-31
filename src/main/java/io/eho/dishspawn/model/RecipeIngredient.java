package io.eho.dishspawn.model;

import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientForm;
import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientCookingMethod;
import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientTexture;
import io.eho.dishspawn.model.util.unitconversion.MassConverter;
import io.eho.dishspawn.model.util.unitconversion.VolumeConverter;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter @Setter
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

    @CreationTimestamp
    @Column(name="timestamp_created")
    private Timestamp timestampCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name="recipe_ingredient_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER is default @ManyToOne, but
    // hey, let's get it explicit to understand what's happening
    @JoinColumn(name="ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    // the unit name and quantity the recipe_ingredient are provided with
    // ( 3 teaspoon, 1.5 tablespoon, etc.) are stored in DB to enable
    // returning these when giving when a recipe is returned from the DB
    @Column(name="unit_name")
    private String unitName;

    @Column(name="quantity")
    private double quantity;

    // additional values join table recipe-ingredient
    @Column(name="mass")
    @Setter(AccessLevel.NONE)
    private double mass;

    @Column(name="volume")
    @Setter(AccessLevel.NONE)
    private double volume;

    @Column(name="visual_impact")
    private boolean visualImpact = true;                        // default value

    @Enumerated(EnumType.STRING)
    @Column(name="form")
    private RecipeIngredientForm form;

    @Enumerated(EnumType.STRING)
    @Column(name="texture")
    private RecipeIngredientTexture texture;

    @Enumerated(EnumType.STRING)
    @Column(name = "cooking_method")
    private RecipeIngredientCookingMethod cookingMethod;

    @Column(name="color")
    private String color;

    // move to service?
    public void massOrVolumeSetter() {

        if (this.unitName == "PIECE") {
            this.mass = -1;
            this.volume = -1;
        } else if (this.unitName == null) {
            this.mass = 0;
            this.volume = 0;
        } else {
            switch (this.unitName) {
                case "MILLILITER":
                case "CUP":
                case "DROP":
                case "FLUID_OUNCE":
                case "GALLON":
                case "LITER":
                case "PINT":
                case "QUART":
                case "TABLESPOON":
                case "TEASPOON":
                    this.volume = calculateVolume();
                    break;
                case "GRAM":
                case "OUNCE":
                case "KILOGRAM":
                case "POUND":
                    this.mass = calculateMass();
                    break;
                default: throw new UnsupportedOperationException();
            }
        }
    }

    private double calculateMass() {
        MassConverter massConverter = new MassConverter();
        MassConverter.MassUnit massUnit =
                massConverter.parseStringToUnit(this.unitName);
        return massConverter.convert(this.quantity, massUnit,
                                MassConverter.MassUnit.GRAM);
    }

    private double calculateVolume() {
        VolumeConverter volumeConverter = new VolumeConverter();
        VolumeConverter.VolumeUnit volumeUnit =
                volumeConverter.parseStringToUnit(this.unitName);
        return volumeConverter.convert(this.quantity, volumeUnit,
                                  VolumeConverter.VolumeUnit.MILLILITER);
    }

    // toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Ingredient - " + this.ingredient.getName() + " ");
        sb.append("in recipe - " + this.recipe.getName() + ": ");
        sb.append("\n");
        sb.append(this.quantity + " " + this.unitName);
        sb.append("\n");
        sb.append("Ingredient has visual impact on recipe? " + this.visualImpact);

        if (this.visualImpact == false) {
            return sb.toString();
        }

        sb.append("Form of ingredient: " + this.form);
        sb.append("\n");
        sb.append("texture: " + this.texture);
        sb.append("\n");
        sb.append("cooking method: " + this.cookingMethod);
        sb.append("\n");
        sb.append("color: " + this.color);

        return sb.toString();

    }


    // equals / hash
}
