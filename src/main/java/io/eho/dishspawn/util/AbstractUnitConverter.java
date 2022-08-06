package io.eho.dishspawn.util;

public interface AbstractUnitConverter<U> {
    double convert(double quantity, U unitFrom, U unitTo);
}
