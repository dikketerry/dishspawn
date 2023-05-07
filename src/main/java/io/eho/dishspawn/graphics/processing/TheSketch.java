package io.eho.dishspawn.graphics.processing;

import io.eho.dishspawn.graphics.processing.shapes.Circle;
import io.eho.dishspawn.graphics.processing.shapes.Ellipse;
import io.eho.dishspawn.graphics.processing.shapes.Rectangle;
import io.eho.dishspawn.graphics.processing.shapes.Shape;
import org.springframework.stereotype.Service;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheSketch extends PApplet {

    private List<Shape> shapes = new ArrayList<>();
    private int shapeIndex = 0;

    private boolean generate;
    private final int COLOR_CEILING = 255;

    int red;
    int green;
    int blue;

    @Override
    public void setup() {
        frameRate(30);

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

        if (generate) {

//            System.out.println("size: " + shapes.size() + "index: " + shapeIndex + "; class: " + shapes.get(shapeIndex).getClass());

            this.color(shapes.get(shapeIndex).getColorValues());
            shapes.get(shapeIndex).render();

            shapeIndex++;

            if (shapeIndex == shapes.size()) {
                this.noLoop();
            }

            // old way
//            for (Shape s: shapes) {
//                s.render();
//                s.step();
//                // s.colorchange ?
//                // s.slight shapechange ?
//            }

        }
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
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