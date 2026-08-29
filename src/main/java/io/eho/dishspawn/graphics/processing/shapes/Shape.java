package io.eho.dishspawn.graphics.processing.shapes;

import io.eho.dishspawn.graphics.processing.ColorizeIt;
import io.eho.dishspawn.graphics.processing.util.Randomizer;
import io.eho.dishspawn.model.util.visualproperties.RecipeIngredientTexture;
import processing.core.PApplet;

import java.util.Random;
import java.lang.*;

public abstract class Shape implements ColorizeIt {

    private PApplet sketch;
    private int xMargin = 12;
    private int yMargin = 12;

    private float x;
    private float y;

//    private float sizeImpact; // todo

    private float xSpeed;
    private float ySpeed;

    private int colorValues;
    private int minAlpha = 64;
    private int maxAlpha = 224;
    private int alpha = 24;

    private RecipeIngredientTexture texture;

    // constructor not yet in use, but might be useful later on
    public Shape(PApplet sketch) {
        this(sketch, 400, 400);
    }

    public Shape(PApplet sketch, float x, float y) {
        this.sketch = sketch;
        this.x = x;
        this.y = y;
        this.xSpeed = sketch.random(-10, 10); // only used in moveStraight algo
        this.ySpeed = sketch.random(-10, 10); // only used in moveStraight algo
    }

    // implementation the same for all types of shape to keep it simple for now
    public void step() {
//        moveStraightAndBounceAtBorder(); // per step position shape algo 1
        movePerlinNoiseWithinFrame(); // per step position shape algo 2
    }

    // render is type-of-shape specific (circle needs other set of variables to be rendered than square)
    public abstract void render();

    // default shape -> color implementation
    public void setColor(String hex) {

        System.out.println("hex input: " + hex); // diagnostic print
        StringBuilder sb = new StringBuilder();

        sb.append(hex);
//        System.out.println(sb);

        sb.deleteCharAt(0);
//        System.out.println(sb);

        alpha = Randomizer.getRandomNumberInRange(minAlpha, maxAlpha);
        String opacity = Integer.toHexString(alpha);
        sb.insert(0, opacity);
//        System.out.println("hex value: " + sb); // diagnostic print

        this.colorValues = PApplet.unhex(sb.toString());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getColorValues() {
        return colorValues;
    }

    public PApplet getSketch() {
        return sketch;
    }

    public RecipeIngredientTexture getTexture() {
        return texture;
    }

    public void setTexture(RecipeIngredientTexture texture) {
        this.texture = texture;
    }

    // Apply texture-based rendering style before drawing shape
    protected void applyTextureStyle() {
        if (texture == null) {
            // Default: filled, no stroke
            getSketch().noStroke();
            getSketch().fill(colorValues);
            return;
        }

        switch (texture) {
            case CRUNCHY:
                // Thick stroke, no fill - crispy edges
                getSketch().stroke(colorValues);
                getSketch().strokeWeight(3);
                getSketch().noFill();
                break;
            case CREAMY:
                // Filled with higher transparency - smooth and soft
                getSketch().noStroke();
                getSketch().fill(colorValues);
                break;
            case POWDERY:
                // Thin stroke with light fill - dusty appearance
                getSketch().stroke(colorValues);
                getSketch().strokeWeight(1);
                getSketch().fill(colorValues, 100); // low opacity fill
                break;
            case WATERY:
                // Very transparent fill, no stroke - liquid
                getSketch().noStroke();
                getSketch().fill(colorValues, 80);
                break;
            case OILY:
                // Filled with medium transparency - glossy
                getSketch().noStroke();
                getSketch().fill(colorValues, 180);
                break;
            case FIRM:
                // Solid fill with thin stroke - defined edges
                getSketch().stroke(colorValues);
                getSketch().strokeWeight(2);
                getSketch().fill(colorValues);
                break;
            case CHEWY:
                // Medium stroke, semi-transparent - elastic feel
                getSketch().stroke(colorValues);
                getSketch().strokeWeight(2);
                getSketch().fill(colorValues, 150);
                break;
            case BREADY:
                // Soft edges with fill - airy texture
                getSketch().stroke(colorValues, 100);
                getSketch().strokeWeight(1);
                getSketch().fill(colorValues);
                break;
            case MOIST:
                // Filled with subtle stroke - slightly wet
                getSketch().stroke(colorValues, 150);
                getSketch().strokeWeight(1);
                getSketch().fill(colorValues, 200);
                break;
            case PASTY:
                // Thick, opaque - dense appearance
                getSketch().noStroke();
                getSketch().fill(colorValues, 220);
                break;
            default:
                getSketch().noStroke();
                getSketch().fill(colorValues);
        }
    }

    @Override // todo: check if correct
    public boolean equals(Object o)
    {
        if ((o != null) && (getClass() == o.getClass()) && (((Shape) o).getX() == getX()) && (((Shape) o).getY() == getY())) {
            return true;
        } else return false;
    }

    // HELPERS
    // traditional speeding x and y coordinates and bounce at borders
    private void moveStraightAndBounceAtBorder()
    {
        this.x += this.xSpeed;
        if (this.x < xMargin || this.x > sketch.width - xMargin)
        {
            xSpeed *= -1;
        }

        this.y += ySpeed;
        if (this.y < yMargin || this.y > sketch.height - yMargin)
        {
            ySpeed *= -1;
        }
    }

    private void movePerlinNoiseWithinFrame()
    {
        // noise script to generate shape at different x, y coordinates through Perlin noise
        float offset1 = sketch.random(0, 100);
        float offset2 = sketch.random(9950, 10050);

        this.x = PApplet.map(sketch.noise(offset1), 0, 1, 0, sketch.width);
        this.y = PApplet.map(sketch.noise(offset2), 0, 1, 0, sketch.height);

        offset1 += 0.02; // todo: make offsets global
        offset2 += 0.02;
    }

}
