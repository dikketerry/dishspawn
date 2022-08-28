package io.eho.dishspawn.model.util.visualproperties;

public enum IngredientCategory {
    ADDITIVE("Additive - sugar, salt, sweeteners"),
    CEREAL_PASTA_NOODLES("Cereal - Pasta and noodles"),
    CEREAL_BREAD("Cereal - Bread"),
    CEREAL_BAKED_OTHER("Cereal baked (pizza, tortilla, naan, taco etc.)"),
    CEREAL_RICE("Cereal - Rice"),
    CEREAL_MAIZE("Cereal - Corns"),
    CEREAL_OTHER("Cereal - Other (quinoa, etc.)"),
    CONDIMENT("Condiment (sauces, powders, oils, butter, vinegars"),
    DAIRY_CHEESE("Dairy - Cheese"),
    DAIRY_CREAM("Dairy - Cream"),
    DAIRY_MILKS("Dairy - Milks"),
    EGGS("Eggs"),
    FISH("Fish"),
    FRUITS_POME("Fruits - Pome (apples, pears)"),
    FRUITS_CITRUS("Fruits - Citrus (orange, lemon, clementin)"),
    FRUITS_STONE("Fruits - Stone (abricot, olive, peach, cherry)"),
    FRUITS_BERRIES("Fruits - Berries (strawberry, blueberry, grape, avocado)"),
    FRUITS_TROPICAL("Fruits - Tropical (banana, mangoe, ..)"),
    FRUITS_OTHER("Fruits - Other"),
    FUNGHI("Funghi"),
    HERBS("Herbs"),
    LIQUIDS_WATER("Liquids - Water"),
    LIQUIDS_JUICE("Liquids - Juices"),
    LIQUIDS_ALCOHOL("Liquids - Alcohol"),
    MEAT("Meat"),
    NUTS("Nuts"),
    OILS("Oils"),
    POULTRY("Poultry"),
    SEAFOOD("Seafood"),
    SEEDS("Seeds"),
    SPICES("Spices"),
    UNKNOWN("I do not know where to put this ingredient!"),
    VEGETABLES_LEAFY_GREEN("Vegetables - Leafy green (lettuce, spinach)"),
    VEGETABLES_CRUCIFEROUS("Vegetables - Cruciferous (cabbage, sprouts, " +
                                   "broccoli)"),
    VEGETABLES_MARROW("Vegetables - Marrow (pumpkin, cucumber, zucchini)"),
    VEGETABLES_ROOT("Vegetables - Root (potatoe, carrot)"),
    VEGETABLES_PLANT_STEM("Vegetables - Plant stem (aspergus, celery)"),
    VEGETABLES_ALLIUM("Vegetables - Allium (onion, garlic, shallot)"),
    VEGETABLES_LEGUMES_BEANS("Vegetables - Legumes and beans (soy products, " +
                                     "beans, lentils)"),
    VEGETABLES_OTHER("Vegetables - Other");

    public final String label;

    IngredientCategory(String label) {
        this.label = label;
    }
}
