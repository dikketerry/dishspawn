package io.eho.dishspawn.util;

public enum RecipeIngredientTexture {
    BREADY("Bready"),
    CHEWY("Chewy"),
    CREAMY("Creamy"),
    CRUNCHY("Crunchy"),
    FIRM("Firm"),
    MOIST("Moist"),
    PASTY("Pasty"),
    WATERY("Watery");

    private final String label;

    RecipeIngredientTexture(String label) {
        this.label = label;
    }

}
