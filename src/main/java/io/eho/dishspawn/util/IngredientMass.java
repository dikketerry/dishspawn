package io.eho.dishspawn.util;

import lombok.Getter;
import lombok.Setter;

// non-static util class. when creating a Recipe, each ingredient will either
// get a Mass or Volume object, which will be attached to the Ingredient and
// as such attached to the Recipe

// TODO: don't think @Getter and @Setter are needed
@Getter
@Setter
public class IngredientMass {
    private double _grams; // internal representation of Mass

    // conversion factors
    private double _gramsToKgFactor = 0.001;
    private double _gramsToPoundsFactor = 0.00220462262;
    private double _gramsToOunceFactor = 0.03571428571;
    // ... more as needed

    // convert to desired unit
    private double grams = _grams;
    private double kilograms = _grams * _gramsToKgFactor;
    private double pounds = _grams * _gramsToPoundsFactor;
    private double ounces = _grams * _gramsToOunceFactor;

    // constructor - public, it needs to be instantiated per ingredient
    private IngredientMass(double grams) {
        this._grams = grams;
    }

    // factory method - create IngredientMass instance with method
    public IngredientMass fromGrams(double grams) {
        return new IngredientMass(grams);
    }

    public IngredientMass fromKg(double kg) {
        return new IngredientMass(kg / _gramsToKgFactor);
    }

    public IngredientMass fromPounds(double pounds) {
        return new IngredientMass(pounds / _gramsToPoundsFactor);
    }

    public IngredientMass fromOunces(double ounces) {
        return new IngredientMass(ounces / _gramsToOunceFactor);
    }

}
