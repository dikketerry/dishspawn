package io.eho.dishspawn.play;

import processing.core.PApplet;

public class Ball {

    private PApplet pApplet;
    private float x;
    private float y;
    private float size;
    private float xSpeed;
    private float ySpeed;

    public Ball(PApplet sketch, float x, float y) {
        this.pApplet = sketch;
        this.x = x;
        this.y = y;
        this.size = pApplet.random(8, 48);
        this.xSpeed = pApplet.random(-10, 10);
        this.ySpeed = pApplet.random(-10, 10);

    }

    public void step() {
        x += xSpeed;
        if (x < 0 || x > pApplet.width) {
            xSpeed *= -1;
        }

        y += ySpeed;
        if (y < 0 || y > pApplet.height) {
            ySpeed *= -1;
        }
    }

    public void render() {
        pApplet.ellipse(x, y, size, size);
    }

}
