package io.eho.dishspawn.model.util.unitconversion;

import org.junit.jupiter.api.Test;

import static io.eho.dishspawn.model.util.unitconversion.VolumeConverter.VolumeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VolumeConverterTest {

    private static final double DELTA = 1e-6;

    private final VolumeConverter converter = new VolumeConverter();

    @Test
    void milliliterToMilliliter_isIdentity() {
        assertEquals(250.0,
                converter.convert(250.0, VolumeUnit.MILLILITER, VolumeUnit.MILLILITER), DELTA);
    }

    @Test
    void milliliterToLiter_appliesFactor() {
        assertEquals(1.0,
                converter.convert(1000.0, VolumeUnit.MILLILITER, VolumeUnit.LITER), DELTA);
    }

    @Test
    void milliliterToCup_appliesFactor() {
        assertEquals(1.0,
                converter.convert(250.0, VolumeUnit.MILLILITER, VolumeUnit.CUP), DELTA);
    }

    @Test
    void milliliterToTeaspoon_appliesFactor() {
        assertEquals(1.0,
                converter.convert(5.0, VolumeUnit.MILLILITER, VolumeUnit.TEASPOON), DELTA);
    }

    @Test
    void literToMilliliter_multipliesByThousand() {
        assertEquals(2000.0,
                converter.convert(2.0, VolumeUnit.LITER, VolumeUnit.MILLILITER), DELTA);
    }

    @Test
    void tablespoonToMilliliter_roundTrips() {
        double ml = converter.convert(3.0, VolumeUnit.TABLESPOON, VolumeUnit.MILLILITER);
        assertEquals(3.0,
                converter.convert(ml, VolumeUnit.MILLILITER, VolumeUnit.TABLESPOON), DELTA);
    }

    @Test
    void parseStringToUnit_validName_returnsEnum() {
        assertEquals(VolumeUnit.CUP, converter.parseStringToUnit("CUP"));
    }

    @Test
    void parseStringToUnit_unknownName_throws() {
        assertThrows(IllegalArgumentException.class, () -> converter.parseStringToUnit("BARREL"));
    }
}