package de.ms.sw;

public class Vector2D {
	
	private float x;
	
	private float y;
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector2D v) {
		this(v.x, v.y);
	}
	
	public Vector2D add(Vector2D v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	
	public Vector2D multiply(float a) {
		this.x *= a;
		this.y *= a;
		return this;
	}
	
	public Vector2D product(float a) {
		return new Vector2D(this.x * a, this.y * a);
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public Vector2D sum(Vector2D v) {
		return new Vector2D(this.x + v.x, this.y + v.y);
	}
	
	public float x() {
		return this.x;
	}
	
	public float y() {
		return this.y;
	}

}
