package io.eho.dishspawn.play.sketchtest.processing;

import org.springframework.stereotype.Component;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

//@Component
public class TheSketch extends PApplet {

    public static void main(String[] args) {
        System.out.println("in main method of TheSketch");
        PApplet.main("TheSketch");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    @Override
    public void setup() {
        fill(120, 50, 240);
    }

    @Override
    public void draw() {
        ellipse(width/2, height/2, second(), second());
    }
}