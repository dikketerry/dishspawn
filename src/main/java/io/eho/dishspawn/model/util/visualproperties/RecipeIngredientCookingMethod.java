package io.eho.dishspawn.model.util.visualproperties;

public enum RecipeIngredientCookingMethod {
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

    RecipeIngredientCookingMethod(String label) {
        this.label = label;
    }

}
