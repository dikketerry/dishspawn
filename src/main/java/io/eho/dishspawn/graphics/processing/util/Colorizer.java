package io.eho.dishspawn.graphics.processing.util;

import io.eho.dishspawn.graphics.processing.shapes.Shape;
import io.eho.dishspawn.model.RecipeIngredient;
import processing.core.PApplet;

public final class Colorizer {

    private Colorizer() {}

    public static void setColor(PApplet sketch, RecipeIngredient recipeIngredient, Shape shape)
    {
        String c = recipeIngredient.getColor();
        int cToInt = sketch.unhex(c);
        System.out.println(cToInt);
//        sketch.color(red, green, blue, alpha);
    }
}
