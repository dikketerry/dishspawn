package io.eho.dishspawn.play.sketchtest.processing;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class TheSketch extends PApplet {

    public static void main(String[] args) {
        PApplet.main("io.eho.dishspawn.play.sketchtest.processing.TheSketch");
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