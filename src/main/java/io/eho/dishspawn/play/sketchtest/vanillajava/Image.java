package io.eho.dishspawn.play.sketchtest.vanillajava;

import java.awt.image.BufferedImage;

public class Image {

    BufferedImage img;
    int xPosition;
    int yPosition;
    int alpha;
    int red;
    int green;
    int blue;

    // chained constructors for flexibility
    public Image() {
        this(new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB));
    }

    public Image(BufferedImage bufferedImage) {
        this(bufferedImage, 128, 244, 241, 134);
    }

    public Image(int xSize, int ySize) {
        this(new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_ARGB),128, 244, 241, 134);
    }

    public Image(int xSize, int ySize, int alpha, int red, int green, int blue) {
        this(new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_ARGB), alpha, red, green, blue);
    }

    public Image(BufferedImage bufferedImage, int alpha, int red, int green, int blue) {
        this.img = bufferedImage;
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void setColorBufferedImage() {
        int color = (alpha << 24) | (red << 16) | (green << 8) | blue;
        this.img.setRGB(0, 0, color);
    }














}
