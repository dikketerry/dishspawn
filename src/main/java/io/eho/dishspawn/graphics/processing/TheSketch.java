package io.eho.dishspawn.graphics.processing;

import org.springframework.stereotype.Component;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

//@Component
public class TheSketch extends PApplet {

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
    }

    @Override
    public void draw() {
        ellipse(width/2, height/2, second(), second());
        // second() returns an int coming from clock (0 - 59)
    }

//    @Override
//    public void run() {
//        System.out.println("running as runnable");
//    }
}