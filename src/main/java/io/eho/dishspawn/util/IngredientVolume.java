package io.eho.dishspawn.util;

import lombok.Getter;
import lombok.Setter;

// non-static util class. when creating a Recipe, each ingredient will either
// get a Mass or Volume object, which will be attached to the Ingredient and
// as such attached to the Recipe

// TODO: don't think @Getter and @Setter are needed
@Getter
@Setter
public class IngredientVolume {
    // internal representation
    private double _milliliter;
    // conversion factors
    private double _milliliterToLtr = 0.001;
    private double _milliliterToCup = 0.004;
    private double _milliliterToFluidOunce = 0.0333333333;
    private double _milliliterToGallon = 0.0002631579;
    private double _milliliterToPint = 0.002;
    private double _milliliterToQuart = 0.0010526315;
    private double _milliliterToTablespoon = 0.0666666667;
    private double _milliliterToTeaspoon = 0.2;

    //
    private double milliliters = _milliliter;
    private double liters = _milliliter * _milliliterToLtr;
    private double cups = _milliliter * _milliliterToCup;
    private double fluidOunces = _milliliter * _milliliterToFluidOunce;
    private double gallons = _milliliter * _milliliterToGallon;
    private double pints = _milliliter * _milliliterToPint;
    private double quarts = _milliliter * _milliliterToQuart;
    private double tablespoons = _milliliter * _milliliterToTablespoon;
    private double teaspoons = _milliliter * _milliliterToTeaspoon;

    // constructor - can it be private due to creating instance in
    // util-methods? YES (TBC)
    private IngredientVolume(double milliliters) {
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
        return new IngredientVolume(cups / _milliliterToCup);
    }

    public IngredientVolume fromFluidOunce(double fluidOunces) {
        return new IngredientVolume(fluidOunces / _milliliterToFluidOunce);
    }

    public IngredientVolume fromGallon(double gallons) {
        return new IngredientVolume(gallons / _milliliterToGallon);
    }

    public IngredientVolume fromPint(double pints) {
        return new IngredientVolume(pints / _milliliterToPint);
    }

    public IngredientVolume fromQuart(double quarts) {
        return new IngredientVolume(quarts / _milliliterToQuart);
    }

    public IngredientVolume fromTablespoon(double tablespoons) {
        return new IngredientVolume(tablespoons / _milliliterToTablespoon);
    }

    public IngredientVolume fromTeaspoon(double teaspoons) {
        return new IngredientVolume(teaspoons / _milliliterToTeaspoon);
    }

}
