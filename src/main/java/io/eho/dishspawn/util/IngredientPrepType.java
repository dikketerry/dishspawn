package io.eho.dishspawn.util;

public enum IngredientPrepType {
    ADD("Add"),
    BAKE("Bake"),
    BOIL("Boil"),
    BROIL("Broil"),
    BRAISE("Braise"),
    GARNISH("Garnish"),
    GRILL("Grill"),
    MELT("Melt"),
    MIX("Mix"),
    POACH("Poach"),
    ROAST("Roast"),
    SAUTE("Saute"),
    SIMMER("Simmer"),
    SPRINKLE("Sprinkle"),
    STEAM("Steam"),
    STEW("Stew"),
    STIR("Stir");

    private final String label;

    IngredientPrepType(String label) {
        this.label = label;
    }

}
