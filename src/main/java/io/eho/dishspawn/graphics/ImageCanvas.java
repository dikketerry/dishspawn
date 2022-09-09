package io.eho.dishspawn.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageCanvas extends JPanel {

    private BufferedImage img;
    private Graphics2D g2;
    private Color color;
    private Ball ball;

    public ImageCanvas() {
        img = new BufferedImage(ImageWindow.WIDTH, ImageWindow.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) img.getGraphics();
        color = new Color(120, 150, 200, 0);
        ball = new Ball();
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
