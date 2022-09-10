package io.eho.dishspawn.graphics.vanillajava;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageCanvas extends JPanel {

    private BufferedImage img;
    private Graphics2D g2; // view this object as the 'robot-artist' to paint
    private Color color;
    private Ball ball;

    public ImageCanvas() {
        img = new BufferedImage(ImageWindow.WIDTH, ImageWindow.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2 = img.createGraphics();
        color = new Color(120, 150, 200, 0);
        ball = new Ball();
        System.out.println();
    }

    public void loopThrough() {

    }

    public void update() {

    }

    public void draw() {
        g2.setColor(color);
        g2.fillRect(0, 0, ImageWindow.WIDTH, ImageWindow.HEIGHT);
        ball.draw(g2);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

}
