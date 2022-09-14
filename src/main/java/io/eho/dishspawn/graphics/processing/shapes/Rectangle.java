package io.eho.dishspawn.graphics.processing.shapes;

import processing.core.PApplet;

public class Rectangle extends Shape {

    private float width;
    private float height;

    public Rectangle(PApplet sketch)
    {
        this(sketch, 200, 200);
    }

    public Rectangle(PApplet sketch, float x, float y)
    {
        super(sketch, x, y);
        this.width = sketch.random(30, 50);
        this.height = sketch.random(10, 70);
    }

    @Override
    public void render()
    {
        renderRectangle();
    }

    private void renderRectangle()
    {
        getSketch().fill(super.getColorValues());
        getSketch().rect(super.getX(), super.getY(), width, height);
    }

    @Override
    public String toString() {
        return "Rectangle{" + "width=" + width + ", height=" + height + '}';
    }
}
