package io.eho.dishspawn.util;

import lombok.Getter;
import lombok.Setter;

// non-static util class. when creating a Recipe, each ingredient will either
// get a Mass or Volume object, which will be attached to the Ingredient and
// as such attached to the Recipe

@Getter
@Setter
public class IngredientVolume {
    // internal representation
    private double _milliliter;
    // conversion factors
    private double _milliliterToLtr = 0.001;
    private double _millilterToCup = 0.005;

    //
    private double milliliters = _milliliter;
    private double liters = _milliliter * _milliliterToLtr;
    private double cups = _milliliter * _millilterToCup;

    // constructor - can it be private due to creating instance in util-methods?
    public IngredientVolume(double milliliters) {
        this._milliliter = milliliters;
    }

    // utility methods
    public IngredientVolume fromMilliliter(double milliliters) {
        return new IngredientVolume(milliliters);
    }

    public IngredientVolume fromLiter(double liters) {
        return new IngredientVolume(liters / _milliliterToLtr);
    }

    public IngredientVolume fromCup(double cups) {
        return new IngredientVolume(cups / _millilterToCup);

    }

}
