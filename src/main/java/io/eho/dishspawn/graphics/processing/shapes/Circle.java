package io.eho.dishspawn.graphics.processing.shapes;

import io.eho.dishspawn.graphics.processing.shapes.Shape;
import processing.core.PApplet;

public class Circle extends Shape {

    private float size;

    public Circle(PApplet sketch)
    {
        this(sketch, 400, 400);
    }

    public Circle(PApplet sketch, float x, float y)
    {
        super(sketch, x, y);
        this.size = sketch.random(10, 100);
    }

    @Override
    public void render()
    {
        renderCircle();
    }

    private void renderCircle()
    {
        getSketch().fill(super.getColorValues());
        getSketch().circle(super.getX(), super.getY(), size);
    }

    @Override
    public String toString() {
        return "Circle{" + "size=" + size + '}';
    }
}
