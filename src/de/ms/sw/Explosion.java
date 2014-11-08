package de.ms.sw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Explosion extends Gizmo {
	
	private long age = 0;

	public Explosion(Universe universe, Vector2D position) {
		super(universe, position);
	}
	
	@Override
	public boolean isVisible() {
		return this.age < 2000;
	}
	
	@Override
	public void move(long millis) {
		super.move(millis);
		this.age += millis;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		int size = (int)(age/25);
		g.fillOval((int)(position.x()-0.5*size), (int)(position.y()-0.5*size), size, size);
	}

	@Override
	public Rectangle getBoundingBox() {
		return null;
	}

}
