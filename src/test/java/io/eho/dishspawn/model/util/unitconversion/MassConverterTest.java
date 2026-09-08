package io.eho.dishspawn.model.util.unitconversion;

import io.eho.dishspawn.exception.UnitDoesNotExistException;
import org.junit.jupiter.api.Test;

import static io.eho.dishspawn.model.util.unitconversion.MassConverter.MassUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MassConverterTest {

    private static final double DELTA = 1e-6;

    private final MassConverter converter = new MassConverter();

    @Test
    void gramToGram_isIdentity() {
        assertEquals(100.0, converter.convert(100.0, MassUnit.GRAM, MassUnit.GRAM), DELTA);
    }

    @Test
    void gramToKilogram_dividesByThousand() {
        assertEquals(1.0, converter.convert(1000.0, MassUnit.GRAM, MassUnit.KILOGRAM), DELTA);
    }

    @Test
    void gramToOunce_appliesFactor() {
        assertEquals(100.0 * 0.0357142857,
                converter.convert(100.0, MassUnit.GRAM, MassUnit.OUNCE), DELTA);
    }

    @Test
    void gramToPound_appliesFactor() {
        assertEquals(100.0 * 0.0022026432,
                converter.convert(100.0, MassUnit.GRAM, MassUnit.POUND), DELTA);
    }

    @Test
    void kilogramToGram_multipliesByThousand() {
        assertEquals(2000.0, converter.convert(2.0, MassUnit.KILOGRAM, MassUnit.GRAM), DELTA);
    }

    @Test
    void ounceToGram_isInverseOfGramToOunce() {
        double grams = converter.convert(4.0, MassUnit.OUNCE, MassUnit.GRAM);
        assertEquals(4.0, converter.convert(grams, MassUnit.GRAM, MassUnit.OUNCE), DELTA);
    }

    @Test
    void poundToGram_roundTrips() {
        double grams = converter.convert(3.0, MassUnit.POUND, MassUnit.GRAM);
        assertEquals(3.0, converter.convert(grams, MassUnit.GRAM, MassUnit.POUND), DELTA);
    }

    @Test
    void convert_withUnhandledSourceUnit_throwsUnitDoesNotExist() {
        assertThrows(UnitDoesNotExistException.class,
                () -> converter.convert(1.0, MassUnit.PIECE, MassUnit.GRAM));
    }

    @Test
    void convert_gramToUnhandledTargetUnit_throwsUnsupportedOperation() {
        assertThrows(UnsupportedOperationException.class,
                () -> converter.convert(1.0, MassUnit.GRAM, MassUnit.PIECE));
    }

    @Test
    void parseStringToUnit_validName_returnsEnum() {
        assertEquals(MassUnit.KILOGRAM, converter.parseStringToUnit("KILOGRAM"));
    }

    @Test
    void parseStringToUnit_unknownName_throws() {
        assertThrows(IllegalArgumentException.class, () -> converter.parseStringToUnit("STONE"));
    }
}