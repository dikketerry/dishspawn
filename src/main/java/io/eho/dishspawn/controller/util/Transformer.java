package io.eho.dishspawn.controller.util;

// to transform properties from RecipeIngredients into visual properties
public class Transformer {

    /*
    what does a recipe ingredient have?
    - ingredient -> ingredient category (DAIRY, FRUITS, VEGETABLES, etc.)
    - unit (ounce, teaspoon, etc.)
    - mass or volume ( grams or milliliter)
    - visual impact (y or n)
    - form
    - cooking method
    - texture
    - color
    The transformer receives a RecipeIngredient - it should only receive the ones that have visual impact
    Preferably the ri's are received in order of mass / volume - the higher the mass / volume, the earlier it is
    received and treated for transformation and draw
    The transformer then picks:
    1 a shape - based on form
    2 amount of shapes - based on mass / volume
    2 color - based on color
    3 draw method - based on texture
    4 something else, TBD - based on cooking method
    all should also have always a bit of noise added, which can for instance be based on time

    positioning: program some relation between the layers of shapes: if shapes A are located on xA and yA, positions
    of xB and yB should somehow relate to xA and yA.
    background color: relative to set color of biggest ingredient
     */



    // get list of RecipeIngredients



    // stream

    // map RecipeIngredient.property to visual property


}
