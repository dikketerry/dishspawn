package io.eho.dishspawn.graphics.processing;

// import io.eho.dishspawn.graphics.processing.shapes.Circle;
// import io.eho.dishspawn.graphics.processing.shapes.Ellipse;
import io.eho.dishspawn.graphics.processing.shapes.Rectangle;
import io.eho.dishspawn.graphics.processing.shapes.Shape;
import org.springframework.stereotype.Service;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheSketch extends PApplet {

    private List<Shape> shapes = new ArrayList<>();
    private int shapeIndex = 0;

    private boolean generate;
    private static final int COLOR_CEILING = 255;

    int red;
    int green;
    int blue;
    private String dominantIngredientColor;

    @Override
    public void setup() {
        frameRate(30);

        // Set background based on dominant ingredient color if provided
        if (dominantIngredientColor != null) {
            setComplementaryBackground(dominantIngredientColor);
        } else {
            // Fallback to random if no dominant color provided
            red = (int) random(0, COLOR_CEILING);
            green = (int) random(0, COLOR_CEILING);
            blue = (int) random(0, COLOR_CEILING);
        }

        background(red, green, blue);
    }

    // Set background color based on complementary color of dominant ingredient
    private void setComplementaryBackground(String hexColor) {
        // Remove # if present
        String hex = hexColor.startsWith("#") ? hexColor.substring(1) : hexColor;

        // Parse RGB values from hex
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        // Calculate complementary color (opposite on color wheel)
        // Convert RGB to HSB, rotate hue by 180 degrees, convert back to RGB
        float[] hsb = java.awt.Color.RGBtoHSB(r, g, b, null);
        float complementaryHue = (hsb[0] + 0.5f) % 1.0f; // Add 180 degrees (0.5 in 0-1 range)

        int rgb = java.awt.Color.HSBtoRGB(complementaryHue, hsb[1], hsb[2]);

        this.red = (rgb >> 16) & 0xFF;
        this.green = (rgb >> 8) & 0xFF;
        this.blue = rgb & 0xFF;

        System.out.println("Dominant ingredient color: " + hexColor);
        System.out.println("Complementary background RGB: " + red + ", " + green + ", " + blue);
    }

    public void setDominantIngredientColor(String hexColor) {
        this.dominantIngredientColor = hexColor;
    }

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void draw() {

        if (generate) {

//            System.out.println("size: " + shapes.size() + "index: " + shapeIndex + "; class: " + shapes.get(shapeIndex).getClass());

            this.color(shapes.get(shapeIndex).getColorValues());
            shapes.get(shapeIndex).render();

            shapeIndex++;

            if (shapeIndex == shapes.size()) {
                this.noLoop();
            }

            // old way
//            for (Shape s: shapes) {
//                s.render();
//                s.step();
//                // s.colorchange ?
//                // s.slight shapechange ?
//            }

        }
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
    }

    @Override
    public void exitActual() {
        this.getSurface().setVisible(false);
    }

    @Override
    public void mouseDragged() {
        shapes.add(new Rectangle(this, mouseX, mouseY));
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

}