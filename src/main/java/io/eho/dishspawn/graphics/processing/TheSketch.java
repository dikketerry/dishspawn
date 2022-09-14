package io.eho.dishspawn.graphics.processing;

import io.eho.dishspawn.graphics.processing.shapes.Circle;
import io.eho.dishspawn.graphics.processing.shapes.Rectangle;
import io.eho.dishspawn.graphics.processing.shapes.Shape;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class TheSketch extends PApplet {

    private List<Shape> shapes;
    int red;
    int green;
    int blue;
//    int alpha = 24;
//    int alphaStep = 1;

    public static void main(String[] args)
    {
        PApplet.main("TheSketch");
    }

    @Override
    public void setup()
    {
        frameRate(60);

        red = (int) random(0, 255);
        green = (int) random(0, 255);
        blue = (int) random(0, 255);

        background(red, green, blue);
//        fill(255 - red, 255 - green, 255 - blue);
    }

    @Override
    public void settings()
    {
        size(800, 800);
    }

    @Override
    public void draw() {

//        if (keyPressed)
//        {
//            shapes.add(new Circle(this, mouseX, mouseY));
//        }
//        background(red, green, blue); // refresh background each frame

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