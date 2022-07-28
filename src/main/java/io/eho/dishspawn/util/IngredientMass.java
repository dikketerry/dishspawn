package io.eho.dishspawn.util;

import lombok.Getter;
import lombok.Setter;

// non-static util class. when creating a Recipe, each ingredient will either
// get a Mass or Volume object, which will be attached to the Ingredient and
// as such attached to the Recipe

@Getter
@Setter
public class IngredientMass {
    private double _grams; // internal representation of Mass

    // conversion factors
    private double _gramsToKgFactor = 0.001;
    private double _gramsToPoundsFactor = 0.00220462262;
    // ... more as needed

    // convert to desired unit
    private double grams = _grams;
    private double kilograms = _grams * _gramsToKgFactor;
    private double pounds = _grams * _gramsToPoundsFactor;

    // constructor - public, it needs to be instantiated per ingredient
    public IngredientMass(double grams) {
        this._grams = grams;
    }

    public IngredientMass fromGrams(double grams) {
        return new IngredientMass(grams);
    }

    public IngredientMass fromKg(double kg) {
        return new IngredientMass(kg / _gramsToKgFactor);
    }

    public IngredientMass fromPounds(double pounds) {
        return new IngredientMass(pounds / _gramsToPoundsFactor);
    }

}
