package de.ms.sw;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler {
	
	private static final float STARSHIP_VELOCITY = 200f;
	
	private Universe universe;
	
	private boolean space_down = false;
	
	private final KeyListener kListener = new KeyListener() {
		public void keyPressed(KeyEvent e) {
			handleKeyPressedEvent(e);
		};
		
		@Override
		public void keyReleased(KeyEvent e) {
			handleKeyReleasedEvent(e);
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			handleKeyTypedEvent(e);
		}
	};
	
	public InputHandler(Universe universe) {
		this.universe = universe;
		this.universe.getFrame().addKeyListener(this.kListener);
	}
	
	public void destroy() {
		this.universe.getFrame().removeKeyListener(this.kListener);
	}

	private void handleKeyPressedEvent(KeyEvent e) {
		int keyCode = e.getKeyCode();
		Spaceship spaceship = this.universe.getSpaceship();
		if ((keyCode == KeyEvent.VK_LEFT) && (spaceship.velocity.x() == 0)) {
			spaceship.velocity.setX(-STARSHIP_VELOCITY);
		}
		if ((keyCode == KeyEvent.VK_RIGHT) && (spaceship.velocity.x() == 0)) {
			spaceship.velocity.setX(STARSHIP_VELOCITY);
		}
		if ((keyCode == KeyEvent.VK_UP) && (spaceship.velocity.y() == 0)) {
			spaceship.velocity.setY(-STARSHIP_VELOCITY);
		}
		if ((keyCode == KeyEvent.VK_DOWN) && (spaceship.velocity.y() == 0)) {
			spaceship.velocity.setY(STARSHIP_VELOCITY);
		}
		if ((keyCode == KeyEvent.VK_SPACE) && !space_down) {
			spaceship.fireBullet();
			space_down = true;
		}
	}

	private void handleKeyReleasedEvent(KeyEvent e) {
		int keyCode = e.getKeyCode();
		Spaceship spaceship = this.universe.getSpaceship();
		if ((keyCode == KeyEvent.VK_LEFT) && (spaceship.velocity.x() == -STARSHIP_VELOCITY)) {
			spaceship.velocity.setX(0);
		}
		if ((keyCode == KeyEvent.VK_RIGHT) && (spaceship.velocity.x() == STARSHIP_VELOCITY)) {
			spaceship.velocity.setX(0);
		}
		if ((keyCode == KeyEvent.VK_UP) && (spaceship.velocity.y() == -STARSHIP_VELOCITY)) {
			spaceship.velocity.setY(0);
		}
		if ((keyCode == KeyEvent.VK_DOWN) && (spaceship.velocity.y() == STARSHIP_VELOCITY)) {
			spaceship.velocity.setY(0);
		}
		if ((keyCode == KeyEvent.VK_SPACE) && space_down) {
			space_down = false;
		}
	}

	private void handleKeyTypedEvent(KeyEvent e) {
	}

}