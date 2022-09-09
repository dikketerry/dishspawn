package io.eho.dishspawn.play.modeltest;

import io.eho.dishspawn.exception.UnitDoesNotExistException;
import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.model.util.visualproperties.IngredientCategory;
import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientForm;
import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientCookingMethod;

public class PlayWithMass {

    public static void main(String[] args) {

        Ingredient penne = new Ingredient("penne",
                                          IngredientCategory.CEREAL_PASTA_NOODLES);

        RecipeIngredient ri = new RecipeIngredient();
        ri.setIngredient(penne);
        ri.setForm(RecipeIngredientForm.SPHERES);
        ri.setColor("#EFC953");
        ri.setCookingMethod(RecipeIngredientCookingMethod.BOIL);

        // mass extra stuff
        // simulate input value user
        String inputUnit = "OUNCE";
        // parse enum equivalent user input
        Unit inputUnitEnum = Unit.valueOf(inputUnit);
        // set a quantity (user input)
        double quantity = 1.5;

        // convert userInput to grams
        MassConverter m = new MassConverter();
        double massPenne = m.convert(quantity, inputUnitEnum, Unit.GRAM);

        // continue setting the RecipeIngredient
        ri.massOrVolumeSetter();

        System.out.println("Recipe Ingredient: " + ri);

        MassConverter mass = new MassConverter();
        System.out.println(mass.convert(1, Unit.OUNCE, Unit.GRAM));

    }

    interface AbstractUnitConverter<U> {
        double convert(double quantity, U unitFrom, U unitTo);
//        double convertFrom(double quantity, U unitTo);
    }

    static class MassConverter implements AbstractUnitConverter<Unit> {

        private double toGrams = -1;
        private double fromGrams = -1;

        private static final double gram = 1;
        private static final double gramToOunceFactor = 0.0357142857;
        private static final double gramToPoundFactor = 0.0022026432;
        private static final double gramToKilogramFactor = 0.001;

        @Override
        public double convert(double quantity, Unit unitFrom, Unit unitTo) {

            switch (unitFrom) {
                case GRAM: toGrams = quantity;
                case OUNCE: toGrams = quantity / gramToOunceFactor;
                case KILOGRAM: toGrams = quantity / gramToKilogramFactor;
                case POUND: toGrams = quantity / gramToPoundFactor;
//                default: throw new UnitDoesNotExistException("unknown unit");
            }

            // if statement to check if grams were properly assigned
            if (toGrams == -1) {
                // TODO: exception adjustment - does not capture properly
                throw new UnitDoesNotExistException(("unknown unit"));
            } else return toGrams;
        }

//        @Override
//        public double convertFrom(double quantity, Unit unitTo) {
//
//            switch (unitTo) {
//                case GRAM: return quantity;
//                case KILOGRAM: return gramToKilogramFactor * quantity;
//                case OUNCE: return gramToOunceFactor * quantity;
//                case POUND: return gramToPoundFactor * quantity;
//            }
//
//            if (fromGrams == -1) {
//                throw new UnitDoesNotExistException("unknown unit");
//            } else return fromGrams;
//        }

    }

    enum Unit {
        GRAM, OUNCE, POUND, KILOGRAM;
    }


}
