package io.eho.dishspawn.play.sketchtest.processing;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class PlaySketch extends PApplet {

//    private List<BallOld> balls = new ArrayList<>();

    @Override
    public void setup()
    {
        background(111, 222, 11);
//        frameRate(2);
    }

    @Override
    public void settings() {
        size(400, 400);
//        balls.add(new BallOld(this, width/2, height/2));
    }

    @Override
    public void draw() {
//        background(64);
//        for (BallOld b : balls) {
//            b.step();
//            b.render();
//        }
    }

//    @Override
//    public void mousePressed() {
//        balls.add(new BallOld(this, width/2, height/2));
//    }

    public static void main(String[] args) {

        String[] processingArgs = {"PlaySketch"};
        PlaySketch playSketch = new PlaySketch();
        PApplet.runSketch(processingArgs, playSketch);
    }
}
