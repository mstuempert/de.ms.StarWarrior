package de.ms.sw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy extends Gizmo {
	
	private static final int SHOT_PERIOD_MILLIS = 1000;
	
	private long lastShotMillis = -1;

	public Enemy(Universe universe, Vector2D position) {
		super(universe, position);
	}
	
	public boolean isVisible() {
		return this.position.x() > 0
				&& this.position.y() > 0
				&& this.position.x() < this.universe.getSize().width
				&& this.position.y() < this.universe.getSize().height;
	}
	
	@Override
	public void move(long millis) {
		super.move(millis);
		long currentTimeMillis = System.currentTimeMillis();
		if (lastShotMillis == -1) {
			lastShotMillis = currentTimeMillis; 
		} else if (currentTimeMillis - lastShotMillis > SHOT_PERIOD_MILLIS) {
			fireBullet();
			lastShotMillis = currentTimeMillis;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine((int)this.position.x(), (int)this.position.y()+10, (int)this.position.x()-10, (int)this.position.y()-10);
		g.drawLine((int)this.position.x()-10, (int)this.position.y()-10, (int)this.position.x()+10, (int)this.position.y()-10);
		g.drawLine((int)this.position.x()+10, (int)this.position.y()-10, (int)this.position.x(), (int)this.position.y()+10);
	}

	private void fireBullet() {
		Bullet bullet = new Bullet(this.universe, new Vector2D(this.position), new Vector2D(0, 500), true);
		this.universe.addBullet(bullet);
	}
	
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle((int)this.position.x()-10, (int)this.position.y()-10, 20, 20);
	}

}
