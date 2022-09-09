package io.eho.dishspawn.play.sketchtest.processing;

public class BallDB {

    private float amount;
    private String color;
    private String form;
    private String texture;

    public BallDB() {
        this(100, "FFA4B6", "Spheres", "Firm");
    }

    public BallDB(float amount, String color, String form, String texture) {
        this.amount = amount;
        this.color = color;
        this.form = form;
        this.texture = texture;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}