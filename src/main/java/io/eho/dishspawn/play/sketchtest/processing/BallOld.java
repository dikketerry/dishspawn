package io.eho.dishspawn.play.sketchtest.processing;

import processing.core.PApplet;

import java.awt.*;


public class BallOld {

    private PApplet pApplet;
    private float x;
    private float y;
    private float size;
    private float xSpeed;
    private float ySpeed;
    private Color color;

    public BallOld() {
        this(new PApplet());
    }

    public BallOld(PApplet sketch) {
        this(sketch, sketch.width/2, sketch.height/2);
    }

    public BallOld(PApplet sketch, float x, float y) {
        this.pApplet = sketch;
        this.x = x;
        this.y = y;
        this.size = pApplet.random(8, 48);
        this.xSpeed = pApplet.random(-10, 10);
        this.ySpeed = pApplet.random(-10, 10);

    }

    public PApplet getpApplet() {
        return pApplet;
    }

    public void setpApplet(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
