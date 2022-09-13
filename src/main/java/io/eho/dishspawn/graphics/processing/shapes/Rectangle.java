package io.eho.dishspawn.graphics.processing.shapes;

import io.eho.dishspawn.graphics.processing.shapes.Shape;
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
        this.width = sketch.random(5, 50);
        this.height = sketch.random(5, 50);
    }

    @Override
    public void render()
    {
        renderCircle();
    }

    private void renderCircle()
    {
        getSketch().rect(super.getX(), super.getY(), width, height);
    }

    @Override
    public String toString() {
        return "Rectangle{" + "width=" + width + ", height=" + height + '}';
    }
}
