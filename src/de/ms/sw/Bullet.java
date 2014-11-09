package de.ms.sw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends Gizmo {
	
	private boolean enemy;

	public Bullet(Universe universe, Vector2D position, Vector2D velocity, boolean enemy) {
		super(universe, position);
		this.velocity = velocity;
		this.enemy = enemy;
	}
	
	public boolean isEnemy() {
		return this.enemy;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(this.enemy ? Color.RED : Color.BLUE);
		g.fillRect((int)this.position.x()-1, (int)this.position.y()-3, 2, 10);
	}
	
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle((int)this.position.x()-1, (int)this.position.y()-3, 2, 10);
	}

}
