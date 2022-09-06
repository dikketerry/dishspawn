package io.eho.dishspawn.play;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class PlaySketch extends PApplet {

    private List<Ball> balls = new ArrayList<>();

    @Override
    public void setup() {
//        frameRate(2);
    }

    @Override
    public void settings() {
        size(400, 400);
        balls.add(new Ball(this, width/2, height/2));
    }

    @Override
    public void draw() {
        background(64);
        for (Ball b : balls) {
            b.step();
            b.render();
        }
    }

    public static void main(String[] args) {
        String[] processingArgs = {"PlaySketch"};
        PlaySketch playSketch = new PlaySketch();
        PApplet.runSketch(processingArgs, playSketch);
    }
}
