package io.eho.dishspawn.graphics;

import java.awt.*;

public class Ball {

    private int x, y;
    private int dx, dy, speed;
    private Color color;
    private int size;

    public Ball() {
        x = ImageCanvas.WIDTH / 2;
        y = ImageCanvas.HEIGHT / 2;
        dx = dy = 3;
        color = new Color(111, 181, 41, 127);
        size = 33;
    }

    public void update() {
        x += dx;
        y += dy;

        if(x < 0 || x > ImageWindow.WIDTH) {
            dx *= -1;
        }

        if (y < 0 || y > ImageWindow.HEIGHT) {
            dy *= -1;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval(x, y, size, size);
    }
}
