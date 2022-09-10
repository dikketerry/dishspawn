package io.eho.dishspawn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
