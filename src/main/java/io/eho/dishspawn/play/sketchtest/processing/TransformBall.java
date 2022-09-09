package io.eho.dishspawn.play.sketchtest.processing;

import processing.core.PApplet;

import java.awt.*;

public class TransformBall {

//    Function<BallDB, Ball> ballDBToBall = new Function<BallDB, Ball>() {
//
//        @Override
//        public Ball apply(BallDB ballDB) {
//            Ball ball = new Ball();
//
//            return null;
//        }
//    };

    public static Ball from(BallDB ballDB) {
        Ball ball = new Ball();
        // map logic
        ball.setpApplet(new PApplet());
        ball.setSize(ballDB.getAmount() / 10);
        ball.setColor(hex2Rgb(ballDB.getColor()));
        ball.setX(100);
        ball.setY(200);
        ball.setxSpeed(2);
        ball.setySpeed(5);
//        ball.setX(ballDB.getForm().name());
        return ball;
    }

    public static BallDB from(Ball ball) {
        BallDB ballDB = new BallDB();
        // map logic
        return ballDB;
    }

    private static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
}

/*
BallDB
    private float amount; --> size
    private String color; --> color
    private String form; --> xSpeed, x
    private String texture; --> ySpeed, y

Ball
    private PApplet pApplet;
    private float x;
    private float y;
    private float size;
    private float xSpeed;
    private float ySpeed;
 */