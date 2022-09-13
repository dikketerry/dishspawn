package io.eho.dishspawn.graphics.processing;

import io.eho.dishspawn.graphics.processing.shapes.Circle;
import io.eho.dishspawn.graphics.processing.shapes.Rectangle;
import io.eho.dishspawn.graphics.processing.shapes.Shape;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class TheSketch extends PApplet {

    private List<Shape> shapes;

    public static void main(String[] args) {
        PApplet.main("TheSketch");
    }

    @Override
    public void setup() {
//        frameRate(2);

        int red = (int) random(0, 255);
        int green = (int) random(0, 255);
        int blue = (int) random(0, 255);

        background(red, green, blue);
        fill(255 - red, 255 - green, 255 - blue);
    }

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void draw() {

        if (keyPressed)
        {
            shapes.add(new Circle(this, mouseX, mouseY));
        }

        for (Shape s: shapes)
        {
            s.step();
            s.render();
        }
    }

    public void mouseDragged()
    {
        shapes.add(new Rectangle(this, mouseX, mouseY));
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }
}