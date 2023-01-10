package io.eho.dishspawn.graphics.processing.shapes;

import io.eho.dishspawn.graphics.processing.ColorizeIt;
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
    public void step()
    {
//        moveStraightAndBounceAtBorder(); // per step position shape algo 1
        movePerlinNoiseWithinFrame(); // per step position shape algo 2
    }

    // render is type-of-shape specific (circle needs other set of variables to be rendered than square)
    public abstract void render();

    // default shape -> color implementation
    public void setColor(String hex)
    {

        System.out.println("hex input: " + hex); // diagnostic print

        StringBuilder sb = new StringBuilder();
        sb.append(hex);
        sb.deleteCharAt(0);

        alpha = getRandomNumberInRange(minAlpha, maxAlpha);
        String opacity = Integer.toHexString(alpha);
        sb.insert(0, opacity);

        System.out.println("hex value: " + sb.toString()); // diagnostic print

        this.colorValues = sketch.unhex(sb.toString());
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

        this.x = sketch.map(sketch.noise(offset1), 0, 1, 0, sketch.width);
        this.y = sketch.map(sketch.noise(offset2), 0, 1, 0, sketch.height);

        offset1 += 0.02; // todo: make offsets global
        offset2 += 0.02;
    }

    private int getRandomNumberInRange(int min, int max)
    {
        if (min >= max)
        {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
