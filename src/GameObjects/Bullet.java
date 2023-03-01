package GameObjects;

import Data.Sprite;
import timer.stopWatchX;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Bullet {
    private int velocityX;
    private int velocityY;
    private int x;
    private int y;
    private double rotation;
    private Sprite sprite;
    public boolean off_screen = false;
    private stopWatchX timer;

    public Bullet(int start_x, int start_y, double rotation, Sprite sprite) {
        x = start_x;
        y = start_y;
        this.rotation = rotation;
        int speed = 30;

        sprite.changeImage(rotate(sprite.getSprite(), rotation));
        double rotRad = Math.toRadians(rotation);
        velocityX = (int)Math.ceil(Math.sin(rotRad) * speed);
        velocityY = (int)Math.ceil(Math.cos(rotRad) * speed);
        this.sprite = sprite;
        timer = new stopWatchX(20);
    }

    public void move() {
        if (timer.isTimeUp()) {
            if (x < -100 || x > 2000 || y < -100 || y > 900)
                off_screen = true;
            else {
                x += velocityX;
                y -= velocityY;
            }
            timer.resetWatch();
        }
    }

    public BufferedImage rotate(BufferedImage image, double angle) {
        double rotationRequired = Math.toRadians(angle);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Sprite getImage() {
        return sprite;
    }
}
