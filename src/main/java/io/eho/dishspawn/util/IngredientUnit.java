package io.eho.dishspawn.util;


// just a helper class for now, not really in use, only for documentation of
// the units..
public enum IngredientUnit {
    // Ounce | Gram | Pound | Kilogram | Pinch | Liter | Fluid ounce | Gallon
    // | Pint | Quart | Milliliter | Drop | Cup | Tablespoon | Teaspoon |
    OUNCE("Ounce"),             // 28 grams
    GRAM("Gram"),               // 1 gram
    POUND("Pound"),             // 454 gram
    KILOGRAM("Kilogram"),       // 1000 gram
    PINCH("Pinch"),             //
    LITER("Liter"),             // 1000 milliliter
    FLUID_OUNCE("Fluid ounce"), // 30 milliliter
    GALLON("Gallon"),           // 3800 milliliter
    PINT("Pint"),               // 500 milliliter
    QUART("Quart"),             // 950 milliliter
    MILLILITER("Milliliter"),   // 1 milliliter
    DROP("Drop"),               //
    CUP("Cup"),                 // 250 milliliter
    TABLESPOON("Tablespoon"),   // 15 milliliter
    TEASPOON("Teaspoon");       // 5 milliliter

    private final String label;
//    private final int milliliters;

    IngredientUnit(String label) {
        this.label = label;
    }
}
