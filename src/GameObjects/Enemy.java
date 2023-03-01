package GameObjects;

import Data.Rect;
import Data.Sprite;
import timer.stopWatchX;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy {
    private double rotation = 0;
    private int width;
    private int height;
    private double rotationSpeed = 12;
    private double speed = 3;
    public int hit = 0;
    private Sprite sprite;
    private BufferedImage master;
    private Rect rect;
    private stopWatchX timer;

    public Enemy(Sprite sprite) {
        this.width = 64;
        this.height = 64;
        this.sprite = sprite;
        this.master = sprite.getSprite();
        this.rect = new Rect(900, 964, 500, 564, "enemy");
        timer = new stopWatchX(20);
    }

    public void setRandomSpawn() {
        Random rand = new Random();
        int randx = rand.nextInt(1280);
        int randy = rand.nextInt(720);
        this.rect = new Rect(randx, randx + 64, randy, randy + 64, "enemy");
    }

    public Sprite getImage() {
        return sprite;
    }

    public Rect getRect() {
        return rect;
    }

    public int getX() {
        return rect.getX();
    }

    public int getY() {
        return rect.getY();
    }

    public BufferedImage rotate(BufferedImage image, double angle) {
        double rotationRequired = Math.toRadians(angle);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }

    public void hit() {
        hit++;
    }

    public void moveTowardsPoint(int x, int y) {
        if (timer.isTimeUp()) {
            rotateTowardsPoint(x, y);
            double rotRad = Math.toRadians(rotation);
            double velocityX = Math.sin(rotRad) * speed;
            double velocityY = Math.cos(rotRad) * speed;

            int self_x = rect.getX();
            int self_y = rect.getY();

            rect.setX(self_x + (int)velocityX);
            rect.setY(self_y - (int)velocityY);

            timer.resetWatch();
        }
    }

    public double getRotation() {
        return rotation;
    }

    public void rotateTowardsPoint(int x, int y) {
        int centeredX = rect.getX() + this.width / 2;
        int centeredY = rect.getY() + this.height / 2;

        double radians = Math.atan2(y - centeredY, x - centeredX);
        rotation = Math.toDegrees(radians) + 90;
        sprite.changeImage(rotate(master, rotation));
    }
}
