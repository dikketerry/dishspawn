package io.eho.dishspawn.graphics.processing.util;

import io.eho.dishspawn.graphics.processing.shapes.*;
import io.eho.dishspawn.model.RecipeIngredient;
import processing.core.PApplet;

// transform properties RecipeIngredients in visual properties
public final class Transformer {

    // GET RECIPE-INGREDIENT
    // SEND TO TRANSFORMER
    // IN TRANSFORMER, COLLECT PROPERTY X FOR RECIPE-INGREDIENT
    // TRANSLATE INTO VISUAL PROPERTY (SHAPE-TYPE, COLOR, DRAW-TYPE, ETC.)

    private Transformer() {}

    public static Shape setShape(PApplet sketch,
                                 RecipeIngredient recipeIngredient)
    {
        String form = recipeIngredient.getForm().toString();
        Shape shape;

        switch (form) {
            case "BEANS": // ellipse
            case "CRUSHED": // ?? ellipse for now
            case "EGG_LIKE": // ellipse
            case "FLUID": // ?? ellipse for now
            case "GRANULES": // ellipse
            case "MASHED": // large ellipse
            case "PIECE": // custom - later - ellipse for now
            case "RINGS": // rings, yeah! todo
                shape = new Ellipse(sketch);
                break;
            case "BLOBS": // sphere / circle
            case "CONFETTI": // small circles
            case "CRUMBS": // small circles
            case "CURLS": // ?? circles for now
            case "PANCAKE": // large circle
            case "POWDER": // tiny circles / dots / dense
            case "SPHERES": // circles
                shape = new Circle(sketch);
                break;
            case "CHOPPED": // triangles
            case "LEAVES": // custom - later - for now triangle
            case "SHELLS": // custom - later - triangle for now
            case "SLICED": // triangle
            case "WEDGES": // triangle
            case "STRINGS": // line! but line shape to be made - for now rings?
                shape = new Triangle(sketch);
                break;
            case "CUBES": // rectangle
            case "DICED": // rectangle
            case "ECCENTRIC": // ??
            case "SHEETS": // rectangle
            case "SHREDDED": // pff. custom - later - rectangel for now
            case "SMASHED": // rectangle
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



}
