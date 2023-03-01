package Main;

import Data.Sprite;
import GameObjects.Bullet;
import GameObjects.Enemy;
import GameObjects.Ship;
import Input.Mouse;
import logic.Control;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Main{
	public static Sprite background;
	public static Graphics graphics;
	public static Ship ship;
	public static Enemy enemy;
	public static char key;
	public static ArrayList<Integer> bulletsToRemove = new ArrayList<>();
	public static ArrayList<Bullet> bullets;

	public static void main(String[] args) {
		Control ctrl = new Control();
		ctrl.gameLoop();
	}

	public static void start(Control ctrl) {
		BufferedImage backgroundBuffer = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
		graphics = backgroundBuffer.getGraphics();

		Sprite enemySprite = ctrl.getSpriteFromBackBuffer("enemy");
		Sprite shipSprite = ctrl.getSpriteFromBackBuffer("ship");
		Sprite bulletSprite = ctrl.getSpriteFromBackBuffer("bullet");

		ship = new Ship(shipSprite, bulletSprite);
		enemy = new Enemy(enemySprite);

		background = new Sprite(0, 0, backgroundBuffer, "background");
		bullets = ship.getBullets();
	}
	
	public static void update(Control ctrl) {
		Point p = Mouse.getMouseCoords();
		ship.followMouse(p);

		if (key == 'd') {
			ship.rotateRight();
		}
		if (key == 'a') {
			ship.rotateLeft();
		}
		if (key == '$')
			ship.move();
		if (key == 'n')
			ship.fireBullet();

		enemy.moveTowardsPoint(ship.getX(), ship.getY());

		if (enemy.hit > 0)
			ctrl.drawString(20, 50, "You hit enemy " + enemy.hit + " times", Color.white);

		if (enemy.getRect().isCollision(ship.getX() + 32, ship.getY() + 32))
			ctrl.drawString(20, 80, "You Lose", Color.WHITE);

		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			boolean remove = false;
			if (enemy.getRect().isCollision(bullet.getX() + 32, bullet.getY() + 32)) {
				enemy.setRandomSpawn();
				remove = true;
			}
			if (!remove) {
				bullet.move();
				ctrl.addSpriteToFrontBuffer(bullet.getX(), bullet.getY(), bullet.getImage());
			}
			if (bullet.off_screen || remove)
				bulletsToRemove.add(i);
		}

		if (bulletsToRemove.size() > 0) {
			int num_removed = 0;
			for (int i : bulletsToRemove) {
				ship.removeBullet(i - num_removed);
				num_removed++;
			}
		}
		bulletsToRemove.clear();

		ctrl.addSpriteToFrontBuffer(ship.getX(), ship.getY(), ship.getImage());
		ctrl.addSpriteToFrontBuffer(enemy.getX(), enemy.getY(), enemy.getImage());
//		graphics.setColor(Color.white);
//		graphics.drawRect(enemy.getX(), enemy.getY(), 64, 64);
//		graphics.drawRect(ship.getX(), ship.getY(), 64, 64);
		ctrl.addSpriteToFrontBuffer(0, 0, background);
	}
}
