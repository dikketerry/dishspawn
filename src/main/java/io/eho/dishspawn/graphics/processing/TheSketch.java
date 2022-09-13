package io.eho.dishspawn.graphics.processing;

import org.springframework.stereotype.Component;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

//@Component
public class TheSketch extends PApplet {

    private List<Circle> circles = new ArrayList<>();

    public static void main(String[] args) {
        PApplet.main("TheSketch");
    }

    @Override
    public void setup() {

        int red = (int) random(0, 255);
        int green = (int) random(0, 255);
        int blue = (int) random(0, 255);

        background(red, green, blue);
        fill(255 - red, 255 - green, 255 - blue);
    }

    @Override
    public void settings() {
        size(800, 800);
        circles.add(new Circle(this, 100, 200));
    }

    @Override
    public void draw() {
        ellipse(width/2, height/2, second(), second()); // second() returns an int coming from clock (0 - 59)
        for (Circle c: circles)
        {
            c.step();
            c.render();
        }
    }

    public void mouseDragged()
    {
        circles.add(new Circle(this, mouseX, mouseY));
    }

}