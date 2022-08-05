package io.eho.dishspawn.play;

import io.eho.dishspawn.exception.UnitDoesNotExistException;

public class PlayWithMass {

    public static void main(String[] args) {

        MassConverter mass = new MassConverter();
        System.out.println(mass.convertFrom(1, Unit.OUNCE));

    }

    interface AbstractUnitConverter<U> {
        double convertTo(double quantity, U unitFrom);
        double convertFrom(double quantity, U unitTo);
    }

    static class MassConverter implements AbstractUnitConverter<Unit> {

        private double toGrams = -1;
        private double fromGrams = -1;

        private static final double gram = 1;
        private static final double gramToOunceFactor = 0.0357142857;
        private static final double gramToPoundFactor = 0.0022026432;
        private static final double gramToKilogramFactor = 0.001;

        @Override
        public double convertTo(double quantity, Unit unitFrom) {

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

        @Override
        public double convertFrom(double quantity, Unit unitTo) {

            switch (unitTo) {
                case GRAM: return quantity;
                case KILOGRAM: return gramToKilogramFactor * quantity;
                case OUNCE: return gramToOunceFactor * quantity;
                case POUND: return gramToPoundFactor * quantity;
            }

            if (fromGrams == -1) {
                throw new UnitDoesNotExistException("unknown unit");
            } else return fromGrams;
        }

    }

    enum Unit {
        GRAM, OUNCE, POUND, KILOGRAM;
    }


}
