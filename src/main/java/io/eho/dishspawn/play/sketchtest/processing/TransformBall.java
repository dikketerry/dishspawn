package io.eho.dishspawn.play.sketchtest.processing;

import processing.core.PApplet;

import java.awt.*;

public class TransformBall {

//    Function<BallDB, BallOld> ballDBToBall = new Function<BallDB, BallOld>() {
//
//        @Override
//        public BallOld apply(BallDB ballDB) {
//            BallOld ball = new BallOld();
//
//            return null;
//        }
//    };

    public static BallOld from(BallDB ballDB) {
        BallOld ballOld = new BallOld();
        // map logic
        ballOld.setpApplet(new PApplet());
        ballOld.setSize(ballDB.getAmount() / 10);
        ballOld.setColor(hex2Rgb(ballDB.getColor()));
        ballOld.setX(100);
        ballOld.setY(200);
        ballOld.setxSpeed(2);
        ballOld.setySpeed(5);
//        ballOld.setX(ballDB.getForm().name());
        return ballOld;
    }

    public static BallDB from(BallOld ballOld) {
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

BallOld
    private PApplet pApplet;
    private float x;
    private float y;
    private float size;
    private float xSpeed;
    private float ySpeed;
 */