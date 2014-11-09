package de.ms.sw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Universe {
	
	private static final float STARFIELD_DENSITY = 1f;
	
	private Frame frame;
	
	private Spaceship spaceship;
	
	private List<Star> stars = Collections.synchronizedList(new ArrayList<Star>());
	
	private List<Bullet> bullets = Collections.synchronizedList(new ArrayList<Bullet>());

	private List<Enemy> enemies = Collections.synchronizedList(new ArrayList<Enemy>());
	
	private List<Explosion> explosions = Collections.synchronizedList(new ArrayList<Explosion>());

	private InputHandler inputHandler;
	
	private LevelController levelController;
	
	private long lastStepMillis = -1;
	
	private Random random;
	
	private long gameOverTimeMillis = -1;
	
	private int score = 0;
	
	public Universe(Frame frame) {
		
		this.frame = frame;
		Dimension size = frame.getSize();
		this.spaceship = new Spaceship(this, new Vector2D(size.width/2f, size.height*0.9f));

		this.inputHandler = new InputHandler(this);
		this.levelController = new LevelController();
		//levelController.reset(25);
		this.levelController.start();
		
		this.lastStepMillis = System.currentTimeMillis();
		this.random = new Random(this.lastStepMillis);
		
	}
	
	public void addBullet(Bullet bullet) {
		synchronized (this.bullets) {
			this.bullets.add(bullet);
		}
	}
	
	public void destroy() {
		this.levelController.destroy();
		this.inputHandler.destroy();
	}
	
	public Frame getFrame() {
		return this.frame;
	}
	
	public Dimension getSize() {
		return this.frame.getSize();
	}
	
	public Spaceship getSpaceship() {
		return this.spaceship;
	}
	
	public void nextStep() {
		
		long currentTimeMillis = System.currentTimeMillis();
		long millisSinceLastStep = currentTimeMillis - this.lastStepMillis;
		this.lastStepMillis = currentTimeMillis;
		
		float nextFloat = this.random.nextFloat();
		if (nextFloat < STARFIELD_DENSITY) {
			createNewStar();
		}
		
		float newEnemyProbability = this.levelController.getEnemiesPerSecond()*millisSinceLastStep/1000f;
		nextFloat = this.random.nextFloat();
		if (nextFloat < newEnemyProbability) {
			createNewEnemy();
		}
		
		synchronized (this.stars) {
			for (int i = 0 ; i < this.stars.size() ; i++) {
				Star s = stars.get(i);
				s.move(millisSinceLastStep);
				if (!s.isVisible()) {
					this.stars.remove(i--);
				}
			}
		}
		
		this.spaceship.move(millisSinceLastStep);
		
		synchronized (this.bullets) {
			for (int i = 0 ; i < this.bullets.size() ; i++) {
				Bullet b = bullets.get(i);
				b.move(millisSinceLastStep);
				if (!b.isVisible()) {
					this.bullets.remove(i--);
				}
			}
		}
		
		synchronized (this.enemies) {
			for (int i = 0 ; i < this.enemies.size() ; i++) {
				Enemy e = enemies.get(i);
				e.move(millisSinceLastStep);
				if (!e.isVisible()) {
					this.enemies.remove(i--);
				}
			}
		}
		
		synchronized (this.explosions) {
			for (int i = 0 ; i < this.explosions.size() ; i++) {
				Explosion e = explosions.get(i);
				if (e.isVisible()) {
					e.move(millisSinceLastStep);
				} else {
					this.explosions.remove(i--);
				}
			}
		}
		
		detectCollisions();
		
	}
	
	private void detectCollisions() {
		synchronized (this.bullets) {
			synchronized (this.enemies) {
				for (int i = 0 ; i < this.bullets.size() ; i++) {
					Bullet bullet = this.bullets.get(i);
					if (!bullet.isEnemy()) {
						for (int j = 0 ; j < this.enemies.size() ; j++) {
							if (bullet.didCollide(this.enemies.get(j))) {
								addExplosion(this.enemies.get(j).position);
								this.score += this.enemies.get(j).getScore();
								this.bullets.remove(i);
								this.enemies.remove(j);
								continue;
							}
						}
					} else {
						if (bullet.didCollide(this.spaceship)) {
							addExplosion(this.spaceship.position);
							this.bullets.remove(i);
							gameOver();
						}
					}
				}
			}			
		}
	}

	private void gameOver() {
		this.inputHandler.destroy();
		this.gameOverTimeMillis = System.currentTimeMillis();
	}

	private void addExplosion(Vector2D position) {
		Explosion explosion = new Explosion(this, position);
		synchronized (this.explosions) {
			this.explosions.add(explosion);
		}
	}

	private void createNewEnemy() {
		Enemy enemy = new Enemy(this, new Vector2D(random.nextInt(frame.getWidth()), 10));
		enemy.velocity = new Vector2D(random.nextInt(200)-100, random.nextInt(150)+50);
		synchronized (this.enemies) {
			this.enemies.add(enemy);
		}
	}

	private void createNewStar() {
		Star star = new Star(this, new Vector2D(random.nextInt(frame.getWidth()), 1), random.nextFloat());
		synchronized (this.stars) {
			this.stars.add(star);
		}
	}

	public void render(Graphics g) {
		
		Dimension size = frame.getSize();
		
		g.setColor(this.gameOverTimeMillis <= 0 ? Color.BLACK : Color.RED);
		g.fillRect(0, 0, size.width, size.height);
		
		synchronized (this.stars) {
			for (Star star : this.stars) {
				star.render(g);
			}
		}
		
		synchronized (this.bullets) {
			for (Bullet bullet : this.bullets) {
				bullet.render(g);
			}
		}
		
		synchronized (this.enemies) {
			for (Enemy enemy : this.enemies) {
				enemy.render(g);
			}
		}
		
		synchronized (this.explosions) {
			for (Explosion e : this.explosions) {
				e.render(g);
			}
		}
		
		this.spaceship.render(g);
		
		renderStatistics(g);
		
		if (this.gameOverTimeMillis > 0) {
			g.drawString("Game Over", 100, 100);
		}
		
	}
	
	private void renderStatistics(Graphics g) {
		int currentLevel = this.levelController.getCurrentLevel();
		g.setColor(Color.WHITE);
		g.setFont(new Font( "SansSerif", Font.PLAIN, 20 ));
		g.drawString("Level: " + currentLevel, 20, 30);
		g.drawString("Score: " + this.score, 20, 60);
	}

}
