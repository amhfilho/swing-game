package com.amhfilho.games;

import java.awt.Graphics2D;

public class Box implements GameObject {
	private float x;
	private float y;
	private final int width;
	private final int height;
	private final float speedX;

	public Box(float x, float y, int width, int height, float speedX) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speedX = speedX;
	}

	@Override
	public void update(float deltaTime) {
		x += speedX * deltaTime;
	}

	@Override
	public void render(Graphics2D g) {
		g.fillRect((int) x, (int) y, width, height);
	}
}
