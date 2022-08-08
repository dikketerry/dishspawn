package io.eho.dishspawn.util.unitconversion;

import io.eho.dishspawn.exception.UnitDoesNotExistException;

public class VolumeConverter implements AbstractUnitConverter<VolumeConverter.VolumeUnit> {
    private static final double milliliterToCupFactor = 0.004;
    private static final double milliliterToDropFactor = 20;
    private static final double milliliterToFluidOunceFactor = 0.0333333333;
    private static final double milliliterToGallonFactor = 0.0002631579;
    private static final double milliliterToLiterFactor = 0.001;
    private static final double milliliterToPintFactor = 0.002;
    private static final double milliliterToQuartFactor = 0.0010526316;
    private static final double milliliterToTablespoonFactor = 0.0666666667;
    private static final double milliliterToTeaspoonFactor = 0.2;

    @Override
    public double convert(double quantity, VolumeUnit unitFrom, VolumeUnit unitTo) {

        // note the switch cases below are not an exhaustive list of all possible
        // combinations; only combinations needed to support the
        // application functionality are included:
        // - input recipe: requires VolumeUnits to convert to MILLILITER
        // - extract recipe: requires VolumeUnit MILLILITER to convert to
        // other VolumeUnits
        switch (unitFrom) {
            case MILLILITER:
                switch (unitTo) {
                    case MILLILITER:
                        return quantity;
                    case CUP:
                        return quantity * milliliterToCupFactor;
                    case DROP:
                        return quantity * milliliterToDropFactor;
                    case FLUID_OUNCE:
                        return quantity * milliliterToFluidOunceFactor;
                    case GALLON:
                        return quantity * milliliterToGallonFactor;
                    case LITER:
                        return quantity * milliliterToLiterFactor;
                    case PINT:
                        return quantity * milliliterToPintFactor;
                    case QUART:
                        return quantity * milliliterToQuartFactor;
                    case TABLESPOON:
                        return quantity * milliliterToTablespoonFactor;
                    case TEASPOON:
                        return quantity * milliliterToTeaspoonFactor;
                    default:
                        throw new UnsupportedOperationException();
                }
            case CUP:
                return quantity / milliliterToCupFactor;
            case DROP:
                return quantity / milliliterToDropFactor;
            case FLUID_OUNCE:
                return quantity / milliliterToFluidOunceFactor;
            case GALLON:
                return quantity / milliliterToGallonFactor;
            case LITER:
                return quantity / milliliterToLiterFactor;
            case PINT:
                return quantity / milliliterToPintFactor;
            case QUART:
                return quantity / milliliterToQuartFactor;
            case TABLESPOON:
                return quantity / milliliterToTablespoonFactor;
            case TEASPOON:
                return quantity / milliliterToTeaspoonFactor;
            default:
                throw new UnitDoesNotExistException("unknown unit");
                // note this exception assumes a user would be able to input any
                // kind of unit instead of being limited to a selection
        }
    }

    @Override
    public VolumeUnit parseStringToUnit(String input) {
        return VolumeUnit.valueOf(input);
    }

    // enum embedded because it really belongs to this class only
    public enum VolumeUnit {
        LITER("Liter"),             // 1000 milliliter
        FLUID_OUNCE("Fluid ounce"), // 30 milliliter
        GALLON("Gallon"),           // 3800 milliliter
        PINT("Pint"),               // 500 milliliter
        QUART("Quart"),             // 950 milliliter
        MILLILITER("Milliliter"),   // 1 milliliter
        DROP("Drop"),               // 0.05 milliliter
        CUP("Cup"),                 // 250 milliliter
        TABLESPOON("Tablespoon"),   // 15 milliliter
        TEASPOON("Teaspoon");       // 5 milliliter

        private final String label;

        VolumeUnit(String label) {
            this.label = label;
        }
    }
}
