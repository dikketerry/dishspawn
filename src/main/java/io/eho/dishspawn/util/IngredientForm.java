package io.eho.dishspawn.util;

public enum IngredientForm {
    BEANS("Beans"),
    BLOBS("Blobs"),
    CHOPPED("Chopped"),
    CONFETTI("Confetti"),
    CRUMBS("Crumbs"),
    CUBES("Cubes"),
    CURLS("Curls"),
    DICED("Diced"),
    ECCENTRIC("Eccentric"),
    EGG_LIKE("Egg-like"),
    FLUID("Fluid"),
    GRANULES("Granules"),
    LEAVES("Leaves"),
    MASHED("Mashed"),
    PANCAKE("Pancake"),
    PIECE("Piece"),
    POWDER("Powder"),
    RINGS("Rings"),
    SHEETS("Sheets"),
    SHELLS("Shells"),
    SMASHED("Smashed"),
    SLICED("Sliced"),
    SPHERES("Spheres"),
    STRINGS("Strings"),
    WEDGES("Wedges");

    public final String label;

    IngredientForm(String label) {
        this.label = label;
    }
}
