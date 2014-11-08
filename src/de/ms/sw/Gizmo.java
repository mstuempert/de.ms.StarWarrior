package de.ms.sw;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Gizmo {
	
	protected Universe universe;
	
	public Vector2D position;
	
	public Vector2D velocity;
	
	public Gizmo(Universe universe, Vector2D position) {
		this.universe = universe;
		this.position = position;
	}
	
	public boolean didCollide(Gizmo g) {
		return getBoundingBox().intersects(g.getBoundingBox());
	}
	
	public void move(long millis) {
		this.position.add(this.velocity.product((float)millis/1000f));
	}

	public abstract void render(Graphics g);
	
	public abstract Rectangle getBoundingBox();

}
