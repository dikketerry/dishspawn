package io.eho.dishspawn.graphics.processing.util;

import io.eho.dishspawn.graphics.processing.shapes.Circle;
import io.eho.dishspawn.graphics.processing.shapes.Rectangle;
import io.eho.dishspawn.graphics.processing.shapes.Shape;
import io.eho.dishspawn.graphics.processing.shapes.Triangle;
import io.eho.dishspawn.model.RecipeIngredient;
import processing.core.PApplet;

// to transform properties from RecipeIngredients into visual properties
public final class Transformer {

    private Transformer() {}

    public static Shape setShape(PApplet sketch, RecipeIngredient recipeIngredient)
    {
        String form = recipeIngredient.getForm().toString();
        Shape shape;

        switch (form) {
            case "BEANS":
            case "BLOBS":
            case "CHOPPED":
            case "CONFETTI":
            case "CRUMBS":
            case "CRUSHED":
            case "CUBES":
            case "CURLS":
            case "DICED":
            case "ECCENTRIC":
            case "EGG_LIKE":
            case "FLUID":
            case "GRANULES":
            case "LEAVES":
            case "MASHED":
            case "PANCAKE":
                shape = new Triangle(sketch);
                break;
            case "PIECE":
            case "POWDER":
            case "RINGS":
            case "SHEETS":
            case "SHELLS":
            case "SHREDDED":
            case "SMASHED":
            case "SLICED":
            case "SPHERES":
            case "STRINGS":
            case "WEDGES":
                shape = new Rectangle(sketch);
                break;
            default:
                shape = new Circle(sketch);
        }
        return shape;
    }


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

    /*
        BEANS("Beans"),
    BLOBS("Blobs"),
    CHOPPED("Chopped"),
    CONFETTI("Confetti"),
    CRUMBS("Crumbs"),
    CRUSHED("Crushed"),
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
    SHREDDED("Shredded"),
    SMASHED("Smashed"),
    SLICED("Sliced"),
    SPHERES("Spheres"),
    STRINGS("Strings"),
    WEDGES("Wedges");
     */


}
