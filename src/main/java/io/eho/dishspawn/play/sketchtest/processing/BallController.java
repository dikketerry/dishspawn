package io.eho.dishspawn.play.sketchtest.processing;

import java.util.ArrayList;
import java.util.List;

public class BallController {



    public String goSpawn() {

        // create BallDB-list
        List<BallDB> ballsDB = new ArrayList<>();
        BallDB ballDB = new BallDB();
        ballsDB.add(ballDB);

        // convert BallDB to BallOld
        provideBalls(ballsDB);



        List<BallOld> ballOlds = new ArrayList<>();


        return "ball test";
    }

    private List<BallOld> provideBalls(List<BallDB> ballsDB) {
        List<BallOld> ballOlds = new ArrayList<>();

        for (BallDB ballDB : ballsDB) {
            BallOld ballOld = TransformBall.from(ballDB);
            ballOlds.add(ballOld);
        }
        return ballOlds;
    }

}