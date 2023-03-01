package GameObjects;

import Data.Rect;
import Data.Sprite;
import timer.stopWatchX;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Ship {
    private double rotation = 0;
    private int width;
    private int height;
    private double rotationSpeed = 8;
    private double speed = 8;
    private Sprite sprite;
    private BufferedImage master;
    private Rect rect;
    private ArrayList<Bullet> bullets;
    private Sprite bulletSprite;
    private stopWatchX firingDelay;

    public Ship(Sprite sprite, Sprite bulletSprite) {
        this.width = 64;
        this.height = 64;
        this.sprite = sprite;
        this.master = sprite.getSprite();
        this.rect = new Rect(300, 364, 300, 364, "ship");
        this.bullets = new ArrayList<>();
        this.bulletSprite = bulletSprite;
        this.firingDelay = new stopWatchX(0);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public void removeBullet(int bullet) {
        bullets.remove(bullet);
    }

    public void fireBullet() {
        if (firingDelay.isTimeUp()) {
            Sprite bullet = new Sprite(
                                bulletSprite.getX(),
                                bulletSprite.getY(),
                                Sprite.deepCopy(bulletSprite.getSprite()),
                                "bullet"
                            );
            if (bullets.size() < 40000)
                bullets.add(new Bullet(rect.getX(), rect.getY(), rotation, bullet));
            firingDelay.resetWatch();
        }
    }

    public Rect getRect() {
        return rect;
    }

    public Sprite getImage() {
        return sprite;
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

    public void move() {
        double rotRad = Math.toRadians(rotation);
        double velocityX = Math.sin(rotRad) * speed;
        double velocityY = Math.cos(rotRad) * speed;

        int x = rect.getX();
        int y = rect.getY();

        rect.setX(x + (int)velocityX);
        rect.setY(y - (int)velocityY);
    }

    public double getRotation() {
        return rotation;
    }

    public void followMouse(Point p) {
        int mouseX = (int)p.getX();
        int mouseY = (int)p.getY();

        int centeredX = rect.getX() + this.width / 2;
        int centeredY = rect.getY() + this.height / 2;

        double radians = Math.atan2(mouseY - centeredY, mouseX - centeredX);
        rotation = Math.toDegrees(radians) + 90;
        sprite.changeImage(rotate(master, rotation));
    }

    public void rotateLeft() {
        rotation -= rotationSpeed;
        sprite.changeImage(rotate(master, rotation));
    }

    public void rotateRight() {
        rotation += rotationSpeed;
        sprite.changeImage(rotate(master, rotation));
    }
}
