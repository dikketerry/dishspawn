package io.eho.dishspawn.util;

public enum IngredientTexture {
    BREADY("Bready"),
    CHEWY("Chewy"),
    CREAMY("Creamy"),
    CRUNCHY("Crunchy"),
    FIRM("Firm"),
    MOIST("Moist"),
    PASTY("Pasty"),
    WATERY("Watery");

    private final String label;

    IngredientTexture(String label) {
        this.label = label;
    }

}
