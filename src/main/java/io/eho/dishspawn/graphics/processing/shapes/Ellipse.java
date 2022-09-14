package io.eho.dishspawn.graphics.processing.shapes;

import processing.core.PApplet;

public class Ellipse extends Shape {

    private float width;
    private float height;

    public Ellipse(PApplet sketch)
    {
        this(sketch, 600, 600);
    }

    public Ellipse(PApplet sketch, float x, float y)
    {
        super(sketch, x, y);
        this.width = sketch.random(10, 60);
        this.height = sketch.random(30, 40);
    }

    @Override
    public void render() {
        renderEllipse();
    }

    private void renderEllipse() {
        getSketch().fill(super.getColorValues());
        getSketch().ellipse(super.getX(), super.getY(), width, height);
    }

    @Override
    public String toString() {
        return "Ellipse{" + "width=" + width + ", height=" + height + '}';
    }
}
