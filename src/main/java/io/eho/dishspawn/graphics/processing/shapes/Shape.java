package io.eho.dishspawn.graphics.processing.shapes;

import io.eho.dishspawn.graphics.processing.util.ColorizeIt;
import processing.core.PApplet;

public abstract class Shape implements ColorizeIt {

    private PApplet sketch;
    private float x;
    private float y;
//    private float sizeImpact;
    private float xSpeed;
    private float ySpeed;

    private int colorValues;

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
        x += xSpeed;
        if (x < 0 || x > sketch.width)
        {
            xSpeed *= -1;
        }

        y += ySpeed;
        if (y < 0 || y > sketch.height)
        {
            ySpeed *= -1;
        }
    }

    public abstract void render();

    // default shape -> color implementation
    public void setColor(String hex)
    {

        System.out.println("hex input: " + hex); // diagnostic print

        StringBuilder sb = new StringBuilder();
        sb.append(hex);
        sb.deleteCharAt(0);
        String alpha = "88";
        sb.insert(0, alpha);

        System.out.println("hex value: " + sb.toString()); // diagnostic print

        this.colorValues = sketch.unhex(sb.toString());
//        return colorValues;
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

    @Override
    public boolean equals(Object o) {
        if ((o != null) && (getClass() == o.getClass()) && (((Shape) o).getX() == getX()) && (((Shape) o).getY() == getY())) {
            return true;
        } else return false;
    }
}
