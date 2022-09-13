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

    @ManyToOne(fetch = FetchType.EAGER) // EAGER is default 4 @ManyToOne
    @JoinColumn(name="ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @Column(name="unit_name")
    private String unitName;

    @Column(name="quantity")
    private double quantity;

    @Column(name="mass")
    @Setter(AccessLevel.NONE)
    private double mass;

    @Column(name="volume")
    @Setter(AccessLevel.NONE)
    private double volume;

    @Column(name="visual_impact")
    private boolean visualImpact = true; // default value

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

    // custom getters (overrides the Lombok standard ones)
    public double getQuantity() {
        // todo: format quantity for view (1.0 -> 1; 1.5 -> 1.5; 10.0 -> 10, ...)
        return quantity;
    }

    // help method for getting volume and mass summed
    public int getMassOrVolume() {
        if (this.mass > 0)
        {
            return (int) mass;
        }
        else if(this.volume > 0)
        {
            return (int) volume;
        }
        else return 0;
    }

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

        sb.append("\n");
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
