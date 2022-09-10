package io.eho.dishspawn.play.sketchtest.processing;

import processing.core.PApplet;

public class SketchThread extends Thread {

    private boolean doStop = false;

    public synchronized void doStop() {
        this.doStop = true;
    }

    public synchronized boolean keepRunning() {
        return this.doStop == false;
    }

    @Override
    public void run() {
        System.out.println("diagnostic: in SketchThread");
        PApplet.main("io.eho.dishspawn.play.sketchtest.processing.TheSketch");

        while(keepRunning()) {
            System.out.println("diagnostic: SketchThread running");

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("diagnostic: exception while sleeping in run (sleepwalking?)!");
                e.printStackTrace();
            }
        }
    }
}
