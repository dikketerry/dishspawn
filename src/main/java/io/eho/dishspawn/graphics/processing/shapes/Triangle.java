package io.eho.dishspawn.graphics.processing.shapes;

import processing.core.PApplet;

public class Triangle extends Shape {

    private float x2;
    private float y2;
    private float x3;
    private float y3;

    public Triangle(PApplet sketch)
    {
        this(sketch, 200, 400);
    }

    public Triangle(PApplet sketch, float x, float y)
    {
        super(sketch, x, y);
        this.x2 = x + sketch.random(10, 30);
        this.y2 = y + sketch.random(10, 30);
        this.x3 = x + sketch.random(20, 50);
        this.y3 = x + sketch.random(10, -10);
    }

    @Override
    public void render() {
        renderTriangle();
    }

    private void renderTriangle() {
        getSketch().noStroke();
        getSketch().fill(super.getColorValues());
        getSketch().triangle(super.getX(), super.getY(), x2, y2, x3, y3);
    }

    @Override
    public String toString() {
        return "Triangle{" + "x2=" + x2 + ", y2=" + y2 + ", x3=" + x3 + ", y3=" + y3 + '}';
    }
}
