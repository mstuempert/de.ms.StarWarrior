package de.ms.sw;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Spaceship extends Gizmo {
	
	public Spaceship(Universe universe, Vector2D position) {
		super(universe, position);
		this.velocity = new Vector2D(0, 0);
	}

	@Override
	public void render(Graphics g) {
		BufferedImage image = null;
		try {
			image = ImageRegistry.getInstance().getImage(
					this.velocity.x() < 0 ?	ImageRegistry.IMG_SPACESHIP_LEFT
							: this.velocity.x() > 0 ? ImageRegistry.IMG_SPACESHIP_RIGHT
									: ImageRegistry.IMG_SPACESHIP_STRAIGHT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (image != null) {
			g.drawImage(image, (int) position.x()-16, (int) position.y()-16, null);
		}
	}

	public void fireBullet() {
		Bullet bullet = new Bullet(this.universe, new Vector2D(this.position), new Vector2D(0, -500), false);
		this.universe.addBullet(bullet);
	}
	
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle((int)this.position.x()-16, (int)this.position.y()-16, 32, 32);
	}

}
