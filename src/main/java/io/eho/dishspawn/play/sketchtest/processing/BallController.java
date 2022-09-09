package io.eho.dishspawn.play.sketchtest.processing;

import java.util.ArrayList;
import java.util.List;

public class BallController {



    public String goSpawn() {

        // create BallDB-list
        List<BallDB> ballsDB = new ArrayList<>();
        BallDB ballDB = new BallDB();
        ballsDB.add(ballDB);

        // convert BallDB to Ball
        provideBalls(ballsDB);



        List<Ball> balls = new ArrayList<>();


        return "ball test";
    }

    private List<Ball> provideBalls(List<BallDB> ballsDB) {
        List<Ball> balls = new ArrayList<>();

        for (BallDB ballDB : ballsDB) {
            Ball ball = TransformBall.from(ballDB);
            balls.add(ball);
        }
        return balls;
    }

}