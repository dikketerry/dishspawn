package io.eho.dishspawn.graphics.processing.shapes;

import io.eho.dishspawn.graphics.processing.ColorizeIt;
import processing.core.PApplet;

public abstract class Shape implements ColorizeIt {

    private PApplet sketch;
    private int xMargin = 12;
    private int yMargin = 12;

    private float x;
    private float y;

//    private float sizeImpact;

    private float xSpeed;
    private float ySpeed;

    private int colorValues;
    private int alpha = 24;
    private int alphaStep = 2;

    public Shape(PApplet sketch) {
        this(sketch, 400, 400);
    }

    public Shape(PApplet sketch, float x, float y) {
        this.sketch = sketch;
        this.x = x;
        this.y = y;
        this.xSpeed = sketch.random(-10, 10);
        this.ySpeed = sketch.random(-10, 10);
    }

    public void step()
    {
//        moveStraightAndBounceAtBorder();
        movePerlinNoiseWithinFrame();
    }

    public abstract void render();

    // default shape -> color implementation
    public void setColor(String hex)
    {

        System.out.println("hex input: " + hex); // diagnostic print

        StringBuilder sb = new StringBuilder();
        sb.append(hex);
        sb.deleteCharAt(0);

        alpha += alphaStep;

        // in- and decrementing the opacity
        if (alpha > 102) {
            alphaStep *= -1;
        }

        if (alpha < 12) {
            alphaStep *= -1;
        }

        String opacity = String.valueOf(alpha);
        sb.insert(0, alpha);

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

    @Override // todo
    public boolean equals(Object o)
    {
        if ((o != null) && (getClass() == o.getClass()) && (((Shape) o).getX() == getX()) && (((Shape) o).getY() == getY())) {
            return true;
        } else return false;
    }

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

        offset1 += 0.02;
        offset2 += 0.02;
    }

}
