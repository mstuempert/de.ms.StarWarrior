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
		this.velocity = new Vector2D(0, 0);
	}
	
	public boolean didCollide(Gizmo g) {
		return getBoundingBox().intersects(g.getBoundingBox());
	}
	
	public boolean isVisible() {
		return new Rectangle(this.universe.getSize()).intersects(getBoundingBox());
	}
	
	public void move(long millis) {
		this.position.add(this.velocity.product((float)millis/1000f));
		if (this.position.x() < 0) {
			this.position.setX(this.universe.getSize().width+this.position.x());
		} else if (this.position.x() > this.universe.getSize().width) {
			this.position.setX(this.position.x()-this.universe.getSize().width);
		}
	}

	public abstract void render(Graphics g);
	
	public abstract Rectangle getBoundingBox();

}
