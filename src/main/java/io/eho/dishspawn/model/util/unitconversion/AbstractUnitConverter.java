package io.eho.dishspawn.model.util.unitconversion;

public interface AbstractUnitConverter<U> {
    double convert(double quantity, U unitFrom, U unitTo);
    U parseStringToUnit(String input);
}
