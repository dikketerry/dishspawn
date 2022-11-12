package io.eho.dishspawn.graphics.processing;

import io.eho.dishspawn.graphics.processing.shapes.Circle;
import io.eho.dishspawn.graphics.processing.shapes.Ellipse;
import io.eho.dishspawn.graphics.processing.shapes.Rectangle;
import io.eho.dishspawn.graphics.processing.shapes.Shape;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class TheSketch extends PApplet {

    private List<Shape> shapes = new ArrayList<>();
//    private boolean generate;
    private final int COLOR_CEILING = 255;

    int red;
    int green;
    int blue;

    @Override
    public void setup() {
        frameRate(40);

        red = (int) random(0, COLOR_CEILING);
        green = (int) random(0, COLOR_CEILING);
        blue = (int) random(0, COLOR_CEILING);

        background(red, green, blue);
    }

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void draw() {
//        if (generate) {
            for (Shape s: shapes) {
                s.step();
                s.render();
            }
//        }
    }

    @Override
    public void exitActual() {
        this.getSurface().setVisible(false);
    }

    public void mouseDragged() {
        shapes.add(new Rectangle(this, mouseX, mouseY));
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

}