package io.eho.dishspawn;

import io.eho.dishspawn.graphics.processing.TheSketch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import processing.core.PApplet;

@SpringBootApplication
public class DishSpawnApplication {

	public static TheSketch theSketch;

	public static void main(String[] args)
	{
		SpringApplication.run(DishSpawnApplication.class, args);
		theSketch = getTheSketch();
	}

	private static TheSketch getTheSketch() {
		System.setProperty("java.awt.headless", "false"); // app needs to be headfull to allow display functionality
		TheSketch theSketch = new TheSketch();
		String[] a = {"MAIN"};
		PApplet.runSketch(a, theSketch);
		return theSketch;
	}

}
