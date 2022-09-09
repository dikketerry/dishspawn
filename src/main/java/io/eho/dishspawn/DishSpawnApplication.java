package io.eho.dishspawn;

import io.eho.dishspawn.graphics.ImageWindow;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import processing.core.PApplet;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class DishSpawnApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(DishSpawnApplication.class, args);
//		System.setProperty("java.awt.headless", "false");
//		SwingUtilities.invokeLater(() -> {
//			JFrame f = new JFrame();
//			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			f.setVisible(true);
//		});
	}

//	@Bean
//	CommandLineRunner commandLineRunner() {
//		return args -> {
//			PApplet.main("PlaySketch");
//		};
//	}
}
