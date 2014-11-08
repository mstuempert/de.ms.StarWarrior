package de.ms.sw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Spaceship extends Gizmo {
	
	public Spaceship(Universe universe, Vector2D position) {
		super(universe, position);
		this.velocity = new Vector2D(0, 0);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine((int)this.position.x(), (int)this.position.y()-10, (int)this.position.x()-10, (int)this.position.y()+10);
		g.drawLine((int)this.position.x()-10, (int)this.position.y()+10, (int)this.position.x()+10, (int)this.position.y()+10);
		g.drawLine((int)this.position.x()+10, (int)this.position.y()+10, (int)this.position.x(), (int)this.position.y()-10);
	}

	public void fireBullet() {
		Bullet bullet = new Bullet(this.universe, new Vector2D(this.position), new Vector2D(0, -500), false);
		this.universe.addBullet(bullet);
	}
	
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle((int)this.position.x()-10, (int)this.position.y()-10, 20, 20);
	}

}
