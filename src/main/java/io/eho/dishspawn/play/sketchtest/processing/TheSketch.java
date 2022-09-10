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
    public void setup() {
        background(111, 222, 11);
        fill(145, 34, 245);
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